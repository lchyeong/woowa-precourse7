package store.file;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.constants.Delimiter;

public class FileUtil {

    public Map<String, String> addKeysFromResource(String resource) {
        List<String> columns = Delimiter.COMMA.splitDelimiter(resource);
        Map<String, String> result = new LinkedHashMap<>();
        for (String column : columns) {
            result.put(column, "");
        }
        return result;
    }

    public Map<String, String> setValues(Map<String, String> input, String values) {

        int i = 0;
        List<String> splitValues = Delimiter.COMMA.splitDelimiter(values);
        for (String key : input.keySet()) {
            if (i < splitValues.size()) {
                input.put(key, splitValues.get(i));
                i++;
            }
        }
        return input;
    }
}
