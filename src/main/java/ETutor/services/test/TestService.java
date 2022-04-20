package ETutor.services.test;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    private final ProcessEngine processEngine;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public TestService(ProcessEngine processEngine, RuntimeService runtimeService, TaskService taskService) {
        this.processEngine = processEngine;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    public String validate() {
        RuntimeService runtime = processEngine.getRuntimeService();
        return this.checkTeacher(runtime) + this.checkProcess(runtime);
    }

    private boolean checkName(Task task, String solution) {
        return task.getName().equals(solution);
    }

    private String checkTeacher(RuntimeService runtime) {
        ProcessInstance instance = runtime.startProcessInstanceByKey("Teacher");
        TaskService taskservice = processEngine.getTaskService();
        TaskQuery taskQuery = taskservice.createTaskQuery();
        Task task = taskQuery.singleResult();
        logger.info(task.getName());
        boolean result = this.checkName(task, "Task1");
        logger.info(String.valueOf(result));
        logger.info("Before " + taskQuery.list().toString());
        taskservice.complete(task.getId());
        Task task2 = taskQuery.singleResult();
        logger.info(task2.getName());
        boolean result2 = this.checkName(task2, "Task2");
        taskservice.complete(task2.getId());
        logger.info("After " + taskQuery.list().toString());

        return String.valueOf(result + "   " + result2);
    }

    private String checkProcess(RuntimeService runtime) {
        ProcessInstance instance = runtime.startProcessInstanceByKey("BPMN-Modul-process");
        TaskService taskservice = processEngine.getTaskService();
        TaskQuery taskQuery = taskservice.createTaskQuery();
        Task task = taskQuery.singleResult();
        logger.info(task.getName());
        boolean result = this.checkName(task, "a");
        logger.info(String.valueOf(result));
        logger.info("Before " + taskQuery.list().toString());
        taskservice.complete(task.getId());
        Task task2 = taskQuery.singleResult();
        logger.info(task2.getName());
        boolean result2 = this.checkName(task2, "b");
        taskservice.complete(task2.getId());
        logger.info("After " + taskQuery.list().toString());

        return String.valueOf(result + "   " + result2);
    }

}
