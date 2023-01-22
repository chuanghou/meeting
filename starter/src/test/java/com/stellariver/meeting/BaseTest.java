package com.stellariver.meeting;

import com.stellariver.basic.LogAspect;
import com.stellariver.meeting.adapter.controller.UserController;
import com.stellariver.milky.common.base.Result;
import com.stellariver.milky.common.tool.exception.BizException;
import com.stellariver.milky.common.tool.exception.ErrorEnumsBase;
import lombok.CustomLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

@CustomLog
@SpringBootTest
public class BaseTest{

    @Autowired
    UserController userController;


    @Test
    public void contextTestOut() {
        Throwable t = null;
        userController.registerOutParameterWrong("jaa");
    }

    @Test
    public void contextTestInternal() {
        Throwable t = null;
        try {
            userController.registerInternalParameterWrong("a");
        } catch (Throwable throwable) {
            t = throwable;
        }
        Assertions.assertNotNull(t);
        Assertions.assertTrue(t instanceof BizException);
        BizException bizException = (BizException) t;
        Assertions.assertEquals(bizException.getFirstError().getCode(), ErrorEnumsBase.PARAM_FORMAT_WRONG.getCode());
    }

}
