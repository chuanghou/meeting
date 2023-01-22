package com.stellariver.meeting.adapter.controller;

import com.stellariver.basic.AspectJLog;
import com.stellariver.meeting.application.UserAbility;
import com.stellariver.milky.common.base.Result;
import com.stellariver.milky.validate.tool.Validate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    final UserAbility userAbility;

    @Validate
    @AspectJLog("AspectJLogTest")
    @GetMapping("testOut")
    public Result<Void> registerOutParameterWrong(String name) {
        return Result.success();
    }


    @Validate
    @AspectJLog("AspectJLogTest")
    @GetMapping("testInternal")
    public Result<Void> registerInternalParameterWrong(@Min(3) String name) {
        return Result.success();
    }

}
