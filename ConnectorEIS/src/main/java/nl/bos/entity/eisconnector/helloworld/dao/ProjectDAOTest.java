package nl.bos.entity.eisconnector.helloworld.dao;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class ProjectDAOTest {

    @Test
    void save() {
        IDataAccessObject projectDAO = new ProjectDAO();
        projectDAO.save(new Project(1, "Dummy1"));
        projectDAO.save(new Project(2, "Dummy2"));
        projectDAO.save(new Project(3, "Dummy3"));
    }

    @Test
    void get() {
        IDataAccessObject projectDAO = new ProjectDAO();
        Project project = projectDAO.get(1);
        assertThat(project).isNotNull();
        assertThat(project.getSubject()).isEqualTo("Dummy1");
    }

    @Test
    void update() {
        IDataAccessObject projectDAO = new ProjectDAO();
        Project project = projectDAO.get(1);
        project.setSubject("Updated Dummy1");
        projectDAO.update(1, project);
        assertThat(projectDAO.get(1).getSubject()).isEqualTo("Updated Dummy1");
    }

    @Test
    void delete() {
        IDataAccessObject projectDAO = new ProjectDAO();
        projectDAO.delete(1);
        assertThat(projectDAO.get(1)).isNull();
    }

    @Test
    void getAll() {
        IDataAccessObject projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.getAll();
        assertThat(projects.size()).isEqualTo(2);
    }
}