package at.jku.dke.etutor.bpmn.module.dto.entities.testEngine;

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
