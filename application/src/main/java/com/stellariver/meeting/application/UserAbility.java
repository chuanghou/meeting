package com.stellariver.meeting.application;


import com.stellariver.milky.validate.tool.ValidConfig;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserAbility {

    public void invoke() {
        internalInvoke(null);
    }


    @ValidConfig
    public void internalInvoke(@NotNull String name) {
        System.out.printf("\nregister name: %s \n", "jack");
        System.out.println("");
    }
}
