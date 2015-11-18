package com.hdhelper.agent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks methods that are invoked my the client thread.
 * These methods should take extreme caution to
 * finish execution as soon as possible, and not throw
 * any errors.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientAccessed {
}
