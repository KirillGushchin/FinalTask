package Pages;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class TestDetailsPage extends Form {
    private final ILabel PROJECT_NAME = getElementFactory().getLabel(By.xpath("//h4[contains(text(),'Project name')]/ following-sibling::p"),
            "Project name");
    private final ILabel TEST_NAME = getElementFactory().getLabel(By.xpath("//h4[contains(text(),'Test name')]/ following-sibling::p"),
            "Test name");
    private final ILabel ENVIRONMENT = getElementFactory().getLabel(By.xpath("//h4[contains(text(),'Environment')]/ following-sibling::p"),
            "Environment");
    private final ILabel LOG = getElementFactory().getLabel(By.xpath("//table[contains(@class,'table')]"), "Log");

    public TestDetailsPage(By locator, String name) {
        super(locator, name);
    }

    public String getProjectName(){
        return PROJECT_NAME.getText();
    }

    public String getTestName(){
        return TEST_NAME.getText();
    }

    public String getEnvironment(){
        return ENVIRONMENT.getText();
    }

    public String getLog(){
        return LOG.getText();
    }
}
