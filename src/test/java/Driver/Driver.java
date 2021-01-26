package Driver;

import Utils.PropertyUtils;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class Driver {
    private static Browser browser;
    private static PropertyUtils propertyUtils = new PropertyUtils("src/test/resources/DrverParameters.properties");


    public void setUp(){
        Driver.getBrowser().setImplicitWaitTimeout(Duration.ofMinutes(propertyUtils.getIntPropertyValue("ImplictWat")));
        Driver.getBrowser().setPageLoadTimeout(Duration.ofMinutes(propertyUtils.getIntPropertyValue("PageLoadTimeOut")));
    }
    public static void addCookie(Cookie cookie){
        browser.getDriver().manage().addCookie(cookie);
    }
    public static void deleteAllCookies(){
        browser.getDriver().manage().deleteAllCookies();
    }

    public static Alert switchToAlert(){
        return browser.getDriver().switchTo().alert();
    }
    public static String getAlertMessage(Alert alert){
        return alert.getText();
    }
    public static boolean isAlertDisplayed(){
        try{
        new WebDriverWait(Driver.getBrowser().getDriver(), propertyUtils.getIntPropertyValue("AlertTimeOut"))
                .until(ExpectedConditions.alertIsPresent());
            return true;
        }catch (UnhandledAlertException | TimeoutException ex){
            System.err.println("Alert isn't displayed. Error: " +ex.getMessage());
            return false;
        }
    }

    public static String takeScreenShot(){
        return  ((TakesScreenshot) browser.getDriver()).getScreenshotAs(OutputType.BASE64);
    }

    public static Browser getBrowser(){
        if (browser == null){
            browser = AqualityServices.getBrowser();
        }
        return browser;
    }

}
