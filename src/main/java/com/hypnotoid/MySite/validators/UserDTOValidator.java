package com.hypnotoid.MySite.validators;

import com.hypnotoid.MySite.dto.UserDTO;
import com.hypnotoid.MySite.services.UserDTOService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;

@Service
public class UserDTOValidator implements Validator { //better use javax.validator
    private static final String input = "Введите ";
    private static final String invChar = " не может содержать спецсимволы";
    private static final String invRus = " может состоять только из букв латинского алфавита";
    private static final String invNum = " не может содержать цифры";
    private static final String lenMin = "Не менее ";
    private static final String lemMax = "Не более ";

    // javax.validation.Validator
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        if (userDTO.getId()<0)
            errors.rejectValue("username", "", "Внутренняя ошибка. Перезагрузите страницу");
        if (userDTO.getUsername().isBlank())
            errors.rejectValue("username", "", input + "логин");
        if (!userDTO.getUsername().matches("[a-zA-Zа-яА-ЯёЁ0-9]+"))
            errors.rejectValue("username", "", "Логин" + invChar);
        if (userDTO.getUsername().matches("[а-яА-ЯёЁ]+"))
            errors.rejectValue("username", "", "Логин" + invRus);
        if (userDTO.getUsername().length()<3)
            errors.rejectValue("username", "", lenMin + "3-х символов");
        if (userDTO.getUsername().length()>100)
            errors.rejectValue("username", "", lemMax + "100 символов");

        if (userDTO.getPassword().isBlank())
            errors.rejectValue("password", "", input + "пароль");
        if (!userDTO.getPassword().matches("[a-zA-Zа-яА-ЯёЁ0-9]+"))
            errors.rejectValue("password", "", "Пароль" + invChar);
        if (userDTO.getPassword().length()<6)
            errors.rejectValue("password", "", lenMin + "6-ти символов");
        if (userDTO.getPassword().length()>29)
            errors.rejectValue("password", "", lemMax + "30-ти символов");

        if (userDTO.getFirstname().isBlank())
            errors.rejectValue("firstname", "", input + " имя");
        if (!userDTO.getFirstname().matches("[a-zA-Zа-яА-ЯёЁ0-9 \\-]+"))
            errors.rejectValue("firstname", "", "Имя" + invChar);
        if (userDTO.getFirstname().length()<3)
            errors.rejectValue("firstname", "", lenMin + "3-х символов");
        if (userDTO.getFirstname().length()>100)
            errors.rejectValue("firstname", "", lemMax + "100 символов");

        if (userDTO.getLastname().isBlank())
            errors.rejectValue("lastname", "", input + "фамилию");
        if (!userDTO.getLastname().matches("[a-zA-Zа-яА-ЯёЁ0-9 \\-]+"))
            errors.rejectValue("lastname", "", "Фамилия" + invChar);
        if (userDTO.getLastname().length()<3)
            errors.rejectValue("lastname","", lenMin + "3-х символов");
        if (userDTO.getLastname().length()>100)
            errors.rejectValue("lastname","", lemMax + "100 символов");
    }

}