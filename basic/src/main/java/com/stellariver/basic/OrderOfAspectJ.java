package com.stellariver.basic;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;

@Aspect
@DeclarePrecedence("LogAspect, ValidateAspect")
public class OrderOfAspectJ {
}
