package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Allocation;
import edu.nju.tickets.entity.Venue;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public interface AllocationDao extends BaseDao<Allocation, Integer> {

    Allocation findByProjectId(Integer projectId);

    List<Allocation> findByVenueId(Integer venueId);

    double sumVenueIncomeByVenueId(Integer venueId);

    List<Object[]> sumPlatformIncomeGroupByProjectTypeAndDay();

    List<Object[]> sumPlatformIncomeGroupByProjectTypeAndMonth();

    List<Object[]> sumPlatformIncomeGroupByProjectTypeAndYear();

    List<Object[]> sumVenueIncomeByVenueGroupByTypeAndDay(Venue venue);

    List<Object[]> sumVenueIncomeByVenueGroupByTypeAndMonth(Venue venue);

    List<Object[]> sumVenueIncomeByVenueGroupByTypeAndYear(Venue venue);


    default Map<String, Map<String, Double>> listToNestMap(List<Object[]> list, int dateNum) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        objects -> {
                            StringBuilder sb = new StringBuilder();
                            sb.append(objects[2]);
                            for (int i = 3; i < 2 + dateNum; i++) {
                                sb.append("-").append(objects[i]);
                            }
                            return sb.toString();
                        },
                        TreeMap::new,
                        Collectors.groupingBy(objects -> String.valueOf(objects[0]), Collectors.summingDouble(objects -> (Double) objects[1]))
                        )
                );
    }

}
