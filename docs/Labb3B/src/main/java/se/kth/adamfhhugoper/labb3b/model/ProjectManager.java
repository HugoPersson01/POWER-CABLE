package se.kth.adamfhhugoper.labb3b.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import se.kth.adamfhhugoper.labb3b.model.Exceptions.*;


/**
 * A class that represents a project manager. A project manager oversees a list of projects
 */
public class ProjectManager {
    private int nextProjectId;

    private List<Project> projects;

    public ProjectManager() {
        this.nextProjectId = nextProjectId;
        projects = new ArrayList<>();
    }

    /**
     * @return a shallow copy of the List
     */
    public List<Project> getProjects() {
        List<Project> copy = new ArrayList<>(projects);
        return copy;
    }

    public int getNextProjectId() {
        nextProjectId++;
        return nextProjectId;
    }

    /**
     * Updates the projet Id
     */
    public void updatePID() {
        this.nextProjectId = getHighestId();
    }

    /**
     * A method that checks if a new project has the same title an already existing one
     * @param newProject incoming project, a project that the user wants to add
     * @return true if the incoming project title is the same as this objects title
     */
    private boolean CheckTitle(Project newProject) {
        for (Project p : projects) {
            if (p.compareTo(newProject) == 0) return true;
        }
        return false;
    }

    /**
     * A method that creates a new project, but makes sure that the wanted title is unique
     * @param title The wanted title of a new created Project
     * @param description The wanted dedscritpion of a new created Project
     * @return the new project
     * @throws TitleNotUniqueException, when the wanted title already exists
     */
    public Project addproject(String title, String description) throws TitleNotUniqueException {
        Project newProject = new Project(description, getNextProjectId(), title);
        if (CheckTitle(newProject)) throw new TitleNotUniqueException();

        projects.add(newProject);
        return newProject;
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }

    public Project getProjectbyId(int id) {
        Project temp = null;

        for (Project p : projects) {
            if (p.getId() == id) {
                temp = p;
                return temp;
            }
        }
        return temp;
    }


    /**
     * A method that takes existing projects stored in a file, and returns them to a List
     * @param IncominProjects Existing projects that is stored in a ".ser" file.
     */
    public void setProjects(List<Project> IncominProjects) {

        projects.clear();

        for (Project p : IncominProjects) {
            projects.add(p);
        }
        updatePID();
    }

    /**
     * A method that is used to search for projects including the specific searched string
     * @param title the String that the users utilize to find all projects that contains the specific String
     * @return all strings that matches the specific String
     */
    public List<Project> findProjects(String title) {
        List<Project> findProject = new ArrayList<>();
        String upperTitle = title.toUpperCase();
        for (Project p : projects) {
            String upperFindTitle = p.getTitle().toUpperCase();
            if (upperFindTitle.contains(upperTitle)) {
                findProject.add(p);
            }
        }
        return findProject;
    }

    public int getHighestId() {
        int temp = 0;

        for (Project p : projects) {
            if (p.getId() > temp) temp = p.getId();
        }
        return temp;
    }

    @Override
    public String toString() {
        String result = projects.toString().replace("[", "")
                .replace("]", "")
                .replace(",", "------------------------------------------------------");
        return result;
    }
}
