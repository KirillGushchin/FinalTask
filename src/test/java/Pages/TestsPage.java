package Pages;

import Driver.Driver;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class TestsPage extends Form {
    private final String LOC_LIST_OF_TESTS = "//table[contains(@id,'allTests')]//tr";
    private final String LOC_START_DATE = "/td[4]";
    private final String LOC_TEST_NAME = "/td[1]//a";
    private  String LOC_TEST = "//a[contains(text(),'')]";
    private final IButton BUT_HOME = getElementFactory().getButton(By.xpath("//a[contains(text(),'Home')]"), "Home button");
    private final int WAITING_TIME = 120;
    private final int POSITION_FOR_INSERT = 21;
    public TestsPage(By locator, String name) {
        super(locator, name);
    }

    public List<IElement> getAllTestsOnPage(){
        new WebDriverWait(Driver.getBrowser().getDriver(), WAITING_TIME)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(LOC_LIST_OF_TESTS)));
        List<IElement> listOfTests = getElementFactory().findElements(By.xpath(LOC_LIST_OF_TESTS),
                ElementType.LABEL,
                ElementsCount.ANY,
                ElementState.DISPLAYED);
        listOfTests.remove(0);
        return listOfTests;
    }
    public String getStartDateFromTests(IElement element){
        return element.findChildElement(By.xpath(LOC_START_DATE), ElementType.LABEL).getText();
    }
    public String getTestName(IElement element){
        return element.findChildElement(By.xpath(LOC_TEST_NAME), ElementType.LABEL).getText();
    }
    public void clickHomeButton(){
        BUT_HOME.click();
    }

    public void openTestByName(String testName){
        LOC_TEST = String.valueOf(new StringBuilder(LOC_TEST).insert(POSITION_FOR_INSERT, testName));
        new WebDriverWait(Driver.getBrowser().getDriver(), WAITING_TIME)
                .until(ExpectedConditions
                        .elementToBeClickable(By.xpath(LOC_TEST)));
        IButton button = getElementFactory().getButton(By.xpath(LOC_TEST),
                "Open" + testName);
        button.click();
    }
}
