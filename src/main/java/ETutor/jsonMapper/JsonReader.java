package ETutor.jsonMapper;

import ETutor.config.ApplicationProperties;
import ETutor.jsonMapper.EntityClasses.TestConfigDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JsonReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);

    public static TestConfigDTO readJsonFile() {
        try {
            TestConfigDTO testConfigDTO = objectMapper.readValue(new File(ApplicationProperties.getJsonTestPath()), TestConfigDTO.class);
            logger.info(testConfigDTO.toString());
            return testConfigDTO;
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return null;
    }
}
