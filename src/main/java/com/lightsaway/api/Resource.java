package com.lightsaway.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Anton Sergienko on 9/22/16.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {

    String path() default "";
}
