package com.vishaga.streams;

import com.vishaga.model.Employee;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithEmployeeStreamTest {

    private static List<Employee> EMPLOYEES;

    @BeforeAll
    public static void setUp(){
        EMPLOYEES = DataLoaderUtils.loadEmployee(100000);
    }

    @Test
    @DisplayName("Count number of employees in collection")
    public void numberOfEmployees(){
        assertThat((long) EMPLOYEES.size()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Employees with maximum emails")
    public void employeesWithMaxEmails(){
        Employee employee = EMPLOYEES.stream()
                .max(Comparator.comparing(emp -> emp.emails().size())).orElseThrow(NoSuchElementException::new);
        assertThat(employee.firstName()).isEqualTo("Kellie");
        assertThat(employee.emails().size()).isEqualTo(5);
    }

}
