package com.vishaga.streams;

import com.vishaga.model.Employee;
import com.vishaga.utils.DataLoaderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

}
