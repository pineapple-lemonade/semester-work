package ru.itis.ruzavin.services;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
	void signOut(HttpServletRequest req);
	boolean signIn(HttpServletRequest req);
	boolean signUp(HttpServletRequest req);
}
