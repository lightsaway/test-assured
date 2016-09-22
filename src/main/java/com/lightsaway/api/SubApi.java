package com.lightsaway.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to instantiate nested apis at runtime
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SubApi {

    /**
     * Indicates whether cookies needs to be injected from parent
     * defaults to true
     */
    boolean useCookies() default true;

    /**
     * Indicates whether filters needs to be injected from parent
     * defaults to true
     */
    boolean useFilters() default true;

    /**
     * Indicates whether some filters should be blacklisted
     * defaults to empty array
     */
    Class[] filtersBlackList() default {};

}
