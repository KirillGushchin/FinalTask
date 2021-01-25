package Pages;

import Driver.Driver;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ProjectsPage extends Form {
    private final ILabel TEXT_TITLE  = getElementFactory().getLabel(By.xpath("//div[contains(@class,'panel-heading')]"),
            "Projects page title");
    private final ILabel TEXT_VARIANT  = getElementFactory().getLabel(By.xpath("//p[contains(@class,'text-muted text-center footer-text')]//span"),
            "Variant number");
    private final String LOC_LIST_OF_PROJECTS = "//div[contains(@class,'list-group')]//a[contains(@class,'list-group-item')]";
    private final IButton BUT_ADD_PROJECT = getElementFactory().getButton(By.xpath("//button[contains(text(),'+Add')]"),
            "Add button");
    public final ITextBox INTEXT_PROJECT_NAME = getElementFactory().getTextBox(By.xpath("//input[contains(@name,'projectName')]"),
            "Project name input field", ElementState.DISPLAYED);
    public final IButton BUT_SAVE_PROJECT = getElementFactory().getButton(By.xpath("//button[contains(@type,'submit')]"),
            "Save project button");



    public ProjectsPage(By locator, String name) {
        super(locator, name);
    }

    public String getTitleText(){
        return  TEXT_TITLE.getText();
    }
    public String getVariantNumber(){
        return TEXT_VARIANT.getText();
    }
    public List<IElement> getProjects() {
        return getElementFactory().findElements(By.xpath(LOC_LIST_OF_PROJECTS),ElementType.BUTTON);
    }
    public void clickAddProject(){
        BUT_ADD_PROJECT.click();
    }
    public void enterProjectName(String projName){
        INTEXT_PROJECT_NAME.sendKeys(projName);
    }
    public void clickSaveProject(){
        BUT_SAVE_PROJECT.click();
    }
    public void closePopUp(){
        try {
            Driver.getBrowser().executeScript("closePopUp()");
        }catch (UnhandledAlertException ex){
            System.out.println("Close pop up using js");
        }
    }


}
