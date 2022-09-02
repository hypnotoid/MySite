package com.hypnotoid.MySite.validators;

import com.hypnotoid.MySite.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ProductValidator implements Validator {
    private static final String input = "Введите ";
    private static final String invChar = " не может содержать спецсимволы";
    private static final String invRus = " может состоять только из букв латинского алфавита";
    private static final String invNum = " не может содержать цифры";
    private static final String lenMin = "Не менее ";
    private static final String lemMax = "Не более ";

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        if (product.getName().isBlank()) errors.rejectValue("name", "", input + " имя");
        if (!product.getName().matches("[a-zA-Zа-яА-ЯёЁ0-9 \\-]+")) errors.rejectValue("name", "", "Имя" + invChar);
        if (product.getName().length() < 3) errors.rejectValue("name", "", lenMin + "3-х символов");
        if (product.getName().length() > 100) errors.rejectValue("name", "", lemMax + "100 символов");


        if (product.getAmount() > Integer.MAX_VALUE - 1) errors.rejectValue("amount", "", "Слишком много");
        if (product.getAmount() < Integer.MIN_VALUE + 2) errors.rejectValue("amount", "", "Слишком мало");
        if (product.getPrice() > 100000000) errors.rejectValue("price", "", "Слишком много");
        if (product.getPrice() < 0.01) errors.rejectValue("price", "", "Не менее 0,01");
        if (product.getId() < 0) errors.rejectValue("name", "", "Внутренняя ошибка");
    }
}
//   if (userDTO.getUsername().isBlank())
//            errors.rejectValue("username", "", input + "логин");
//
//@NotBlank(message = "Напишите название")
//@Pattern(regexp = "[a-zA-Zа-яА-ЯёЁ0-9 /-]{3,}", message = "Название не может содержать спецсимволы")
//
