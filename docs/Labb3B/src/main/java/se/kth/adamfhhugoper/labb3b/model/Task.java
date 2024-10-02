package se.kth.adamfhhugoper.labb3b.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class represents tasks that each project from the project List has.
 */
public class Task implements Comparable<Task>, Serializable {


    private String description;
    private int id;
    private String takenby;
    private TaskState state;
    private LocalDate lastupdate;
    private TaskPrio prio; // Kanske final

    /**
     * This constructs a new task with specified description, id and title
     * All set methods updates the variable "lastupdate", with the current LocalDate.now()
     * @param description, the description for the task
     * @param id, the id for the task
     * @param prio, the priority for the task
     */
     Task(String description, int id, TaskPrio prio) {
        this.description = description;
        this.id = id;
        this.lastupdate = LocalDate.now();
        this.prio = prio;
        this.state = TaskState.TO_DO;
        this.takenby = takenby;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.lastupdate = LocalDate.now();
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.lastupdate = LocalDate.now();
        this.id = id;
    }

    public LocalDate getLastupdate() {
        return lastupdate;
    }

    public TaskPrio getPrio() {
        return prio;
    }

    public void setPrio(TaskPrio prio) {
        this.lastupdate = LocalDate.now();
        this.prio = prio;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.lastupdate = LocalDate.now();
        this.state = state;
    }

    public String getTakenby() {
        return takenby;
    }

    /**
     * A method that assign a person a task
     * @param taker the person that is assigned the task
     * @throws IllegalStateException if the current task already is assigned by another person
     */
    public void setTakenby(String taker)  throws IllegalStateException {
        if (takenby != null) throw new IllegalStateException("Someone already has this Task");
        this.lastupdate = LocalDate.now();
        this.takenby = taker;
    }


    /**
     * Compares the description of this object and the incoming parameter.
     * @param other the object to be compared.
     * @return , 0 if the description of other is the same as this objects description and
     *           < 0 or > 0 if they're not the same
     */
    @Override
    public int compareTo(Task other) {
        int prioCompare = 0;
        if (!equals(other)){
            int descCompare = (description.compareTo(other.description));
            return descCompare;
        } else {
            return prioCompare;
        }
    }
    /**
     * Compares this objects description with the incoming parameter,
     * @param other the object to be compared
     * @return true if same, false if not the same
     */
    @Override
    public boolean equals(Object other) {
        return this.prio.equals(((Task) other).prio);
    }

    @Override
    public String toString() {
         StringBuilder builder = new StringBuilder();
            builder.append("(" + id + "):");
            builder.append("\n\t- Description: " + description);
            builder.append(" | Priority (" + prio + ")");
            builder.append("\n\t- Owner: " + takenby);
            builder.append("\n\t- State: " + state + " | Last updated: " + lastupdate + "\n");
        String result = builder.toString().replace("[", "").replace("]", "");
        return result;
    }
}
