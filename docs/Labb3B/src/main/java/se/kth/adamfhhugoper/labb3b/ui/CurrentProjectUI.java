
package se.kth.adamfhhugoper.labb3b.ui;


import se.kth.adamfhhugoper.labb3b.model.*;
import se.kth.adamfhhugoper.labb3b.model.Matcher.ITaskMatcher;
import se.kth.adamfhhugoper.labb3b.model.Matcher.NotDoneMatcher;
import se.kth.adamfhhugoper.labb3b.model.Matcher.PrioMatcher;
import se.kth.adamfhhugoper.labb3b.model.Matcher.TakenByMatcher;

import java.util.List;
import java.util.Scanner;

/**
 * User interactions for a specific project, current project.
 * The user selects actions on current project in the projectLoop method.
 */

class CurrentProjectUI {
    private Project currentProject;
    private final Scanner scan;

    // package private visibility - only visible to other classes in
    // package ui - intended for MainUI.
    CurrentProjectUI(Scanner scan) {
        this.scan = scan;
        this.currentProject = null; // TODO: Ugly!
    }

    void setCurrentProject(Project project) {
        this.currentProject = project;
        projectLoop();
    }

    Project getCurrentProject() {
        return currentProject;
    }

    void projectLoop() {
        char choice;
        do {
            printCurrentProjectMenu();
            choice = InputUtils.scanAndReturnFirstChar(scan);

            switch (choice) {
                case 'T':
                    System.out.print("Name? ");
                    String takenBy = scan.nextLine();
                    viewTasks(new TakenByMatcher(takenBy));
                    break;
                case 'N':
                    viewTasks(new NotDoneMatcher());
                    break;
                case 'H':
                    viewTasks(new PrioMatcher(TaskPrio.High));
                    break;
                case 'A':
                    addTask();
                    break;
                case 'U':
                    updateTask();
                    break;
                case 'R':
                    removeTask();
                    break;
                case 'P':
                    printTasks(currentProject.getTasks());
                    break;
                case 'X':
                    break;


                default:
                    System.out.println("Unknown command");
            }

        } while (choice != 'X');
    }

    private void viewTasks(ITaskMatcher matcher) {
        System.out.println(currentProject.toString());
        List<Task> tasks = currentProject.findTasks(matcher);
        printTasks(tasks);
    }

    private void addTask() {
        System.out.print("Description? ");
        String descr = scan.nextLine();
        System.out.print("Priority (L)ow, (M)edium, (H)igh? ");
        char prioChar = InputUtils.scanAndReturnFirstChar(scan);
        TaskPrio taskPrio = prioChar == 'H' ? TaskPrio.High : prioChar == 'L' ? TaskPrio.Low : TaskPrio.Medium;
        currentProject.addTask(descr, taskPrio);
    }

    private void updateTask() {
        System.out.print("Task id? ");
        int id = scan.nextInt();
        scan.nextLine(); //remove "new line" from scanner buffer
        Task task = currentProject.getTaskbyId(id);
        if (task != null) {
            System.out.println(task);
            System.out.print("New state (T)odo (D)one? ");
            char stateChar = InputUtils.scanAndReturnFirstChar(scan);
            if (stateChar == 'T') {
                System.out.print("Taken by (name or email address)? ");
                String emailStr = scan.nextLine();
                task.setState(TaskState.TO_DO);
                task.setTakenby(emailStr);
            }
            else if(stateChar == ('D')) {
                task.setState(TaskState.DONE);
            }
        } else {
            System.out.println("Id not found.");
        }
    }

    /**
     * removes a chosen task by the user
     */
    public void removeTask(){
        System.out.print("Task id? ");
        int id = scan.nextInt();
        scan.nextLine();
        Task task = currentProject.getTaskbyId(id);
        if (task != null) {
            currentProject.removeTask(task);
        }
    }

    private void printCurrentProjectMenu() {
        System.out.println("--- Manage " + currentProject.getTitle() + " ---");
        System.out.println("T - list tasks taken by ...");
        System.out.println("N - list tasks not done");
        System.out.println("H - list high priority tasks");
        System.out.println("A - add task");
        System.out.println("U - update task");
        System.out.println("R - remove task");
        System.out.println("P - print all tasks");
        System.out.println("X - exit project menu");
        System.out.println("----------");
    }

    private void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks added");
        } else {
            for (Task task : tasks) {
                System.out.println(task.toString());
            }
        }
    }
}



