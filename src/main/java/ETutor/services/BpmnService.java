package ETutor.services;

import ETutor.jsonMapper.EntityClasses.TestConfigDTODTO;
import ETutor.jsonMapper.JsonReader;
import ETutor.services.user_Task.NameService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BpmnService {
    private static final Logger logger = LoggerFactory.getLogger(BpmnService.class);

    private final ProcessEngine processEngine;
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    //Custom services
    private final NameService nameService;
    private ProcessInstance instance;

    public BpmnService(ProcessEngine processEngine, RuntimeService runtimeService, TaskService taskService) {
        this.processEngine = processEngine;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.nameService = new NameService();
    }

    public boolean validate() {
        try {
            instance = runtimeService.startProcessInstanceByKey("Teacher");
            boolean nameTestResult = nameService.checkNameInProcessOrder(List.of("Task1", "Task3      "), taskService);
//            boolean nameTestResult = nameService.findTasks(List.of("Task1", "Task2"), taskService);
            logger.info("NameTest result: " + nameTestResult);
            runtimeService.deleteProcessInstance(instance.getId(), null);
            return nameTestResult;
        } catch (Exception e) {
            logger.info("Failed: " + e.getMessage());
            runtimeService.deleteProcessInstance(instance.getId(), null);
            return false;
        }
    }

    public boolean useConfig() {
        try {
            instance = runtimeService.startProcessInstanceByKey("Teacher");
            TestConfigDTODTO config = JsonReader.readJsonFile();
            if (config == null) throw new RuntimeException("no config");
            boolean nameTestResult = nameService.checkNameInProcessOrder(config.taskNames(), taskService);
//            boolean nameTestResult = nameService.findTasks(List.of("Task1", "Task2"), taskService);
            logger.info("NameTest result: " + nameTestResult);
            runtimeService.deleteProcessInstance(instance.getId(), null);
            return nameTestResult;
        } catch (Exception e) {
            logger.info("Failed: " + e.getMessage());
            runtimeService.deleteProcessInstance(instance.getId(), null);
            return false;
        }
    }


//
//    private boolean checkTaskCount (int count) {
//        logger.info(count + "  " +taskService.createTaskQuery().list().size());
//        return taskService.createTaskQuery().count() == count;
//    }
}
