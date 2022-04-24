package ETutor.dto.entities.testEngine;

public class TestEngineRuntimeDTO {

    private boolean processInOrder = false;
    private boolean processHaveParallelGateway = false;
    private boolean canReachLastTask = false;

    public TestEngineRuntimeDTO() {
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

    public boolean isCanReachLastTask() {
        return canReachLastTask;
    }

    public void setCanReachLastTask(boolean canReachLastTask) {
        this.canReachLastTask = canReachLastTask;
    }

    @Override
    public String toString() {
        return "TestEngineRuntimeDTO{" +
                "processInOrder=" + processInOrder +
                ", processHaveParallelGateway=" + processHaveParallelGateway +
                ", canReachLastTask=" + canReachLastTask +
                '}';
    }
}
