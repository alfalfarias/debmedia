/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.validators;

import javax.validation.ConstraintValidator;
import play.libs.F;

/**
 *
 * @author simon
 */
public class UniqueValidator extends play.data.validation.Constraints.Validator<Object> 
        implements ConstraintValidator<Unique, Object>{
    /* Default error message */
    final static public String message = "The value must be unique.";
    
    /**
     * Validator init
     * Can be used to initialize the validation based on parameters
     * passed to the annotation.
     */
    public void initialize(Unique constraintAnnotation) {}

    /**
     * The validation itself
     * @param object
     * @return 
     */
    public boolean isValid(Object object) {
        if(object == null)
            return false;

        if(!(object instanceof String))
            return false;

        String s = object.toString(); 
        
        for(char c : s.toCharArray()) {
            if(Character.isLetter(c) && Character.isLowerCase(c))
                return false;
        }

        return true;
    }

    /**
     * Constructs a validator instance.
     */
    public static play.data.validation.Constraints.Validator<Object> alluppercase() {
        return new UniqueValidator();
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
