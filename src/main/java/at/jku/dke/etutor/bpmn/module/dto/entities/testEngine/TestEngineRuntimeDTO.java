package at.jku.dke.etutor.bpmn.module.dto.entities.testEngine;

public class TestEngineRuntimeDTO {

    private boolean processInOrder = false;
    private boolean containsAllLabels = false;
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

    public boolean isContainsAllLabels() {
        return containsAllLabels;
    }

    public void setContainsAllLabels(boolean containsAllLabels) {
        this.containsAllLabels = containsAllLabels;
    }

    @Override
    public String toString() {
        return "TestEngineRuntimeDTO{" +
                "processInOrder=" + processInOrder +
                ", containsAllLabels=" + containsAllLabels +
                ", processHaveParallelGateway=" + processHaveParallelGateway +
                ", canReachLastTask=" + canReachLastTask +
                '}';
    }
}
