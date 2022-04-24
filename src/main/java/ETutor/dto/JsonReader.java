package ETutor.dto;

import ETutor.config.ApplicationProperties;
import ETutor.dto.entities.TestConfigDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JsonReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);

    public static TestConfigDTO readStaticJsonFile() {
        try {
            File file = new File(ApplicationProperties.getJsonTestPath());
            TestConfigDTO testConfigDTO = objectMapper.readValue(file, TestConfigDTO.class);
            logger.info(testConfigDTO.toString());
            return testConfigDTO;
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return null;
    }
}
