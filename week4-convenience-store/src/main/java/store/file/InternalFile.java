package store.file;

public enum InternalFile {
    PRODUCTS("products.md"),
    PROMOTIONS("promotions.md");

    private final String fileName;

    InternalFile(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
