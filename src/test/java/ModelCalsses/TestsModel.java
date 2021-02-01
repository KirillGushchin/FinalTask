package ModelCalsses;

public class TestsModel {
    private final String projectName;
    private final String testName;
    private final String methodName;
    private final String SID;
    private final String ENV;


    public TestsModel(String projectName,String testName, String methodName, String SID, String ENV ){
        this.projectName = projectName;
        this.testName = testName;
        this.methodName = methodName;
        this.SID = SID;
        this.ENV = ENV;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTestName() {
        return testName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getSID() {
        return SID;
    }

    public String getEnv() {
        return ENV;
    }
}
