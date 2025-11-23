package com.vigiaenchente.core.util;

public class StringUtil {

    private StringUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Formats phone number to Brazilian format: (11) 98765-4321
     */
    public static String formatPhoneNumber(String phone) {
        if (phone == null) {
            return null;
        }

        String cleanPhone = phone.replaceAll("\\D", "");

        if (cleanPhone.length() == 11) {
            return String.format("(%s) %s-%s",
                    cleanPhone.substring(0, 2),
                    cleanPhone.substring(2, 7),
                    cleanPhone.substring(7));
        } else if (cleanPhone.length() == 10) {
            return String.format("(%s) %s-%s",
                    cleanPhone.substring(0, 2),
                    cleanPhone.substring(2, 6),
                    cleanPhone.substring(6));
        }

        return phone;
    }

    /**
     * Formats CEP to Brazilian format: 12345-678
     */
    public static String formatCep(String cep) {
        if (cep == null) {
            return null;
        }

        String cleanCep = cep.replaceAll("\\D", "");

        if (cleanCep.length() == 8) {
            return String.format("%s-%s",
                    cleanCep.substring(0, 5),
                    cleanCep.substring(5));
        }

        return cep;
    }

    /**
     * Capitalizes first letter of each word
     */
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.trim().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return result.toString().trim();
    }

    /**
     * Truncates string to specified length with ellipsis
     */
    public static String truncate(String input, int maxLength) {
        if (input == null || input.length() <= maxLength) {
            return input;
        }

        return input.substring(0, maxLength - 3) + "...";
    }
}
