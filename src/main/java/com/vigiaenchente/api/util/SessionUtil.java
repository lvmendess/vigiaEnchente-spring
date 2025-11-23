package com.vigiaenchente.api.util;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String USER_NAME_ATTRIBUTE = "userName";
    private static final String USER_EMAIL_ATTRIBUTE = "userEmail";

    private SessionUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Long getUserId(HttpSession session) {
        return (Long) session.getAttribute(USER_ID_ATTRIBUTE);
    }

    public static void setUserId(HttpSession session, Long userId) {
        session.setAttribute(USER_ID_ATTRIBUTE, userId);
    }

    public static String getUserName(HttpSession session) {
        return (String) session.getAttribute(USER_NAME_ATTRIBUTE);
    }

    public static void setUserName(HttpSession session, String userName) {
        session.setAttribute(USER_NAME_ATTRIBUTE, userName);
    }

    public static String getUserEmail(HttpSession session) {
        return (String) session.getAttribute(USER_EMAIL_ATTRIBUTE);
    }

    public static void setUserEmail(HttpSession session, String userEmail) {
        session.setAttribute(USER_EMAIL_ATTRIBUTE, userEmail);
    }

    public static boolean isAuthenticated(HttpSession session) {
        return getUserId(session) != null;
    }

    public static void clearSession(HttpSession session) {
        session.invalidate();
    }
}
