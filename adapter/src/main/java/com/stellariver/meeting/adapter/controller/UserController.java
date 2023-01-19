package com.stellariver.meeting.adapter.controller;

import com.stellariver.meeting.application.UserAbility;
import com.stellariver.milky.common.base.Result;
import com.stellariver.milky.validate.tool.ValidConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    final UserAbility userAbility;

    @ValidConfig
    @GetMapping("testOut")
    public Result<Void> registerOutParameterWrong(@NotNull @Min(5) String name) {
        userAbility.invoke();
        return Result.success();
    }


    @ValidConfig
    @GetMapping("testInternal")
    public Result<Void> registerInternalParameterWrong() {
        userAbility.invoke();
        return Result.success();
    }

}
