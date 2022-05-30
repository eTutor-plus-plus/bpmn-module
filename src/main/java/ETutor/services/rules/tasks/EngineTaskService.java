package ETutor.services.rules.tasks;

import ETutor.dto.entities.testEngine.TestEngineDTO;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class EngineTaskService {
    private static final Logger logger = LoggerFactory.getLogger(EngineTaskService.class);

    public EngineTaskService() {
        logger.info(logger.getName() + "- is started");
    }

    public boolean checkNameInProcessOrder(List<String> names, TestEngineDTO testEngineDTO, TaskService taskService) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        boolean result = true;
        for (String name : names) {
            name = name.trim();
            if (taskQuery.list().size() == 1) {
                Task task = taskQuery.singleResult();
                if (this.taskNameNotEqual(task, name)) return false;
                taskService.complete(task.getId());
            } else if (taskQuery.list().size() >= 2) {
                result = false;
                testEngineDTO.testEngineRuntimeDTO.setProcessHaveParallelGateway(true);
                for (Task task : taskQuery.list()) {
                    logger.info("Except:" + task.getName() + " compare with " + name);
                    if (task.getName().equals(name)) {
                        taskService.complete(task.getId());
                        result = true;
                    }
                }
            }
            // TODO else path should check process end before last name is check
        }
        return result;
    }

    public boolean tasksInProcess(List<String> names, TestEngineDTO testEngineDTO, TaskService taskService) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Map<String, Boolean> map = new Hashtable<>();
        names.forEach(k -> map.put(k, false));
        while (taskQuery.list().size() != 0) {
            if (taskQuery.list().size() == 1) {
                Task task = taskQuery.singleResult();
                for (String s : map.keySet()) {
                    if (s.equals(task.getName())) {
                        logger.info("Except:" + task.getName());
                        map.put(task.getName(), true);
                    }
                }
                taskService.complete(task.getId());
            } else {
                testEngineDTO.testEngineRuntimeDTO.setProcessHaveParallelGateway(true);
                for (Task task : taskQuery.list()) {
                    for (String s : map.keySet()) {
                        if (s.equals(task.getName())) {
                            map.put(task.getName(), true);
                        }
                    }
                    taskService.complete(task.getId());
                }
            }
        }
        return !map.containsValue(false);
    }

    private boolean taskNameNotEqual(Task task, String name) {
        logger.info("Except:" + task.getName() + " compare with " + name);
        return !task.getName().equals(name);
    }
    //    public boolean findTasks(List<String> names, TaskService taskService) {
//        TaskQuery taskQuery = taskService.createTaskQuery();
//        for (String name : names) {
//            Task t = taskQuery.taskName(name).singleResult();
//            logger.info("Except Find:" + name + "find Task: " + t);
//            if (t == null) return false;
//        }
//        return true;
//    }
}
