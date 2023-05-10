package pages.Pinterest.LoginPage;

public class LoginPage_MOBILE extends LoginPage {

	@Override
	public void login(String email, String password) {
		txtEmail.waitForDisplay();
		txtEmail.clear();
		txtEmail.enter(email);
		btnContinue.click();
		txtPassword.waitForDisplay();
		txtPassword.enter(password);
		btnLogin.click();
		btnLogin.waitForInvisibility();
	}
}
