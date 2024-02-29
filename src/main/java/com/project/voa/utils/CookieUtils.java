package com.project.voa.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtils {
	/**
	 * Set Cookie
	 * @param response
	 * @param key cookie name
	 * @param value
	 * @param maxAge
	 * @param path
	 * @param httpOnly
	 */
	public static void setCookie(
			HttpServletResponse response,
			String key,
			String value,
			int maxAge,
			String path,
			boolean httpOnly
	) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		cookie.setHttpOnly(httpOnly);
		response.addCookie(cookie);
	}

	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				if (name.equals(key)) {
					return value;
				}
			}
		}
		return "";
	}

	/**
	 * 특정 cookie 제거
	 * @param response
	 */
	public static void deleteCookie(HttpServletResponse response, String delKey) {
		Cookie cookie = new Cookie(delKey, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	public static void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		}
	}
}
