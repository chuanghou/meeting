package com.stellariver.meeting.application;


import com.stellariver.milky.validate.tool.Validate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
public class UserAbility {

    public void invoke() {
        internalInvoke(null);
    }


    @Validate
    public void internalInvoke(String name) {
        System.out.printf("\nregister name: %s \n", "jack");
        System.out.println("");
    }
}
