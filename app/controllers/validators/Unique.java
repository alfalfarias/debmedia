/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.validators;

import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author simon
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@play.data.Form.Display(name="constraint.alluppercase")
public @interface Unique {
    String message() default UniqueValidator.message;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}