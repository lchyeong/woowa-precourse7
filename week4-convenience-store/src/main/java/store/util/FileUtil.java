package store.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.constants.Delimiter;

public class FileUtil {

    public static Map<String, String> addKeysFromResource(String resource) {
        List<String> columns = Delimiter.COMMA.splitDelimiter(resource);
        Map<String, String> headLine = new LinkedHashMap<>();
        for (String column : columns) {
            headLine.put(column, "");
        }
        return headLine;
    }

    public static Map<String, String> addValuesToKeys(Map<String, String> headLine, String values) {

        int i = 0;
        List<String> splitValues = Delimiter.COMMA.splitDelimiter(values);
        for (String key : headLine.keySet()) {
            if (i < splitValues.size()) {
                headLine.put(key, splitValues.get(i));
                i++;
            }
        }
        return headLine;
    }
}
