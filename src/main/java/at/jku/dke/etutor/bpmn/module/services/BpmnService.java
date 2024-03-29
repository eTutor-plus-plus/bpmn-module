package at.jku.dke.etutor.bpmn.module.services;

import at.jku.dke.etutor.bpmn.module.config.ApplicationProperties;
import at.jku.dke.etutor.bpmn.module.dto.entities.TestConfigDTO;
import at.jku.dke.etutor.bpmn.module.dto.entities.testEngine.TestEngineDTO;
import at.jku.dke.etutor.bpmn.module.services.rules.tasks.EngineTaskService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class BpmnService {
    private static final Logger logger = LoggerFactory.getLogger(BpmnService.class);
    private static long counter;

    //    private final ProcessEngine processEngine;
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    //Custom services
    private final EngineTaskService engineTaskService;
    private ProcessInstance instance;
    //    private ProcessInstance instance2;
    private final ApplicationProperties applicationProperties;

    public BpmnService(ApplicationProperties applicationProperties, RuntimeService runtimeService, TaskService taskService, EngineTaskService engineTaskService) {
        counter = 0;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.engineTaskService = engineTaskService;
        this.applicationProperties = applicationProperties;
    }

    public TestEngineDTO startTest(String key, TestConfigDTO testConfig) {
        TestConfigDTO testConfigDTO;
        try {
            this.startProcess(key);
        } catch (Exception e) {
            e.printStackTrace();
            counter++;
            return new TestEngineDTO(counter);
        }
        TestEngineDTO testEngineDTO;

        // TODO build stub XML-valid check
        try {
            if (testConfig == null) {
                throw new Exception("no Config");
            } else testConfigDTO = testConfig;
            testEngineDTO = this.startTestEngine(key, testConfigDTO);
        } catch (Exception e) {
            logger.warn("Failed: Exception " + e.getMessage());
            if (runtimeService.createProcessInstanceQuery().list().size() != 0) {
                runtimeService.deleteProcessInstance(instance.getId(), null);
            }
            counter++;
            return new TestEngineDTO(counter);
        }
        logger.info(testEngineDTO.toString());
        return testEngineDTO;
    }

    private void startProcess(String key) throws Exception {
        if (runtimeService.createProcessInstanceQuery().list().size() > 0) {
            runtimeService.createProcessInstanceQuery().list().forEach((storedInstance) -> {
                runtimeService.deleteProcessInstance(storedInstance.getId(), null);
            });
        }
        logger.info("Start Process by: " + key);
        instance = runtimeService.startProcessInstanceByKey(key);
        if (instance == null) throw new Exception("No Process with Process ID: " + key + " is deployed");
    }

    private void deleteProcess(String key, TestEngineDTO testEngineDTO, boolean testLastTask) {
        logger.info("Delete Process by: " + key);
        if (runtimeService.createProcessInstanceQuery().list().size() == 0) {
            if (testLastTask) testEngineDTO.testEngineRuntimeDTO.setCanReachLastTask(true);
        } else {
            runtimeService.deleteProcessInstance(instance.getId(), null);
        }
    }

    private TestEngineDTO startTestEngine(String key, TestConfigDTO testConfigDTO) throws Exception {
        TestEngineDTO testEngineDTO = new TestEngineDTO(counter);
        counter++;
        if (applicationProperties.getDeployment().isRuntimeStarted()) {
            if (testConfigDTO.getTasksInCorrectOrder() != null && testConfigDTO.getTasksInCorrectOrder().size() != 0) {
                testEngineDTO.testEngineRuntimeDTO.setProcessInOrder(engineTaskService.checkNameInProcessOrder(testConfigDTO.getTasksInCorrectOrder(), testEngineDTO, taskService));
                this.deleteProcess(key, testEngineDTO, true);
            }
            if (testConfigDTO.getLabels() != null && testConfigDTO.getLabels().size() != 0) {
                this.startProcess(key);
                testEngineDTO.testEngineRuntimeDTO.setContainsAllLabels(engineTaskService.tasksInProcess(testConfigDTO.getLabels(), testEngineDTO, taskService));
            }
            this.deleteProcess(key, testEngineDTO, false);
        }
        return testEngineDTO;
    }
}

//    private TestConfigDTO readConfig() throws Exception {
//        try {
//            logger.info("Start read JSON file");
//            TestConfigDTO config = new JsonReader(applicationProperties).readStaticJsonFile();
//            if (config == null) throw new IOException("no config");
//            return config;
//        } catch (IOException e) {
//            logger.warn(e.getMessage());
//        }
//        throw new Exception("no Config");
//    }

//    public boolean startTestWithOutRestJson(String key) {
//        try {
//            this.startProcess(key);
//            TestConfigDTO testConfigDTO = this.readConfig();
//            TestEngineDTO testEngineDTO = this.startTestEngine(key, testConfigDTO);
//            this.deleteProcess(key, testEngineDTO, true);
//            logger.info(testEngineDTO.toString());
//            return true;
//        } catch (Exception e) {
//            logger.warn("Failed: Exception " + e.getMessage());
//            if (instance != null)
//                runtimeService.deleteProcessInstance(instance.getId(), null);
//            return false;
//        }
//    }
