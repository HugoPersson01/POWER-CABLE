package se.kth.adamfhhugoper.labb3b.model.Matcher;

import se.kth.adamfhhugoper.labb3b.model.Task;

public interface ITaskMatcher {

    /**
     * This class gives the user the ability to compare different task parameters,
     * like:
     * task state,
     *  task priority,
     *  task taken by .
     * @param task The task that is compared
     * @return true if task matches
     */

    public boolean match(Task task);
}
