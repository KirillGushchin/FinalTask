package Enums;

public enum APIEndpoints {
    getToken("token/get?"),
    addLogForTest("test/put/log?"),
    addAttachment("test/put/attachment?"),
    addTest("test/put?");

    private final String endpoint;
    APIEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }
    public String getValue() {
        return this.endpoint;
    }
}
