package Tests;

import Driver.Driver;
import ModelCalsses.TestsModel;
import Steps.Steps;

import Utils.TextGenerator;
import aquality.selenium.elements.interfaces.IElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;

@Listeners(Utils.Listener.class)
public class TestRunner extends BaseTest {
    private final String EXPECTED_PROJECT_PAGE_TEXT = "Available projects";
    private final String NEW_PROJ_NAME = propertyUtils.getStringPropertyValue("TestProjectName");
    private final String EXPECTED_POP_UP_MESSAGE  = "Project "+NEW_PROJ_NAME+" saved";
    private final int TEXT_LENGTH = 5;
    private final SoftAssert softAssert = new SoftAssert();


    @Test
    public void testRunner() {
       String token = Steps.getSteps().getToken();
       String startURL  = Steps.getSteps().loginViaBasic(propertyUtils.getStringPropertyValue("WEBStartURL"),
               propertyUtils.getStringPropertyValue("Login"),
               propertyUtils.getStringPropertyValue("Password"));
       System.out.println(startURL);
       Driver.getBrowser().goTo(startURL);
       Steps.getSteps().addCookie(token);
       Steps.getSteps().refreshPage();
       Assert.assertTrue(Steps.getSteps().getTitleText().contains(EXPECTED_PROJECT_PAGE_TEXT), "Projects page validation");
       Assert.assertTrue(Steps.getSteps().getVariantFromFooter().contains(propertyUtils.getStringPropertyValue("variant")),
               "Check that correct variant is displayed");
       Steps.getSteps().openProjectByName(propertyUtils.getStringPropertyValue("Project"));
       List<IElement> listOfTestsFromPage = Steps.getSteps().getTestsFromPage();
       List<String> testDates = Steps.getSteps().getStartDateFromTestsList(listOfTestsFromPage);
       List<String> sortedTestDates = Steps.getSteps().sortElementsDescending(testDates);
       Assert.assertEquals(testDates, sortedTestDates, "Tests order validation");
       List<String> listOfTestsFromDB = Steps.getSteps().getTestsFromDB(propertyUtils.getStringPropertyValue("DBURL"),
                propertyUtils.getStringPropertyValue("DBLogin"),
                propertyUtils.getStringPropertyValue("DBPassword"));
       List<String> testNames = Steps.getSteps().getTestNameFromTestsList(listOfTestsFromPage);
       Assert.assertTrue(Steps.getSteps().compareTests(listOfTestsFromDB, testNames), "Compare tests between BD and web page");
       Steps.getSteps().goToHomepage();
       Steps.getSteps().clickAddProject();
       Steps.getSteps().enterProjectName(NEW_PROJ_NAME);
       Steps.getSteps().clickSaveProject();
       Assert.assertTrue(Steps.getSteps().isPopUpDisplayed(), "Check that pop up is displayed");
       String popUpMessage = Steps.getSteps().getPopUpMessage();
       Steps.getSteps().closePopUP();
       Assert.assertFalse(Steps.getSteps().isPopUpDisplayed(),"Check that pop up isn't displayed");
       Driver.getBrowser().refresh();
       Assert.assertEquals(popUpMessage, EXPECTED_POP_UP_MESSAGE, "Pop up message validation");
       Steps.getSteps().openProjectByName(NEW_PROJ_NAME);
       TestsModel test = new TestsModel(NEW_PROJ_NAME, TextGenerator.generateText(TEXT_LENGTH),
               TextGenerator.generateText(TEXT_LENGTH),
               TextGenerator.generateText(TEXT_LENGTH),
               TextGenerator.generateText(TEXT_LENGTH));
       String createdTestId = Steps.getSteps().addTestsViaAPI(test);
       String log = TextGenerator.generateText(TEXT_LENGTH);
       Steps.getSteps().addLogsToTestViaAPI(createdTestId, log);
       Steps.getSteps().openTestByName(test.getTestName());
       softAssert.assertEquals(Steps.getSteps().getProjectNameFromTestDetailsPage(), test.getProjectName(),
               "Check that project name is correct");
       softAssert.assertEquals(Steps.getSteps().getTestNameFromTestDetailsPage(), test.getTestName(),
               "check that test name is correct");
       softAssert.assertEquals(Steps.getSteps().getEnvironmentFromTestDetailsPage(), test.getEnv(),
               "Check that environment is correct");
       softAssert.assertEquals(Steps.getSteps().getLogFromTestDetailsPage(),log,
               "Check that log is correct");
       softAssert.assertAll();






    }
}
