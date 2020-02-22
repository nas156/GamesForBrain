package ua.project.games.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Make the annotation available at runtime:
@Retention(RetentionPolicy.RUNTIME)
// Allow to use only on types:
@Target(ElementType.TYPE)
public @interface AdminRepo {

    Class<?> name();
}
