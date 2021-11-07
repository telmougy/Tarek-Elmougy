package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends PageBase {

	public RegistrationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(name = "firstname")
	public	WebElement FirstNameTxtBox;

	@FindBy(name="lastname")
	public WebElement LastNameTxtBox;

	@FindBy(name="reg_email__")
	public WebElement MailTxtBox;

	@FindBy (name="reg_email_confirmation__")
	public WebElement MailConfirmation;

	@FindBy (name="reg_passwd__")
	public WebElement PasswordTxtBox;

	@FindBy (id="day")
	WebElement Day;
	public Select DayList = new Select(Day);

	@FindBy (id="month")
	WebElement Month;
	Select MonthList = new Select(Month);

	@FindBy (id="year")
	WebElement Year;
	Select YearList = new Select(Year);

	@FindBy (css  ="input[value='2']")
	WebElement male;

	@FindBy (css  ="input[value='1']")
	WebElement female;

	@FindBy (css  ="input[value='-1']")
	WebElement Custom;

	@FindBy (name  ="websubmit")
	public WebElement submit;


	@FindBy (css="a[class='d2edcug0 hpfvmrgz qv66sw1b c1et5uql lr9zc1uh a8c37x1j keod5gw0 nxhoafnm aigsh9s9 qg6bub1s fe6kdd0r mau55g9w c8b282yb iv3no6db o0t2es00 f530mmz5 hnhda86s oo9gr5id']")
	public	WebElement welcomeMessage;

	@FindBy(css = "div[class='kvgmc6g5 cxmmr5t8 oygrvhab hcukyx3x c1et5uql']")
	public WebElement confirmationMessage ;

	@FindBy(css = "div[class='_5633 _5634 _53ij']")
	public WebElement errorContextMessage ;

	public void selectDay(String Day) {
		DayList.selectByValue(Day);
	}
	public void selectMonth(String Month) {
		MonthList.selectByValue(Month);
	}
	public void selectYear(String Year) {
		YearList.selectByValue(Year);


	}
	public void register (String firstName,String surname,String email,
			String emailConfirmation,String password,String day , String month,String year, String gender)
	{
		setText(FirstNameTxtBox, firstName);
		setText(LastNameTxtBox, surname);
		setText(MailTxtBox, email);
		setText(MailConfirmation, emailConfirmation);
		setText(PasswordTxtBox, password);
		select(DayList, day);
		select(MonthList, month);
		select(YearList, year);
		if (gender.equals("male"))
			male.click();
		else if(gender.equals("female"))
			female.click();
		else 
			male.click();
		submit.click();
	}

	public void registerWithInvalidData (String dataFlag,String firstName,String surname,String email,
			String emailConfirmation,String password,String day , String month,String year, String gender)
	{
		if (!dataFlag.equals("NoFirstName"))
			setText(FirstNameTxtBox, firstName);

		if (!dataFlag.equals("NoLastName"))
			setText(LastNameTxtBox, surname);

		if (!dataFlag.equals("NoEmail"))
			setText(MailTxtBox, email);
		
		if (!dataFlag.equals("NoEmailConfirmation")&& !dataFlag.equals("NoEmail")&&!dataFlag.equals("InvalidEmail"))
			setText(MailConfirmation, emailConfirmation);

		if (!dataFlag.equals("NoPassword"))
			setText(PasswordTxtBox, password);

		if (!dataFlag.equals("N0Birthdate"))
		{
			select(DayList, day);
			select(MonthList, month);
			if (!dataFlag.equals("NoBirthYear"))

				select(YearList, year);
		}
		if (!dataFlag.equals("NoGender"))
		{
			if (gender =="male")
				male.click();
			else if(gender=="female")
				female.click();
			else 
				male.click();
		}
		submit.click();
	}
	//	public void registerWithMissingFirstName (String surname,String email,
	//			String emailConfirmation,String password,String day , String month,String year, String gender)
	//	{
	//		setText(LastNameTxtBox, surname);
	//		setText(RegMailTxtBox, email);
	//		setText(RegMailConfirmation, emailConfirmation);
	//		setText(PasswordTxtBox, password);
	//		select(DayList, day);
	//		select(MonthList, month);
	//		select(YearList, year);
	//		if (gender =="male")
	//			male.click();
	//		else if(gender=="female")
	//			female.click();
	//		else 
	//			male.click();
	//		submit.click();
	//	}
	//	public void registerWithMissingLastName (String firstName,String email,
	//			String emailConfirmation,String password,String day , String month,String year, String gender)
	//	{
	//		setText(FirstNameTxtBox, firstName);
	//		setText(RegMailTxtBox, email);
	//		setText(RegMailConfirmation, emailConfirmation);
	//		setText(PasswordTxtBox, password);
	//		select(DayList, day);
	//		select(MonthList, month);
	//		select(YearList, year);
	//		if (gender =="male")
	//			male.click();
	//		else if(gender=="female")
	//			female.click();
	//		else 
	//			male.click();
	//		submit.click();
	//	}
	//	public void registerWithMissingEmail (String firstName,String surname,
	//			String password,String day , String month,String year, String gender)
	//	{
	//		setText(FirstNameTxtBox, firstName);
	//		setText(LastNameTxtBox, surname);
	//		setText(PasswordTxtBox, password);
	//		select(DayList, day);
	//		select(MonthList, month);
	//		select(YearList, year);
	//		if (gender =="male")
	//			male.click();
	//		else if(gender=="female")
	//			female.click();
	//		else 
	//			male.click();
	//		submit.click();
	//	}
	//	public void registerWithMissingEmailConfirmation (String firstName,String surname,String email,
	//			String password,String day , String month,String year, String gender)
	//	{
	//		setText(FirstNameTxtBox, firstName);
	//		setText(LastNameTxtBox, surname);
	//		setText(RegMailTxtBox, email);
	//		setText(PasswordTxtBox, password);
	//		select(DayList, day);
	//		select(MonthList, month);
	//		select(YearList, year);
	//		if (gender =="male")
	//			male.click();
	//		else if(gender=="female")
	//			female.click();
	//		else 
	//			male.click();
	//		submit.click();
	//	}
	//	public void registerWithMissingPassword (String firstName,String surname,String email,
	//			String emailConfirmation,String day , String month,String year, String gender)
	//	{
	//		setText(FirstNameTxtBox, firstName);
	//		setText(LastNameTxtBox, surname);
	//		setText(RegMailTxtBox, email);
	//		setText(RegMailConfirmation, emailConfirmation);
	//		select(DayList, day);
	//		select(MonthList, month);
	//		select(YearList, year);
	//		if (gender =="male")
	//			male.click();
	//		else if(gender=="female")
	//			female.click();
	//		else 
	//			male.click();
	//		submit.click();
	//	}
	//	public void registerWithMissingGender (String firstName,String surname,String email,
	//			String emailConfirmation,String password,String day , String month,String year)
	//	{
	//		setText(FirstNameTxtBox, firstName);
	//		setText(LastNameTxtBox, surname);
	//		setText(RegMailTxtBox, email);
	//		setText(RegMailConfirmation, emailConfirmation);
	//		setText(PasswordTxtBox, password);
	//		select(DayList, day);
	//		select(MonthList, month);
	//		select(YearList, year);
	//		submit.click();
	//	}
}
