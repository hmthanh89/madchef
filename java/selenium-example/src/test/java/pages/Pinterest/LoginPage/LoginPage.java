package pages.Pinterest.LoginPage;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.TextBox;

import utils.base.PageFactory;

public class LoginPage extends PageFactory {

	// Elements
	protected TextBox txtEmail = $(TextBox.class, "txtEmail");
	protected TextBox txtPassword = $(TextBox.class, "txtPassword");
	protected Button btnContinue = $(Button.class, "btnContinue");
	protected Button btnLogin = $(Button.class, "btnLogin");
	protected Button btnWelcomeLogin = $(Button.class, "btnWelcomeLogin");

	// Methods
	public void login(String email, String password) {
		btnWelcomeLogin.waitForDisplay();
		btnWelcomeLogin.click();
		txtEmail.waitForDisplay();
		txtEmail.enter(email);
		txtPassword.enter(password);
		btnLogin.click();
	}

}
