package Tests;

import Driver.Driver;
import Utils.APIUtils;
import Utils.PropertyUtils;
import Utils.TestRailUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

public abstract class BaseTest {
    Driver driver = new Driver();
    protected PropertyUtils propertyUtils = new PropertyUtils("src/test/resources/TestData.properties");
    protected PropertyUtils TRpropertyUtils = new PropertyUtils("src/test/resources/TestRailParameters.properties");

    @BeforeTest
    public void beforeMethod(){
        driver.setUp();
        Driver.getBrowser().maximize();
        TestRailUtils testRailUtils = new TestRailUtils(TRpropertyUtils.getStringPropertyValue("login"),
                TRpropertyUtils.getStringPropertyValue("password"),
                TRpropertyUtils.getIntPropertyValue("projectId"),
                TRpropertyUtils.getStringPropertyValue("runName"),
                TRpropertyUtils.getIntPropertyValue("caseId"));
        HttpResponse suite =  TestRailUtils.addTestSuiteToTestRun();
        JsonNode nod = null;
        try {
            nod = new ObjectMapper().readTree(new APIUtils().getContent(suite));
        } catch (IOException e) {
            System.err.println("Cannot parse json due to following error: "+ e.getMessage());
        }
        TestRailUtils.setRunId(nod.findValue("id").asInt());

    }

    @AfterTest
    public void afterMethod(){
    //Driver.getBrowser().quit();
    }
}
