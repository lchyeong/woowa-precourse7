package store.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static store.util.FileUtil.addKeysFromResource;
import static store.util.FileUtil.addValuesToKeys;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileUtilTest {

    private Map<String, String> getMapAddedKeys() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("name", "");
        result.put("buy", "");
        result.put("get", "");
        result.put("start_date", "");
        result.put("end_date", "");
        return result;
    }

    private Map<String, String> getMapAddedValues() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("name", "John");
        result.put("buy", "100");
        result.put("get", "50");
        result.put("start_date", "2023-11-01");
        result.put("end_date", "2023-11-10");
        return result;
    }

    @Test
    @DisplayName("구분자를 기준으로 맵을 생성해 키값을 세팅")
    public void 맵에_키_세팅() {
        Map<String, String> expected = getMapAddedKeys();

        String mockColumns = "name,buy,get,start_date,end_date";
        Map<String, String> actual = addKeysFromResource(mockColumns);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("구분자를 기준으로 맵에 값을 세팅")
    public void 맵에_값_세팅() {
        Map<String, String> expected = getMapAddedValues();

        String mockValues = "John,100,50,2023-11-01,2023-11-10";
        Map<String, String> actual = addValuesToKeys(getMapAddedKeys(), mockValues);

        assertEquals(expected, actual);
    }
}