package com.vishaga.streams;

import com.vishaga.model.Employee;
import com.vishaga.model.Position;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithCollectorsTest {

    private static List<Employee> EMPLOYEES;
    private static final Employee FAKE = Employee.fake();

    @BeforeAll
    public static void setUp(){
        EMPLOYEES = DataLoaderUtils.loadEmployee(100000);
    }

    @Test
    @DisplayName("Count number of employees in collection")
    public void numberOfEmployees(){
        assertThat((long) EMPLOYEES.size()).isEqualTo(1003);
    }

    @Test
    @DisplayName("Count number of employees in collection who are at director post")
    public void test_counting(){
        Long count = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.DIRECTOR)
                .collect(Collectors.counting());
        assertThat(count).isEqualTo(79);
    }

    @Test
    @DisplayName("Count number of Employees by Position/Category")
    public void test_countEmployeeByCategory(){
        Map<Position, Long> numberOfEmployeesByPosition = EMPLOYEES.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::position,
                                Collectors.counting()));
        assertThat(numberOfEmployeesByPosition).containsAllEntriesOf(Map.ofEntries(
                Map.entry(Position.INTERN, 106L),
                Map.entry(Position.TRAINEE, 3L),
                Map.entry(Position.SE, 96L),
                Map.entry(Position.SSE, 103L),
                Map.entry(Position.CONSULTANT, 99L),
                Map.entry(Position.SENIOR_CONSULTANT, 104L),
                Map.entry(Position.LEAD, 94L),
                Map.entry(Position.SENIOR_LEAD, 102L),
                Map.entry(Position.MANAGER, 112L),
                Map.entry(Position.SENIOR_MANAGER, 105L),
                Map.entry(Position.DIRECTOR, 79L)
        ));
    }

    @Test
    @DisplayName("Average Salary of Director")
    public void test_averaging(){
        int averagingUsingAveragingInt = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.averagingInt(Employee::salary),
                                Double::intValue
                        )
                );
        assertThat(averagingUsingAveragingInt).isEqualTo(248333);

        int averagingUsingAveragingLong = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.averagingLong(Employee::salary),
                                Double::intValue
                        )
                );
        assertThat(averagingUsingAveragingLong).isEqualTo(248333);

        int averagingUsingAveragingDouble = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.averagingDouble(Employee::salary),
                                Double::intValue
                        )
                );
        assertThat(averagingUsingAveragingDouble).isEqualTo(248333);
    }

    @Test
    @DisplayName("Trainee with Maximum Salary")
    public void test_max(){
        String maxSalariedTrainee = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Employee::salary)),
                                optionalEmp -> optionalEmp.orElse(FAKE).firstName()
                        )
                );
        assertThat(maxSalariedTrainee).isEqualTo("Gaurav");
    }

    @Test
    @DisplayName("Trainee with Minimum Salary")
    public void test_min(){
        String maxSalariedTrainee = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.minBy(Comparator.comparing(Employee::salary)),
                                optionalEmp -> optionalEmp.orElse(FAKE).firstName()
                        )
                );
        assertThat(maxSalariedTrainee).isEqualTo("Anuj");
    }

}
