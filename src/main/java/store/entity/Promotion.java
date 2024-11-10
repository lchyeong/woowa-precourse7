package store.entity;

import java.time.LocalDateTime;
import java.util.Map;
import store.exception.ApiException;
import store.exception.ErrorCode;
import store.util.DateUtil;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = validateBuy(buy);
        this.get = validateGet(get);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Promotion(Map<String, String> data) {
        this.name = data.getOrDefault("name", "");
        this.buy = extractNumber(data.getOrDefault("buy", "0"));
        this.get = extractNumber(data.getOrDefault("get", "0"));
        this.startDate = DateUtil.toDate(data.getOrDefault("start_date", ""));
        this.endDate = DateUtil.toDate(data.getOrDefault("end_date", ""));
    }

    public String getName() {
        return name;
    }

    public int getGet() {
        return get;
    }

    public int getBuy() {
        return buy;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    private int extractNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new ApiException(ErrorCode.NOT_NUMBER);
        }
    }

    private int validateBuy(int buy) {
        if (buy < 1) {
            throw new ApiException(ErrorCode.INVALID_QUANTITY);
        }
        return buy;
    }

    private int validateGet(int get) {
        if (get < 1) {
            throw new ApiException(ErrorCode.INVALID_QUANTITY);
        }
        return get;
    }
}
