package at.jku.dke.etutor.bpmn.module.dto;

import at.jku.dke.etutor.bpmn.module.config.ApplicationProperties;
import at.jku.dke.etutor.bpmn.module.dto.entities.TestConfigDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JsonReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ApplicationProperties applicationProperties;
    private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);

    public JsonReader(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public TestConfigDTO readStaticJsonFile() {
        try {
            File file = new File(applicationProperties.getJsonReader().getJsonTestPath());
            TestConfigDTO testConfigDTO = objectMapper.readValue(file, TestConfigDTO.class);
            logger.info(testConfigDTO.toString());
            return testConfigDTO;
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return null;
    }
}
