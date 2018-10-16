package com.example.nzliveservice.util;

import org.junit.jupiter.api.Test;

public class Text
{
    @Test
    public void Text(){
        String s="sdgshjgd/fgdfghdf/derwr/gfh/dfgrt/sdgsd";
        String[] t=s.split("/");
        for (String s1:t){
            System.out.println(s1);
        }
    }
}
