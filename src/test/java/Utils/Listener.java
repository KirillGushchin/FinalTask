package Utils;

import Enums.TestRailEndpoints;
import Enums.TestResult;
import org.apache.http.HttpResponse;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        HttpResponse response = TestRailUtils.addResultForCase(TestResult.Passed.getValue());
        TestRailUtils.addScreenshot(response);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        HttpResponse response = TestRailUtils.addResultForCase(TestResult.Failed.getValue());
        TestRailUtils.addScreenshot(response);
    }
}
