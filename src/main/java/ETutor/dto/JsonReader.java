package ETutor.dto;

import ETutor.config.ApplicationProperties;
import ETutor.dto.entities.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JsonReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);

    public static TestConfig readStaticJsonFile() {
        try {
            File file = new File(ApplicationProperties.getJsonTestPath());
            TestConfig testConfigDTO = objectMapper.readValue(file, TestConfig.class);
            logger.info(testConfigDTO.toString());
            return testConfigDTO;
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return null;
    }
}
