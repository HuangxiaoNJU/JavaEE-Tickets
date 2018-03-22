package edu.nju.tickets.util;

import javax.servlet.http.Cookie;

public class CookieUtil {

    public static Cookie getCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setHttpOnly(true);
        return cookie;
    }

}
