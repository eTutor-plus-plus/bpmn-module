package ETutor.jsonMapper.EntityClasses;

import ETutor.jsonMapper.interfaces.TestConfigDTO_Interface;

import java.util.List;
import java.util.Objects;

public class TestConfigDTO implements TestConfigDTO_Interface {
    private List<String> taskNames;

    public List<String> taskNames() {
        return taskNames;
    }

    public void setTaskNames(List<String> taskNames) {
        this.taskNames = taskNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestConfigDTO)) return false;
        TestConfigDTO that = (TestConfigDTO) o;
        return Objects.equals(taskNames, that.taskNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskNames);
    }

    @Override
    public String toString() {
        return "TestConfig{" +
                "taskNames=" + taskNames +
                '}';
    }
}