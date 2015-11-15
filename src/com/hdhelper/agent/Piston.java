package com.hdhelper.agent;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Marks methods that require the game engines thread to be parked,
//or for this method to be executing within the engines thread.
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Piston {
}
