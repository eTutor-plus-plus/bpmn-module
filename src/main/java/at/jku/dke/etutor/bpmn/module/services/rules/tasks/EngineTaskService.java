package at.jku.dke.etutor.bpmn.module.services.rules.tasks;

import at.jku.dke.etutor.bpmn.module.dto.entities.testEngine.TestEngineDTO;
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

    public boolean checkNameInProcessOrder(List<String> labels, TestEngineDTO testEngineDTO, TaskService taskService) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        boolean result = false;
        for (String label : labels) {
            result = false;
            label = label.trim();
            while (!result) {
                if (taskQuery.list().size() == 1) {
                    Task task = taskQuery.singleResult();
                    if (this.taskNameEqual(task, label)) result = true;
                    taskService.complete(task.getId());
                } else if (taskQuery.list().size() >= 2) {
                    testEngineDTO.testEngineRuntimeDTO.setProcessHaveParallelGateway(true);
                    for (Task task : taskQuery.list()) {
                        logger.info("Except:" + task.getName() + " compare with " + label);
                        if (task.getName().equals(label)) {
                            result = true;
                        }
                        taskService.complete(task.getId());
                    }
                } else {
                    return false;
                }
            }
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

    private boolean taskNameEqual(Task task, String name) {
        logger.info("Except:" + task.getName() + " compare with " + name);
        return task.getName().equals(name);
    }
}
