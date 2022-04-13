package ETutor.services.user_Task;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NameService {
    private static final Logger logger = LoggerFactory.getLogger(NameService.class);

    public NameService() {
        logger.info(logger.getName() + "- is started");
    }

    public boolean checkNameInProcessOrder(List<String> names, TaskService taskService) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        for (String name : names) {
            name = name.trim();
            if (taskQuery.list().size() == 1) {
                Task task = taskQuery.singleResult();
                if (this.taskNameNotEqual(task, name)) return false;
                taskService.complete(task.getId());
            } else {
                for (Task task : taskQuery.list()) {
                    logger.info("Except:" + task.getName() + " compare with " + name);
                    if (task.getName().equals(name)) {
                        taskService.complete(task.getId());
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
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

    private boolean taskNameNotEqual(Task task, String name) {
        logger.info("Except:" + task.getName() + " compare with " + name);
        return !task.getName().equals(name);
    }
}
