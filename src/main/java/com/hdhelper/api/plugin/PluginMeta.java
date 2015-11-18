package com.hdhelper.api.plugin;

public @interface PluginMeta {
    String name();
    String desc();
    boolean configurable() default false;
}
