package ETutor.services;

import ETutor.dto.JsonReader;
import ETutor.dto.entities.TestConfigDTO;
import ETutor.dto.entities.TestEngineDTO;
import ETutor.services.rules.user_Task.EngineTaskService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;


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
//    private ApplicationProperties applicationProperties;

    public BpmnService(RuntimeService runtimeService, TaskService taskService, EngineTaskService engineTaskService) {
        counter = 0;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.engineTaskService = engineTaskService;
    }

    public TestEngineDTO startTest(String key, TestConfigDTO testConfig) {
        TestConfigDTO testConfigDTO;
        try {
            this.startProcess(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TestEngineDTO testEngineDTO;
        try {
            if (testConfig == null) {
                throw new Exception("no Config");
            } else testConfigDTO = testConfig;
            testEngineDTO = this.startTestEngine(testConfigDTO);
        } catch (Exception e) {
            logger.warn("Failed: Exception " + e.getMessage());
            if (instance != null)
                runtimeService.deleteProcessInstance(instance.getId(), null);
            return null;
        }
        this.deleteProcess(key, testEngineDTO);
        logger.info(testEngineDTO.toString());
        return testEngineDTO;
    }

    public boolean startTestWithOutRestJson(String key) {
        try {
            this.startProcess(key);
            TestConfigDTO testConfigDTO = this.readConfig();
            TestEngineDTO testEngineDTO = this.startTestEngine(testConfigDTO);
            this.deleteProcess(key, testEngineDTO);
            logger.info(testEngineDTO.toString());
            return true;
        } catch (Exception e) {
            logger.warn("Failed: Exception " + e.getMessage());
            if (instance != null)
                runtimeService.deleteProcessInstance(instance.getId(), null);
            return false;
        }
    }

    private void startProcess(String key) throws Exception {
        logger.info("Start Process by: " + key);
        instance = runtimeService.startProcessInstanceByKey(key);
        if (instance == null) throw new Exception("No Process with Process ID: " + key + " is deployed");
    }

    private void deleteProcess(String key, TestEngineDTO testEngineDTO) {
        logger.info("Delete Process by: " + key);
        // TODO if process end not check this
        if (runtimeService.createProcessInstanceQuery().list().size() == 0) {
            testEngineDTO.testEngineRuntimeDTO.setCanReachLastTask(true);
        } else {
            runtimeService.deleteProcessInstance(instance.getId(), null);
        }
    }

    private TestConfigDTO readConfig() throws Exception {
        try {
            logger.info("Start read JSON file");
            TestConfigDTO config = JsonReader.readStaticJsonFile();
            if (config == null) throw new IOException("no config");
            return config;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        throw new Exception("no Config");
    }

    private TestEngineDTO startTestEngine(TestConfigDTO testConfigDTO) {
        TestEngineDTO testEngineDTO = new TestEngineDTO(counter);
        counter++;
        if (testConfigDTO.taskNames().size() != 0) {
            testEngineDTO.testEngineRuntimeDTO.setProcessInOrder(engineTaskService.checkNameInProcessOrder(testConfigDTO.taskNames(), testEngineDTO, taskService));
        }
        return testEngineDTO;
    }
}

//    public boolean validate() {
//        try {
//            instance = runtimeService.startProcessInstanceByKey("Teacher");
//            instance2 = runtimeService.startProcessInstanceByKey("BPMN-Modul-process");
//            boolean nameTestResult = nameService.checkNameInProcessOrder(List.of("Task1", "Task3      "), taskService);
////            boolean nameTestResult = nameService.findTasks(List.of("Task1", "Task2"), taskService);
//            logger.info("NameTest result: " + nameTestResult);
//            runtimeService.deleteProcessInstance(instance.getId(), null);
//            runtimeService.deleteProcessInstance(instance2.getId(), null);
//            return nameTestResult;
//        } catch (Exception e) {
//            logger.info("Failed: " + e.getMessage());
//            runtimeService.deleteProcessInstance(instance.getId(), null);
//            runtimeService.deleteProcessInstance(instance2.getId(), null);
//            return false;
//        }
//    }
//
//    private boolean checkTaskCount (int count) {
//        logger.info(count + "  " +taskService.createTaskQuery().list().size());
//        return taskService.createTaskQuery().count() == count;
//    }
