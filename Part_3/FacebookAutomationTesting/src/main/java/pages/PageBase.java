package pages;

import java.nio.channels.Selector;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PageBase {

	protected WebDriver driver ;

	public PageBase (WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
public static void clickButton (WebElement button) {
	
	button.click();
}
public static void setText (WebElement textElement, String value)
{
	textElement.sendKeys(value);
}
public static void select(Select selector,String visibleText) {
	selector.selectByVisibleText(visibleText);
}
}
