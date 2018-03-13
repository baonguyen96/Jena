package annotations;

public @interface Note {
    String message() default "Leave a message here";
}
