package se.kth.adamfhhugoper.labb3b.model.Matcher;

import se.kth.adamfhhugoper.labb3b.model.Task;
import se.kth.adamfhhugoper.labb3b.model.TaskPrio;

public class PrioMatcher implements ITaskMatcher {
     private TaskPrio taskPrio;

     public PrioMatcher(TaskPrio taskPrio){
         this.taskPrio = taskPrio;
     }

    /**
     * Compares task priority
     * @param task The task that is compared
     * @return true if the taskPrio is the same as the in parameters
     */
    @Override
    public boolean match(Task task) {
         if(taskPrio == task.getPrio()) return true;
        return false;
    }
}
