package com.vishaga.streams;

import com.vishaga.model.Employee;
import com.vishaga.model.Position;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class PlayWithCollectorsTest {

    private static List<Employee> EMPLOYEES;
    private static final Employee FAKE = Employee.fake();

    @BeforeAll
    public static void setUp(){
        EMPLOYEES = MockData.employees(1004);
    }

    @Test
    @DisplayName("Count number of employees in collection")
    public void numberOfEmployees(){
        assertThat((long) EMPLOYEES.size()).isEqualTo(1004);
    }

    @Test
    @DisplayName("Count number of employees in collection who are DIRECTOR")
    public void test_counting(){
        Long count = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.DIRECTOR)
                .collect(Collectors.counting());
        assertThat(count).isEqualTo(79);

        count = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.DIRECTOR)
                .count();
        assertThat(count).isEqualTo(79);
    }

    @Test
    @DisplayName("Count number of Employees by Position")
    public void test_countEmployeeByCategory(){
        Map<Position, Long> numberOfEmployeesByPosition = EMPLOYEES.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::position,
                                Collectors.counting()));

        assertThat(numberOfEmployeesByPosition).containsAllEntriesOf(Map.ofEntries(
                Map.entry(Position.INTERN, 106L),
                Map.entry(Position.TRAINEE, 4L),
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
    @DisplayName("Average Salary of TRAINEE")
    public void test_averaging(){
        int salaryUsingAveragingInt = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.averagingInt(Employee::salary),
                                Double::intValue
                        )
                );
        assertThat(salaryUsingAveragingInt).isEqualTo(114000);

        int salaryUsingAveragingLong = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.averagingLong(Employee::salary),
                                Double::intValue
                        )
                );
        assertThat(salaryUsingAveragingLong).isEqualTo(114000);

        int salaryUsingAveragingDouble = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.averagingDouble(Employee::salary),
                                Double::intValue
                        )
                );
        assertThat(salaryUsingAveragingDouble).isEqualTo(114000);
    }

    @Test
    @DisplayName("Trainee with Maximum Salary")
    public void test_max(){
        String maxSalariedTrainee = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Employee::salary)),
                                optionalEmp -> optionalEmp.orElse(FAKE).firstName()));

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
                                optionalEmp -> optionalEmp.orElse(FAKE).firstName()));

        assertThat(maxSalariedTrainee).isEqualTo("Vivek");
    }

    @Test
    @DisplayName("Comma separated names of all Trainee(s)")
    public void test_mappingBy(){
        String maxSalariedTrainee = EMPLOYEES.stream()
                .filter(employee -> employee.position() == Position.TRAINEE)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.mapping(Employee::firstName,Collectors.joining(",")),
                                Function.identity()
                        )
                );
        assertThat(maxSalariedTrainee).isEqualTo("Gaurav,Gautam,Anuj,Vivek");
    }

    @Test
    @DisplayName("Employee who joined/switched most recently")
    public void test_sorted(){
        Employee recentlyJoinedEmployee = EMPLOYEES.stream()
                .max(Comparator.comparing(Employee::dateOfJoining)).orElse(FAKE);
        assertThat(recentlyJoinedEmployee.firstName()).isEqualTo("Vivek");
    }

    @Test
    @DisplayName("Given a list of person, count the number of adult person by sex" +
            "where Female is treated as Adult on or after 18" +
            "but Male is treated as Adult on or after 21")
    public void test_filtering(){
        enum Sex{M,F};
        record Person(String name, Sex sex, int age){};

        List<Person> people = List.of(
                new Person("Alice", Sex.F, 25),
                new Person("Bob", Sex.M,18),
                new Person("Charlie", Sex.M,30),
                new Person("David", Sex.M,16),
                new Person("Nancy", Sex.F,18),
                new Person("Gaurav", Sex.M,26),
                new Person("William", Sex.M,20),
                new Person("Naina", Sex.F,12)
        );

        Predicate<Person> maleAdult = person -> person.sex == Sex.M && person.age >= 21;
        Predicate<Person> femaleAdult = person -> person.sex == Sex.F && person.age >= 18;

        Map<Sex, Long> numberOfAdultsBySex = people.stream()
                .collect(Collectors.groupingBy(
                        Person::sex,
                        Collectors.filtering(maleAdult.or(femaleAdult), Collectors.counting())));

        assertThat(numberOfAdultsBySex).containsAllEntriesOf(Map.ofEntries(
                entry(Sex.M, 2L),
                entry(Sex.F, 2L)
        ));
    }

}
