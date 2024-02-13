package com.vishaga.streams;

import com.vishaga.model.Employee;
import com.vishaga.model.Position;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class PlayWithEmployeeStreamTest {

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
    @DisplayName("Employees with maximum emails")
    public void employeesWithMaxEmails(){
        Employee employee = EMPLOYEES.stream()
                .max(Comparator.comparing(emp -> emp.emails().size())).orElseThrow(NoSuchElementException::new);
        assertThat(employee.firstName()).isEqualTo("Rudy");
        assertThat(employee.emails().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("Employees with maximum Salary")
    public void employeesWithMaxSalary(){
        Employee employee = EMPLOYEES.stream()
                .max(Comparator.comparing(Employee::salary)).orElseThrow(NoSuchElementException::new);
        assertThat(employee.firstName()).isEqualTo("Cierra");
        assertThat(employee.salary()).isEqualTo(903000);
    }

    @Test
    @DisplayName("Employees with maximum Salary per role like SSE, SE etc")
    public void roleWiseEmployeesWithMaxSalary(){
        Map<Position, Optional<Employee>> positionWiseMaxSalariedEmployee1 = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(
                        Employee::position,
                        Collectors.maxBy(Comparator.comparing(Employee::salary))));

        positionWiseMaxSalariedEmployee1.forEach((key, value) -> {
            assertThat("Gaurav Vishal").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.TRAINEE).orElse(FAKE).name());
            assertThat("Tabitha Rempel").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.INTERN).orElse(FAKE).name());
            assertThat("Ottilie Romaguera").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.SE).orElse(FAKE).name());
            assertThat("Bill Little").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.SSE).orElse(FAKE).name());
            assertThat("Gregg Pouros").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.CONSULTANT).orElse(FAKE).name());
            assertThat("Osborne Rempel").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.SENIOR_CONSULTANT).orElse(FAKE).name());
            assertThat("Meredith Sanford").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.LEAD).orElse(FAKE).name());
            assertThat("Hattie Lindgren").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.SENIOR_LEAD).orElse(FAKE).name());
            assertThat("Cierra Keebler").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.MANAGER).orElse(FAKE).name());
            assertThat("Antonetta Franecki").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.SENIOR_MANAGER).orElse(FAKE).name());
            assertThat("Leonie Willms").isEqualTo(positionWiseMaxSalariedEmployee1.get(Position.DIRECTOR).orElse(FAKE).name());
        });

        Map<Position, String> positionWiseMaxSalariedEmployee2 = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(
                        Employee::position,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Employee::salary)),
                                optEmp -> optEmp.orElse(FAKE).name())));

        positionWiseMaxSalariedEmployee2.forEach((key, value) -> {
            assertThat("Gaurav Vishal").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.TRAINEE));
            assertThat("Tabitha Rempel").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.INTERN));
            assertThat("Ottilie Romaguera").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.SE));
            assertThat("Bill Little").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.SSE));
            assertThat("Gregg Pouros").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.CONSULTANT));
            assertThat("Osborne Rempel").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.SENIOR_CONSULTANT));
            assertThat("Meredith Sanford").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.LEAD));
            assertThat("Hattie Lindgren").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.SENIOR_LEAD));
            assertThat("Cierra Keebler").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.MANAGER));
            assertThat("Antonetta Franecki").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.SENIOR_MANAGER));
            assertThat("Leonie Willms").isEqualTo(positionWiseMaxSalariedEmployee2.get(Position.DIRECTOR));
        });
    }

    @Test
    @DisplayName("Role wise Sum of Salaries")
    public void roleWiseTotalSalary(){
        Map<Position, Integer> positionToSalary = EMPLOYEES.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::position,
                                Collectors.mapping(
                                        Employee::salary,
                                        Collectors.reducing(0, Integer::sum)
                                )
                        )
                );

        positionToSalary.forEach((key, value) -> {
            assertThat(456000).isEqualTo(positionToSalary.get(Position.TRAINEE));
            assertThat(48888000).isEqualTo(positionToSalary.get(Position.INTERN));
            assertThat(45160500).isEqualTo(positionToSalary.get(Position.SE));
            assertThat(46231500).isEqualTo(positionToSalary.get(Position.SSE));
            assertThat(50736000).isEqualTo(positionToSalary.get(Position.CONSULTANT));
            assertThat(47995500).isEqualTo(positionToSalary.get(Position.SENIOR_CONSULTANT));
            assertThat(41769000).isEqualTo(positionToSalary.get(Position.LEAD));
            assertThat(49045500).isEqualTo(positionToSalary.get(Position.SENIOR_LEAD));
            assertThat(53602500).isEqualTo(positionToSalary.get(Position.MANAGER));
            assertThat(48741000).isEqualTo(positionToSalary.get(Position.SENIOR_MANAGER));
            assertThat(38808000).isEqualTo(positionToSalary.get(Position.DIRECTOR));
        });
    }

    @Test
    @DisplayName("Role wise Min and Max Salaried Employee")
    public void roleWiseMinAndMaxSalariedEmployee(){
        Map<Position, List<String>> positionMinMaxEmployeeMap = EMPLOYEES.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::position,
                                Collectors.teeing(
                                        Collectors.minBy(Comparator.comparing(Employee::salary)),
                                        Collectors.maxBy(Comparator.comparing(Employee::salary)),
                                        (min, max) -> List.of(min.orElse(FAKE).name(), max.orElse(FAKE).name()))));

        assertThat(positionMinMaxEmployeeMap).contains(
                entry(Position.TRAINEE, List.of("Vivek Vishal", "Gaurav Vishal")),
                entry(Position.CONSULTANT, List.of("April Considine", "Gregg Pouros")),
                entry(Position.SENIOR_LEAD, List.of("Claire Bode", "Hattie Lindgren")),
                entry(Position.SSE, List.of("Maegan Runolfsson", "Bill Little")),
                entry(Position.SE, List.of("Johann Feest", "Ottilie Romaguera")),
                entry(Position.LEAD, List.of("Eduardo Reilly", "Meredith Sanford")),
                entry(Position.SENIOR_MANAGER, List.of("Sonia Heaney", "Antonetta Franecki")),
                entry(Position.SENIOR_CONSULTANT, List.of("Paris Ernser", "Osborne Rempel")),
                entry(Position.DIRECTOR, List.of("Jerrod Gaylord", "Leonie Willms")),
                entry(Position.MANAGER, List.of("Boris Gottlieb", "Cierra Keebler")),
                entry(Position.INTERN, List.of("Marco Heidenreich", "Tabitha Rempel"))
        );
    }

}
