package Utils;

import Enums.APIEndpoints;
import Enums.ContentTypes;
import org.apache.http.HttpResponse;

import java.io.File;


public class SiteAPIUtils {
    private static PropertyUtils propertyUtils = new PropertyUtils("src/test/resources/TestData.properties");

    public static HttpResponse getToken(){
        return APIUtils.postRequest(propertyUtils.getStringPropertyValue("APIBaseURL") + APIEndpoints.getToken.getValue()+
                "variant=" + propertyUtils.getStringPropertyValue("variant"));
    }

    public static HttpResponse addTests(String projectName, String testName, String methodName, String SID, String ENV){
        return APIUtils.postRequest(propertyUtils.getStringPropertyValue("APIBaseURL") + APIEndpoints.addTest.getValue() +
                "projectName=" + projectName + "&testName=" + testName + "&methodName=" + methodName + "&SID=" + SID + "&env=" + ENV);
    }

    public static HttpResponse addLogToPost(String testId, String content){
        return APIUtils.postRequest(propertyUtils.getStringPropertyValue("APIBaseURL") + APIEndpoints.addLogForTest.getValue() +
                "testId=" + testId + "&content=" + content);
    }

    public static HttpResponse addAttachmentToTest(String testId, String endCodeFile){
        return APIUtils.postRequest(propertyUtils.getStringPropertyValue("APIBaseURL") + APIEndpoints.addAttachment.getValue() +
                "testId=" + testId + "&content=" + endCodeFile + "&contentType=" + ContentTypes.image_png.getValue());
    }
}
