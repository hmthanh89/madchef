package utils.integration.jira;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.atlassian.jira.rest.client.IssueRestClient;
import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.JiraRestClientFactory;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.BasicProject;
import com.atlassian.jira.rest.client.domain.Comment;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.IssueType;
import com.atlassian.jira.rest.client.domain.Transition;
import com.atlassian.jira.rest.client.domain.input.FieldInput;
import com.atlassian.jira.rest.client.domain.input.IssueInput;
import com.atlassian.jira.rest.client.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import utils.helper.Logger;

public class JiraIssueReporterHandler {

	private String jiraURL;
	private String userName;
	private String password;
	private String projectKey;

	public JiraIssueReporterHandler(String jiraURL, String userName,
			String password, String projectKey) {
		this.jiraURL = jiraURL;
		this.userName = userName;
		this.password = password;
		this.projectKey = projectKey;
	}

	public void maskBugStatus(String issueID, String status) {
		try {
			URI uri = new URI(this.jiraURL);
			JiraRestClientFactory restFactory = new AsynchronousJiraRestClientFactory();
			JiraRestClient client = null;
			client = restFactory.createWithBasicHttpAuthentication(uri,
					this.userName, this.password);
			IssueRestClient issueClient = client.getIssueClient();
			List<IssueRestClient.Expandos> expand = new ArrayList<IssueRestClient.Expandos>();
			expand.add(IssueRestClient.Expandos.TRANSITIONS);

			Promise<Issue> promisedParent = issueClient.getIssue(issueID,
					expand);
			if (promisedParent.get().getIssueType().getName().equals("Bug")) {
				Issue issue = promisedParent.claim();

				Promise<Iterable<Transition>> ptransitions = issueClient
						.getTransitions(issue);
				Iterable<Transition> transitions = ptransitions.claim();
				for (Transition t : transitions) {
					if (t.getName().equalsIgnoreCase(status)) {
						issueClient.transition(issue,
								new TransitionInput(t.getId())).claim();
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("JiraIssueReporterHandler:maskBugStatus: "
					+ e.getCause());
		}
	}

	public void addComment(String issueID, String comment) {

	}

	public void updateIssue(String issueID, String description,
			String attachment) {
		try {
			URI uri = new URI(this.jiraURL);
			JiraRestClientFactory restFactory = new AsynchronousJiraRestClientFactory();
			JiraRestClient client = null;
			client = restFactory.createWithBasicHttpAuthentication(uri,
					this.userName, this.password);
			IssueRestClient issueClient = client.getIssueClient();
			Issue issue = (Issue) issueClient.getIssue(issueID).claim();
			Iterable<Comment> comments = issue.getComments();
			String lastElement = "";

			for (Comment comment : comments) {
				lastElement = comment.getBody();
			}

			if (!lastElement.isEmpty()) {
				lastElement = lastElement.split("=============")[1];
			}

			if (!lastElement.trim().replaceAll("(\\r|\\n)", "")
					.equals(description.replaceAll("(\\r|\\n)", ""))) {
				File att = new File(attachment);
				String attachmentName = getToday() + ".png";
				ByteArrayInputStream bInput = new ByteArrayInputStream(
						FileUtils.readFileToByteArray(att));
				issueClient.addAttachment(issue.getAttachmentsUri(), bInput,
						attachmentName);
				issueClient
						.addComment(
								issue.getCommentsUri(),
								Comment.valueOf(String
										.format("This issue is still {color:red}*FAILING*{color} at step: \n=============\n%s\n=============\nPlease see screenshot: !%s|thumbnail!",
												description, attachmentName)));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void createNewBug(String summary, String description,
			String attachment, String testClass, String testMethod) {
		try {
			URI uri = new URI(this.jiraURL);
			JiraRestClientFactory restFactory = new AsynchronousJiraRestClientFactory();
			JiraRestClient client = null;
			client = restFactory.createWithBasicHttpAuthentication(uri, this.userName, this.password);
			IssueRestClient issueClient = client.getIssueClient();
			BasicProject basicProject = null;
			IssueType issueType = null;

			Promise<Iterable<BasicProject>> projects = client
					.getProjectClient().getAllProjects();
			Iterable<BasicProject> project = projects.claim();
			for (BasicProject t : project) {
				if (t.getKey().equalsIgnoreCase(this.projectKey)) {
					basicProject = t;
					break;
				}
			}

			Promise<Iterable<IssueType>> promise = client.getMetadataClient()
					.getIssueTypes();
			Iterable<IssueType> issueTypes = promise.claim();
			for (IssueType it : issueTypes) {
				if (it.getName().equalsIgnoreCase("Bug")) {
					issueType = it;
					break;
				}
			}

			IssueInputBuilder issueBuilder = new IssueInputBuilder(
					basicProject, issueType);
			issueBuilder.setSummary(summary);
			issueBuilder.setDescription(description);
			issueBuilder.setFieldInput(new FieldInput("customfield_10009", testClass));
			issueBuilder.setFieldInput(new FieldInput("customfield_10008", testMethod));
			issueBuilder.build();
			IssueInput issueInput = issueBuilder.build();
			BasicIssue issueObj = issueClient.createIssue(issueInput).claim();

			if (!attachment.isEmpty()) {
				File att = new File(attachment);
				Issue issue = (Issue) issueClient.getIssue(issueObj.getKey())
						.claim();
				ByteArrayInputStream bInput = new ByteArrayInputStream(
						FileUtils.readFileToByteArray(att));
				issueClient.addAttachment(issue.getAttachmentsUri(), bInput,
						getToday() + ".png");
			}

			String bugID = issueObj.getKey();
			Logger.bug(bugID, this.jiraURL + "/browse/" + bugID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getToday() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
