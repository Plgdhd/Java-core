package com.plgdhd.interfaces;

public @interface Scope {
    String value() default "singleton";
}
