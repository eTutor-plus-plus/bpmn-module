package ETutor.dto.entities;

import ETutor.dto.entities.testEngine.TestEngineRuntimeDTO;

public class TestEngineDTO {
    public TestEngineRuntimeDTO testEngineRuntimeDTO;

    public TestEngineDTO(long id) {
        this.testEngineRuntimeDTO = new TestEngineRuntimeDTO();
    }

    @Override
    public String toString() {
        return "TestEngineDTO{" +
                "testEngineRuntimeDTO=" + testEngineRuntimeDTO +
                '}';
    }
}
