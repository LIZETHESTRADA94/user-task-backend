package com.user_service.util;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.user_service.exception.InvalidUserException;
import com.user_service.model.dto.UserDTO;

public class UserUtil {

    private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    public static void  validateUser(UserDTO userDTO) {

        if (userDTO == null ) {
            throw new InvalidUserException("Datos vacíos");
        }

        if (userDTO.getName() == null || !StringUtils.hasLength(userDTO.getName().trim())) {
            throw new InvalidUserException("Nombre vacío");
        }

        if (userDTO.getEmail() == null || !StringUtils.hasLength(userDTO.getEmail().trim())) {
            throw new InvalidUserException("Email vacío");
        }

        if (userDTO.getRole() == null || !StringUtils.hasLength(userDTO.getRole().trim())) {
            throw new InvalidUserException("Rol vacío");
        }

        if (userDTO.getId() == null && (userDTO.getPassword() == null || !StringUtils.hasLength(userDTO.getPassword().trim()))) {
            throw new InvalidUserException("Contraseña vacía");
        }

        if (
            userDTO.getEmail() == null
            || !StringUtils.hasLength(userDTO.getEmail().trim())
            || !isValidEmail(userDTO.getEmail().trim())
        ) {
            throw new InvalidUserException("Formato de email es incorrecto");
        }
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
