package at.jku.dke.etutor.bpmn.module.controller;

import at.jku.dke.etutor.bpmn.module.dto.entities.TestConfigDTO;
import at.jku.dke.etutor.bpmn.module.dto.entities.testEngine.TestEngineDTO;
import at.jku.dke.etutor.bpmn.module.services.BpmnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/bpmn")
public class BpmnController {
    private static final Logger logger = LoggerFactory.getLogger(BpmnController.class);
    private final BpmnService bpmnService;

    public BpmnController(BpmnService service) {
        this.bpmnService = service;
    }

    @PostMapping("")
    public TestEngineDTO startTestEngine(
            @RequestParam String processID,
            @Valid @RequestBody TestConfigDTO testConfigDTO
    ) {
        if (processID == null || processID.isBlank()) return null;
        logger.info("Start Test with ID: " + processID);
        TestEngineDTO testResult = null;
        try {
            logger.info("TestConfig: " + testConfigDTO.toString());
            testResult = bpmnService.startTest(processID, testConfigDTO);
        } catch (Exception e) {
            logger.warn("Failed: Exception " + e.getMessage());
        }
        logger.info("Test result: " + testResult);
        return testResult;
    }
}
//    @GetMapping("")
//    public boolean startTestEngine() {
//        String startkey = "Teacher";
//        logger.info(logger.getName() + "start Test!");
//        boolean testResult = bpmnService.startTestWithOutRestJson(startkey);
//        logger.info("Test result: " + testResult);
//        return testResult;
//    }
