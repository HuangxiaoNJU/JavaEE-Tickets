package edu.nju.tickets.service;

import edu.nju.tickets.vo.ProjectInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml"})
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void testGetProjects() {
//        List<ProjectInfoVO> projects = projectService.getProjects("id", "desc");
//        projects.forEach(p -> System.out.println(p.getName()));
    }

}
