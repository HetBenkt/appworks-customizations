package nl.bos.entity.eisconnector.helloworld.dao;

import java.util.List;

public interface IDataAccessObject {

    void save(Project project);

    Project get(int id);

    void update(int id, Project newProject);

    void delete(int id);

    List<Project> getAll();
}