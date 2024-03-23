package com.vishaga.streams;

import com.vishaga.model.ElectoralBondBuyer;
import com.vishaga.model.ElectoralBondUser;
import com.vishaga.utils.MockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayWithElectoralBondTest {

    private static List<ElectoralBondBuyer> BUYERS;
    private static List<ElectoralBondUser> USERS;

    @BeforeAll
    public static void setUp(){
        BUYERS = MockData.electoralBondBuyerList();
        USERS = MockData.electoralBondUserList();
    }

    @Test
    @DisplayName("Counting")
    public void countTest(){
        assertThat((long) BUYERS.size()).isEqualTo(18871);
        assertThat((long) USERS.size()).isEqualTo(20421);
    }
}
