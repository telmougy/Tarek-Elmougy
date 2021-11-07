package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import data.ExcelReader;
import pages.HomePage;

public class LoginTest extends TestBase {
	HomePage HomeObj ;

	@DataProvider(name="validData")
	public Object[][] GetValidData () throws IOException{
		ExcelReader ER = new ExcelReader()  ;
		return ER.getExcelData("loginValidData",3);
	}

	@DataProvider(name="invalidData")
	public Object[][] GetinvalidData () throws IOException{
		ExcelReader ER = new ExcelReader()  ;
		return ER.getExcelData("loginInvalidData",3);
	}

	@Test(dataProvider = "invalidData" ,priority = 2 , enabled = true)
	public void LoginWithinvalidPassword (String email , String password,String errorMessage) { 
		driver.navigate().to("http://www.facebook.com");
		HomeObj = new HomePage(driver);
		HomeObj.LogIn(email, password);
		Assert.assertTrue(HomeObj.errorMessage.getText().contains(errorMessage));
	}

	@Test(dataProvider = "validData",priority = 3 ,enabled = true )
	public void LoginWithValidData (String email , String password,String accountName) throws InterruptedException { 
		driver.close();
		StartDriver("chrome");
		HomeObj = new HomePage(driver);
		HomeObj.LogIn(email, password);
		Assert.assertEquals(HomeObj.accountName.getText(),accountName);

	}

}
