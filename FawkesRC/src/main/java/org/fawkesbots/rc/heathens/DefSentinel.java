package org.fawkesbots.rc.heathens;

/**
 * Created by hello_000 on 10/23/2016.
 */
public @interface DefSentinel {
    String drive();
    boolean specialized() default false;
    String description();
}
