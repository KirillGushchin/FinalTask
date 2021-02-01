package Enums;

public enum ContentTypes {
    image_png("image/png");

    private final String contentType;
    ContentTypes(String endpoint) {
        this.contentType = endpoint;
    }
    public String getValue() {
        return this.contentType;
    }
}
