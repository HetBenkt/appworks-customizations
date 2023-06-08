package nl.bos.entity.eisconnector.helloworld.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO implements IDataAccessObject {
    private List<Project> projects = new ArrayList<>();

    public ProjectDAO() {
        deserializeData();
    }

    @Override
    public void save(Project project) {
        projects.add(project);
        serializeData();
    }

    @Override
    public Project get(int id) {
        for (Project project : projects) {
            if(project.getId() == id) {
                return project;
            }
        }
        return null;
    }

    @Override
    public void update(int id, Project newProject) {
        for (Project project : projects) {
            if(project.getId() == id) {
                project.setSubject(newProject.getSubject());
                break;
            }
        }
        serializeData();
    }

    @Override
    public void delete(int id) {
        for (Project project : projects) {
            if(project.getId() == id) {
                projects.remove(project);
                break;
            }
        }
        serializeData();
    }

    @Override
    public List<Project> getAll() {
        return projects;
    }

    private void serializeData() {
        try {
        FileOutputStream fos = new FileOutputStream("projectsData");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(projects);

        oos.close();
        fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deserializeData() {
        try {
            FileInputStream fis = new FileInputStream("projectsData");
            ObjectInputStream ois = new ObjectInputStream(fis);

            projects = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
