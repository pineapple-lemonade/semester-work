package ru.itis.ruzavin.services;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
	boolean signIn(HttpServletRequest req);
	boolean signUp(HttpServletRequest req);
	boolean isSigned(HttpServletRequest req);
}
