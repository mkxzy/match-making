package com.iblotus.exchange;


import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {


    @Test
    public void testSetScale(){
        BigDecimal a = new BigDecimal("1.356");
        BigDecimal b = a.setScale(1, BigDecimal.ROUND_HALF_UP);
        System.out.println(b);
    }
}
