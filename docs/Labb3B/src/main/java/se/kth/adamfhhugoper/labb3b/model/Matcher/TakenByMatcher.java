package se.kth.adamfhhugoper.labb3b.model.Matcher;

import se.kth.adamfhhugoper.labb3b.model.Task;

public class TakenByMatcher implements ITaskMatcher{
    private String takenBy;

    public TakenByMatcher(String takenBy){
        this.takenBy = takenBy;
    }

    /**
     * Compares the task TakenBy 
     * @param task The task that is compared
     * @return true if the assigned person is the same as the one in the in parameters
     */
    @Override
    public boolean match(Task task) {
        if (takenBy == task.getTakenby()) return true;
        return false;
    }
}
