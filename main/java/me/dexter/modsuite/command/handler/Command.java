package me.dexter.modsuite.command.handler;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Command {
    String name();

    String usage() default "";

    int minArgs() default 0;

    boolean playerOnly() default false;

    String permission() default "";

    String noPerm() default "&cYou do not have permission to use that command.";

    boolean allowFlags() default false;

    String[] flags() default {};
}
