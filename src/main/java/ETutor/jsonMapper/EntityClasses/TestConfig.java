package ETutor.jsonMapper.EntityClasses;

import ETutor.jsonMapper.interfaces.TestConfig_Interface;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class TestConfig implements TestConfig_Interface, Serializable {
    private boolean nameTest;
    private List<String> taskNames;

    public TestConfig(boolean nameTest, List<String> taskNames) {
        this.nameTest = nameTest;
        this.taskNames = taskNames;
    }

    public TestConfig() {
    }

    @Override
    public boolean nameTest() {
        return nameTest;
    }

    @Override
    public List<String> taskNames() {
        return taskNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestConfig)) return false;
        TestConfig that = (TestConfig) o;
        return nameTest == that.nameTest && Objects.equals(taskNames, that.taskNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameTest, taskNames);
    }

    @Override
    public String toString() {
        return "TestConfig{" +
                "nameTest=" + nameTest +
                ", taskNames=" + taskNames +
                '}';
    }
}
