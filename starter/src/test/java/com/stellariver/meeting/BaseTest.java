package com.stellariver.meeting;

import com.stellariver.meeting.application.UserAbility;
import lombok.CustomLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@CustomLog
@Transactional
@SpringBootTest
public class BaseTest{

    @Autowired
    UserAbility userAbility;

    @Test
    public void contextTest() {
        userAbility.registerUser();
    }

}
