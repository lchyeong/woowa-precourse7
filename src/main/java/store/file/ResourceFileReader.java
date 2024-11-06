package store.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import store.exception.ApiException;
import store.exception.ErrorCode;

public class ResourceFileReader {

    public String readFirstLine(String filename) {
        try {
            return new BufferedReader(internalInputStream(filename)).readLine();
        } catch (IOException e) {
            throw new ApiException(ErrorCode.FILE_IS_EMPTY);
        }
    }

    public StringBuilder readAllLines(String filename) {
        try {
            BufferedReader bufferedReader = new BufferedReader(internalInputStream(filename));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder;
        } catch (IOException e) {
            throw new ApiException(ErrorCode.FILE_IS_EMPTY);
        }
    }

    private InputStreamReader internalInputStream(String fileName) {

        ClassLoader classLoader = ResourceFileReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        return new InputStreamReader(inputStream);
    }
}
