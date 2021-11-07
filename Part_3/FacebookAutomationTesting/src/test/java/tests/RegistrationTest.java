package tests;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import data.ExcelReader;
import pages.HomePage;
import pages.RegistrationPage;

public class RegistrationTest extends TestBase{

	HomePage homeObj ;
	RegistrationPage registrationObj ;

	@DataProvider(name="getValidRegstrationData")
	public Object[][] getValidRegstrationData () throws IOException{
		ExcelReader ER = new ExcelReader()  ;
		return ER.getExcelData("RegisterationValidData",9);
	}
	
	@DataProvider(name="getInvalidRegistrationData")
	public Object[][] invalidData () throws IOException{
		ExcelReader ER = new ExcelReader()  ;
		return ER.getExcelData("RegistrationInvalidData",11);
	}
	
	
	@Test(dataProvider = "getValidRegstrationData",enabled =true,priority = 1)
	public void registerWithValidData(String firstName,String lastName, String email , String confirmEmail
			,String password,String day, String month, String year,String gender)  {
	
		driver.navigate().to("http://www.facebook.com");
		homeObj= new HomePage(driver);
		homeObj.registrationBtn.click();
		registrationObj= new RegistrationPage(driver);
		day= ParsString(day);
		year= ParsString(year);
		registrationObj.register(firstName, lastName, email, confirmEmail, password, day, month, year, gender);
		assertEquals(registrationObj.confirmationMessage.getText(),"To confirm your account, enter the 5-digit code that we sent to: instabug@instabug.com.");
		homeObj.logOutBtn.click();
	}
	@Test(enabled =false)
	public void registrWithInvalidData() {
		
	}
	
	@Test(dataProvider = "getInvalidRegistrationData",enabled = true ,priority = 2)
	public void registrWithIncompleteData( String dataFlag,String  firstName,String lastName, String email , String confirmEmail
			,String password,String day, String month, String year,String gender, String errorMessage) {
		driver.navigate().to("http://www.facebook.com");
		homeObj= new HomePage(driver);
		homeObj.registrationBtn.click();
		registrationObj= new RegistrationPage(driver);
		day= ParsString(day);
		year= ParsString(year);
		registrationObj.registerWithInvalidData(dataFlag,firstName,lastName, email, confirmEmail, 
				password, day, month, year, gender);
		assertEquals(registrationObj.errorContextMessage.getText(), errorMessage);
		
		
	}

	public String ParsString(String value )
	{

		String[] values = value.split("[.]");
		String parsedValue= values[0];
		return parsedValue;
	}
}
