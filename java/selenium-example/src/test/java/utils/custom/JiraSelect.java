package utils.custom;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;

public class JiraSelect extends Element{

	private Button btnDropdown;
	private TextBox txtInput;
	
	public JiraSelect(String locator) {
		super(locator);
		txtInput = new TextBox(locator);
		btnDropdown = new Button(txtInput, "./following-sibling::span[contains(@class,'drop-menu')]");
	}
	
	public JiraSelect(By locator) {
		super(locator);
		txtInput = new TextBox(locator);
		btnDropdown = new Button(txtInput, "./following-sibling::span[contains(@class,'drop-menu')]");
	}

	public void selectValueByVisibleText(String value) {
		btnDropdown.click();
		txtInput.enter(value);
		txtInput.enter(Keys.ENTER);
	}
}
