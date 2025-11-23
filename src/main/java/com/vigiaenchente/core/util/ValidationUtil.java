package com.vigiaenchente.core.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\d{10,11}$"
    );

    private static final Pattern CEP_PATTERN = Pattern.compile(
            "^\\d{8}$"
    );

    private ValidationUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Validates email format
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates Brazilian phone number (10 or 11 digits)
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }

        String cleanPhone = phone.replaceAll("\\D", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }

    /**
     * Validates Brazilian CEP (8 digits)
     */
    public static boolean isValidCep(String cep) {
        if (cep == null) {
            return false;
        }

        String cleanCep = cep.replaceAll("\\D", "");
        return CEP_PATTERN.matcher(cleanCep).matches();
    }

    /**
     * Removes non-digit characters from string
     */
    public static String removeNonDigits(String input) {
        return input != null ? input.replaceAll("\\D", "") : null;
    }

    /**
     * Validates string is not null or empty
     */
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Validates password strength (minimum 6 characters)
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
