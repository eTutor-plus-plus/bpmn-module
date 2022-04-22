package ETutor.dto.entities;

import ETutor.dto.interfaces.TestConfig_Interface;

import java.util.List;

public class TestConfig implements TestConfig_Interface {
    private List<String> taskNames;

    public List<String> taskNames() {
        return taskNames;
    }

    public void setTaskNames(List<String> taskNames) {
        this.taskNames = taskNames;
    }

    @Override
    public String toString() {
        return "TestConfig{" +
                "taskNames=" + taskNames +
                '}';
    }
}
