package com.stellariver.meeting.adapter.controller;

import com.stellariver.meeting.application.UserAbility;
import com.stellariver.milky.common.base.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    final UserAbility userAbility;

    public Result<Void> register(String name) {
        userAbility.invoke();
        return Result.success();
    }

}
