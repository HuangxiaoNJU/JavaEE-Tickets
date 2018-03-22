package edu.nju.tickets.service;

import edu.nju.tickets.entity.Project;
import edu.nju.tickets.entity.ProjectPrice;
import edu.nju.tickets.vo.PriceInfoVO;
import edu.nju.tickets.vo.ProjectInfoVO;

public interface ProjectInfo {

    PriceInfoVO convertProjectPriceToVO(ProjectPrice projectPrice);

    ProjectInfoVO convertProjectToVO(Project project);

}
