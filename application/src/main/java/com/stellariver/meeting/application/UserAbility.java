package com.stellariver.meeting.application;


import org.springframework.stereotype.Service;

@Service
public class UserAbility {

    public void invoke() {
        internalInvoke();
    }


    public void internalInvoke() {
        System.out.printf("\nregister name: %s \n", "jack");
        System.out.println("");
    }
}
