package se.kth.adamfhhugoper.labb3b.model.Matcher;

import se.kth.adamfhhugoper.labb3b.model.Task;
import se.kth.adamfhhugoper.labb3b.model.TaskState;

public class NotDoneMatcher implements ITaskMatcher{

    /**
     * Compares task state
     * @param task The task that is compared
     * @return  true if the TaskState is done
     */
    @Override
    public boolean match(Task task) {

        if(task.getState() != TaskState.DONE) return true;
        return false;
    }
}
