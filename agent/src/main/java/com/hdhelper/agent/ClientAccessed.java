package com.hdhelper.agent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks class members that are invoked by the client thread.
 * These members are to remain accessible to the client.
 * These members are to maintain their type at runtime.
 *
 * ClientAccessed methods should take extreme caution to finish
 * execution as soon as possible, not throw any errors.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientAccessed {
}
