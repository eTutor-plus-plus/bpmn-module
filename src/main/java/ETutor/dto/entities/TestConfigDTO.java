package ETutor.dto.entities;

import ETutor.dto.interfaces.TestConfig_Interface;

import java.util.List;

public class TestConfigDTO implements TestConfig_Interface {
    private List<String> taskNames;
    private List<String> labels;

    public void setTaskNames(List<String> taskNames) {
        this.taskNames = taskNames;
    }

    public List<String> getTaskNames() {
        return taskNames;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "TestConfigDTO{" +
                "taskNames=" + taskNames +
                ", labels=" + labels +
                '}';
    }
}
