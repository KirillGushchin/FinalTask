package Enums;

public enum TestRailEndpoints {
    add_to_run("add_run/"),
    add_result_for_case("add_result_for_case/"),
    add_attachment_to_result("add_attachment_to_result/");

    private final String endpoint;
    TestRailEndpoints(String s) {
        this.endpoint = s;
    }
    public String getValue(){
        return endpoint;
    }
}