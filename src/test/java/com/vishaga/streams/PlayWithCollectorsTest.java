package com.vishaga.streams;

import com.vishaga.model.Employee;
import com.vishaga.model.Position;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
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

}
