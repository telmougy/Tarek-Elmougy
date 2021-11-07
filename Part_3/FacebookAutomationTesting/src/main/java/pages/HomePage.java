package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageBase {

	public HomePage(WebDriver driver) {
		super(driver);
	}

		@FindBy(id="email")
		WebElement emailOrPhoneNumberTxtBox;
	
		@FindBy(id="pass")
		WebElement passWordTxtBox;
	
		@FindBy(css="button[class='_42ft _4jy0 _6lth _4jy6 _4jy1 selected _51sy']")
		WebElement loginBtn;

		@FindBy (linkText = "Create New Account")
		public WebElement registrationBtn;
		
		@FindBy (linkText="Forgot password?")
		WebElement forgottenPasswordBtn;
		
		@FindBy(css = "._9ay7")
		public WebElement errorMessage;

		
		@FindBy (css="a[class='d2edcug0 hpfvmrgz qv66sw1b c1et5uql lr9zc1uh a8c37x1j keod5gw0 nxhoafnm aigsh9s9 qg6bub1s fe6kdd0r mau55g9w c8b282yb iv3no6db o0t2es00 f530mmz5 hnhda86s oo9gr5id']")
		public	WebElement welcomeMessage;
		
		@FindBy (css="span[class='a8c37x1j ni8dbmo4 stjgntxs l9j0dhe7']")
		public	WebElement accountName;
		@FindBy(linkText = "Log Out")
		public WebElement logOutBtn;

		public void LogIn (String username ,String password) {
			setText(emailOrPhoneNumberTxtBox, username);
			setText(passWordTxtBox, password);
			clickButton(loginBtn);
		}
		public void openregisterWindow() {clickButton(registrationBtn);}
		

}
