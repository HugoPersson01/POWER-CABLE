package se.kth.adamfhhugoper.labb3b.model;

import se.kth.adamfhhugoper.labb3b.model.Matcher.ITaskMatcher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Project, that has a number of Tasks in a List
 * @author Adam Fabrisius-Hansen & Hugo persson.
 */
public class Project implements Comparable<Project>, Serializable {

    private final String title;
    private int id;
    private final String description;
    private LocalDate created;
    private int nexttaskId;
    private ProjectState state;
    private List<Task> tasks;

    /**
     * Constructs a new project with the specified description, id and title
     * @param description, describes the project
     * @param id, id of the project
     * @param title, title for the project
     */
    Project(String description, int id, String title) {
        this.created = LocalDate.now();
        this.description = description;
        this.id = id;
        this.nexttaskId = getNexttaskId();
        this.title = title;
        tasks = new ArrayList<>();
        this.state = getState();
    }

    private int getNexttaskId() {
        if (tasks == null) return 1;

        return tasks.size() + 1;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDecription() {
        return description;
    }

    public LocalDate getCreated() {
        return created;
    }

    public int getNextTaskId() {
        return nexttaskId;
    }

    /**
     *
     * @param decription, adds a description to the new task
     * @param taskPrio, adds a priority to the new task
     * @return newtask, that is the new task
     */

    public Task addTask(String decription, TaskPrio taskPrio) {
        Task newtask = new Task(decription, getNexttaskId(), taskPrio);
        tasks.add(newtask);
        return newtask;
    }

    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    /**
     * @return a shallow copy of the current list of tasks
     */

    public List<Task> getTasks() {
        List<Task> temp = new ArrayList<>(tasks);
        return temp;
    }

    /**
     *
     * @param matcher is strategy, that refers to the interface ITMatcher
     *                the goal is to find the tasked that is
     * @return
     */
    public List<Task> findTasks(ITaskMatcher matcher) {
        List<Task> temp = new ArrayList<>();
        for (Task t : tasks) {
            if (matcher.match(t)) {
                temp.add(t);
            } else {
            }
        }
        return temp;
    }

    public Task getTaskbyId(int id) {
        Task temp;
        for (Task t : tasks) {
            if (t.getId() == id) {
                temp = t;
                return temp;
            }
        }
        return null;
    }

    public ProjectState getState() {
        if (tasks.isEmpty()) return ProjectState.EMPTY;
        for (Task t : tasks) {
            if (t.getState() == TaskState.IN_PROGRESS) return ProjectState.ONGOING;
            if (t.getState() == TaskState.TO_DO) return ProjectState.ONGOING;
        }
        return ProjectState.COMPLETED;
    }

    /**
     * @return temp, that is the latest updated task
     */
    public LocalDate getLastUpdated() {
        LocalDate temp = tasks.get(1).getLastupdate();
        if (tasks.isEmpty()) return this.created;
        for (Task t : tasks) {
            if (temp.isBefore(t.getLastupdate())) {
                temp = t.getLastupdate();
            }
        }
        return temp;
    }


    public void setCreated(LocalDate created) {
        this.created = created;
    }

    /**
     * Compares the title of this object and the incoming parameter.
     * @param other the object to be compared.
     * @return , 0 if the title of other is the same as this objects title and
     *           < 0 or > 0 if they're not the same
     */
    @Override
    public int compareTo(Project other) {
        int titlecompare = 0;
        if (!equals(other)) {
            titlecompare = (title.compareTo(other.title));
            return titlecompare;
        }
        return titlecompare;
    }

    /**
     * Compares this objects title with the incoming parameter,
     * @param other
     * @return true if same, false if not the same
     */
    @Override
    public boolean equals(Object other) {
        return this.title.equals(((Project) other).title);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nProjects:");
        builder.append("\n- Title: " + title);
        builder.append(" | Project-ID: " + id + " | Created: " + created);
        builder.append("\n- Description: " + description);
        builder.append("\n- State: " + getState() + "\n");
        builder.append("\nTask with ID ");
        builder.append(tasks);
        String result = builder.toString().replace("[", "").replace("]", "").replace(",", "Task with ID");
        return result;
    }
}
