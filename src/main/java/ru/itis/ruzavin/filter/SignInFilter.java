package ru.itis.ruzavin.filter;

import ru.itis.ruzavin.services.SecurityService;
import ru.itis.ruzavin.services.SecurityServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/profile")
public class SignInFilter implements Filter {

	SecurityService securityService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.securityService = new SecurityServiceImpl();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if (securityService.isSigned(req)) {
			req.getRequestDispatcher("/profile").forward(req, resp);
		} else {
			req.getRequestDispatcher("/signIn").forward(req, resp);
		}

		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {

	}
}
