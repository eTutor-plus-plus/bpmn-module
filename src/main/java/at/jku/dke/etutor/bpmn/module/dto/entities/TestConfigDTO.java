package at.jku.dke.etutor.bpmn.module.dto.entities;

import at.jku.dke.etutor.bpmn.module.dto.interfaces.TestConfig_Interface;

import java.util.List;

public class TestConfigDTO implements TestConfig_Interface {
    private List<String> tasksInCorrectOrder;
    private List<String> labels;

    public void setTasksInCorrectOrder(List<String> tasksInCorrectOrder) {
        this.tasksInCorrectOrder = tasksInCorrectOrder;
    }

    public List<String> getTasksInCorrectOrder() {
        return tasksInCorrectOrder;
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
                "taskNames=" + tasksInCorrectOrder +
                ", labels=" + labels +
                '}';
    }
}
