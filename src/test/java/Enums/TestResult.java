package Enums;

public enum TestResult {
    Passed(1),
    Failed(5);

    private final int status;
    TestResult(int i) {
        this.status = i;
    }
    public int getValue(){
        return status;
    }
}