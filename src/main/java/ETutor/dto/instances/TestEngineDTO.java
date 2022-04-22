package ETutor.dto.instances;

public class TestEngineDTO {
    private final long id;
    private boolean processInOrder = false;
    private boolean processHaveParallelGateway = false;

    public TestEngineDTO(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean isProcessInOrder() {
        return processInOrder;
    }

    public void setProcessInOrder(boolean processInOrder) {
        this.processInOrder = processInOrder;
    }

    public boolean isProcessHaveParallelGateway() {
        return processHaveParallelGateway;
    }

    public void setProcessHaveParallelGateway(boolean processHaveParallelGateway) {
        this.processHaveParallelGateway = processHaveParallelGateway;
    }

    @Override
    public String toString() {
        return "TestEngine{" +
                "id=" + id +
                ", processInOrder=" + processInOrder +
                ", processHaveParallelGateway=" + processHaveParallelGateway +
                '}';
    }
}
