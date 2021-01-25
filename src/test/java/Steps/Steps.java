package Steps;

import Driver.Driver;
import ModelCalsses.TestsModel;
import Pages.ProjectsPage;
import Pages.TestDetailsPage;
import Pages.TestsPage;
import Utils.APIUtils;
import Utils.DBUtils;
import Utils.SiteAPIUtils;
import aquality.selenium.elements.interfaces.IElement;
import org.apache.http.HttpResponse;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Steps {
    private static Steps steps;
    private final String LABEL_TEST_DETAILS = "//div[contains(text(),'Common info')]";
    private final TestDetailsPage testDetailsPage = new TestDetailsPage(By.xpath(LABEL_TEST_DETAILS), "Test details");
    private final String LABEL_PROJECT_PAGE = "//div[contains(@class,'panel-heading')]";
    private final String LABEL_TESTS_PAGE = "//a[contains(text(),'All running tests')]";
    private final TestsPage testsPage = new TestsPage(By.xpath(LABEL_TESTS_PAGE), "Tests page");
    private ProjectsPage projectsPage = new ProjectsPage(By.xpath(LABEL_PROJECT_PAGE), "Projects page");
    private final int POSITION = 7;
    private final String QUERY_NEXAGE_TESTS_FILE_PATH = "src/test/resources/GetAllNexageTests.sql";

    public String loginViaBasic(String url, String login, String password){
        String preparedCredentials = login + ":" + password + "@";
        return String.valueOf(new StringBuilder(url).insert(POSITION, preparedCredentials));
    }
    public void addCookie(String token){
        Cookie cookie = new Cookie("token", token);
        Driver.getBrowser().getDriver().manage().deleteAllCookies();
        Driver.deleteAllCookies();
        Driver.addCookie(cookie);
    }
    public void refreshPage(){
        Driver.getBrowser().refresh();
    }
    public String getTitleText(){
        return projectsPage.getTitleText();
    }
    public String getVariantFromFooter(){
        return projectsPage.getVariantNumber();
    }

    public List<IElement> getTestsFromPage(){
        return testsPage.getAllTestsOnPage();
    }


    public void openProjectByName(String projectName) {
        List<IElement> projects = projectsPage.getProjects();
        for (IElement project : projects) {
            if (project.getText().equals(projectName)) {
                project.click();
                break;
            }
        }
    }

    public void openTestByName(String testName) {
        testsPage.openTestByName(testName);
    }
    public List<String> getTestsFromDB(String url, String user, String password) {
        DBUtils dbUtils = new DBUtils(url, user, password);
        ResultSet rs=  dbUtils.runQUERY(QUERY_NEXAGE_TESTS_FILE_PATH);
        List<String> listOfTestsDB = new ArrayList<String>();
        try {
            while (rs.next()) {
                listOfTestsDB.add(rs.getString(1));
                }
            } catch (SQLException throwables) {
                System.err.println( "Can't get row due to followong error" + throwables.getMessage());
            }
        return listOfTestsDB;
    }

    public List<String> getStartDateFromTestsList(List<IElement> listOfElements){
        List<String> dates = new ArrayList<>();
        for (IElement test : listOfElements) {
            dates.add(testsPage.getStartDateFromTests(test));
        }
        return dates;
    }

    public List<String> sortElementsDescending(List<String> listOfElements){
        List<String> result = new ArrayList<>(listOfElements);
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }

    public List<String> getTestNameFromTestsList(List<IElement> listOfElements){
        List<String> names  = new ArrayList<>();
        for (IElement tests : listOfElements){
            names.add(testsPage.getTestName(tests));
        }
        return names;
    }

    public boolean compareTests(List<String> DBNames, List<String> pageNames){
        boolean result = false;
        for(String page : pageNames){
            for(String db : DBNames){
                if (page.equals(db)){
                    result = true;
                    break;
                }else{
                    result = false;
                }
            }
            if(!result){
                break;
            }
        }
        return result;
    }
    public String getToken(){
        return APIUtils.getContent(SiteAPIUtils.getToken());
    }

    public void goToHomepage(){
        testsPage.clickHomeButton();
    }
    public void clickAddProject(){
        projectsPage.clickAddProject();
    }
    public void clickSaveProject(){
        projectsPage.clickSaveProject();
    }
    public void enterProjectName(String projName){
        projectsPage.enterProjectName(projName);
    }
    public void closePopUP(){
        projectsPage.closePopUp();
    }
    public boolean isPopUpDisplayed(){
        return Driver.isAlertDisplayed();
    }
    public String getPopUpMessage(){
        Alert alert = Driver.switchToAlert();
        return Driver.getAlertMessage(alert);
    }
    public String addTestsViaAPI(TestsModel testsModel){
        HttpResponse response = SiteAPIUtils.addTests(testsModel.getProjectName(), testsModel.getTestName(), testsModel.getMethodName(),
                testsModel.getSID(), testsModel.getEnv());
        return APIUtils.getContent(response);
    }
    public void addLogsToTestViaAPI(String testId, String logContent){
        SiteAPIUtils.addLogToPost(testId, logContent);
    }
    public void addAttachmentToTestViaApi(String testId, File file){
        SiteAPIUtils.addAttachmentToTest(testId,file);
    }

    public String getProjectNameFromTestDetailsPage(){
        return testDetailsPage.getProjectName();
    }
    public String getTestNameFromTestDetailsPage(){
        return testDetailsPage.getTestName();
    }
    public String getEnvironmentFromTestDetailsPage(){
        return testDetailsPage.getEnvironment();
    }
    public String getLogFromTestDetailsPage(){
        return testDetailsPage.getLog();
    }


    public static Steps getSteps() {
        if(steps == null)
            steps = new Steps();
        return steps;
    }
}
