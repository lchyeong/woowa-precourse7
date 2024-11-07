package store.promotion.entity;

public class Promotion {
    private final String name;
    private final String buy;
    private final String get;
    private final String startDate;
    private final String endDate;

    public Promotion(String name, String buy, String get, String startDate, String endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }
}
