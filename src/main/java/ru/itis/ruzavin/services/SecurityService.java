package ru.itis.ruzavin.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityService {
	boolean signIn(HttpServletRequest req, HttpServletResponse response);
	boolean signUp(HttpServletRequest req);
	boolean isSigned(HttpServletRequest req);
	void signOut(HttpServletRequest request, HttpServletResponse response);
}
