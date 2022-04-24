package ETutor.dto.entities;

import ETutor.dto.entities.testEngine.TestEngineRuntimeDTO;

public class TestEngineDTO {
    private final long id;
    public TestEngineRuntimeDTO testEngineRuntimeDTO;

    public TestEngineDTO(long id) {
        this.id = id;
        this.testEngineRuntimeDTO = new TestEngineRuntimeDTO();
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TestEngineDTO{" +
                "id=" + id +
                ", testEngineRuntimeDTO=" + testEngineRuntimeDTO.toString() +
                '}';
    }
}
