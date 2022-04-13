package ETutor.jsonMapper;

import ETutor.jsonMapper.EntityClasses.TestConfigDTODTO;
import ETutor.services.BpmnService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JsonReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(BpmnService.class);

    public static TestConfigDTODTO readJsonFile() {
        try {
            TestConfigDTODTO testConfigDTO = objectMapper.readValue(new File("src/main/resources/JsonTestFiles/testfile.json"), TestConfigDTODTO.class);
            logger.info(testConfigDTO.toString());
            return testConfigDTO;
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return null;
    }
}
