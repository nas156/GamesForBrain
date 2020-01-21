package ua.project.games.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.project.games.entity.User;


public class NewUserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(user.getPassword().length() < 8) {
            errors.rejectValue("password", "1", "password must contain at least 8 characters");
            return;
        }
        try {
            double d = Double.parseDouble(user.getPassword());
            errors.rejectValue("password", "2", "password must contain letters" );
            return;
        } catch (NumberFormatException nfe) {
            nfe.getStackTrace();
        }

        if(!user.getConfirmPassword().equals(user.getPassword())){
            errors.rejectValue("confirmPassword", "4", "passwords do not match" );
        }
    }
}
