package com.stellariver.meeting;

import com.stellariver.meeting.adapter.controller.UserController;
import com.stellariver.milky.common.base.Result;
import com.stellariver.milky.common.tool.exception.ErrorEnumsBase;
import lombok.CustomLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@CustomLog
@Transactional
@SpringBootTest
public class BaseTest{

    @Autowired
    UserController userController;

    @Test
    public void contextTestOut() {
        Throwable t = null;
        Result<Void> result = userController.registerOutParameterWrong("jaa");
        Assertions.assertNotNull(result);
        String code = result.getCode();
        Assertions.assertEquals(code, ErrorEnumsBase.PARAM_FORMAT_WRONG.getCode());
        Assertions.assertTrue(result.getMessage().contains("小于"));
    }

    @Test
    public void contextTestInternal() {
        Throwable t = null;
        Result<Void> result = userController.registerInternalParameterWrong();
        Assertions.assertNotNull(result);
        String code = result.getCode();
        Assertions.assertEquals(code, ErrorEnumsBase.PARAM_FORMAT_WRONG.getCode());
    }

}
