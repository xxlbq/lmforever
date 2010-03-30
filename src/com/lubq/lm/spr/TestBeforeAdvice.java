package com.lubq.lm.spr;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class TestBeforeAdvice implements MethodBeforeAdvice{
    public void before(Method method,Object[] args,Object target) throws Throwable{
        System.out.println("Hi,this is "+this.getClass().getName());
   }

}
