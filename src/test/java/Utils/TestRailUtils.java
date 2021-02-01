package Utils;

import Driver.Driver;
import Enums.TestRailEndpoints;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TestRailUtils {
    private static PropertyUtils propertyUtils = new PropertyUtils("src/test/resources/TestRailParameters.properties");
    private static String userName;
    private static String password;
    private static int projectId;
    private static String runName;
    private static int runId;
    private static int caseId;
    private static int resultId;

    public TestRailUtils(String userName, String password, int projectId, String runName, int caseId){
        this.userName = userName;
        this.password = password;
        this.runName = runName;
        this.projectId = projectId;
        this.caseId = caseId;
    }

    public static String logIn(){
        return Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
    }

    public static HttpResponse addResultForCase(int status){
        Map<String, String> body = new HashMap();
        body.put("status_id", String.valueOf(status));
        return APIUtils.postRequest( propertyUtils.getStringPropertyValue("TRUrl")+
                TestRailEndpoints.add_result_for_case.getValue() + runId + "/" + caseId , body ,logIn());
    }

    public static HttpResponse addTestSuiteToTestRun(){
        Map<String, String> body = new HashMap();
        body.put( "name", runName);
        return APIUtils.postRequest(propertyUtils.getStringPropertyValue("TRUrl") +
                TestRailEndpoints.add_to_run.getValue() + projectId, body, logIn());
    }

    public static HttpResponse attachScreenshot(File file, int resultId){
        return APIUtils.postRequest(propertyUtils.getStringPropertyValue("TRUrl") +
                TestRailEndpoints.add_attachment_to_result.getValue() + resultId, file, logIn());
    }

    public static void setRunId(int runId) {
        TestRailUtils.runId = runId;
    }

    public static void setResultId(int resultId) {
        TestRailUtils.resultId = resultId;
    }

    public static int getResultId() {
        return resultId;
    }

    public static void addScreenshot(HttpResponse response){
        File screenShot =  ((TakesScreenshot) Driver.getBrowser().getDriver()).getScreenshotAs(OutputType.FILE);
        JsonNode nod2 = null;
        try {
            nod2 = new ObjectMapper().readTree(new APIUtils().getContent(response));
        } catch (IOException e) {
            System.err.println("Cannot parse json due to following error: "+ e.getMessage());
        }
        TestRailUtils.setResultId(nod2.get("id").asInt());
        TestRailUtils.attachScreenshot(screenShot, TestRailUtils.getResultId());
    }
}
