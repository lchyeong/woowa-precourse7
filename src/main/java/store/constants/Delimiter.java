package store.constants;

import java.util.Arrays;
import java.util.List;

public enum Delimiter {
    COMMA(",");

    private final String delimiter;

    Delimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public List<String> splitDelimiter(String input) {
        return Arrays.asList(input.split(delimiter));
    }
}
