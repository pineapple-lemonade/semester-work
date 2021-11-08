package ru.itis.ruzavin.servlets.guide;

import ru.itis.ruzavin.dto.GuideCommentDTO;
import ru.itis.ruzavin.dto.GuideDTO;
import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.models.GuideComment;
import ru.itis.ruzavin.services.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/guideInfo")
public class GuideInfoServlet extends HttpServlet {

	private GuideService guideService;

	private UserService userService;

	private GuideCommentService guideCommentService;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		GuideDTO guide = guideService.findById(Integer.parseInt(req.getParameter("id")));
		UserDTO user = userService.findUserByNick(guide.getUserNick());
		List<GuideCommentDTO> comments =
				guideCommentService.findAllByGuideId(Integer.parseInt(req.getParameter("id")));

		req.setAttribute("guide", guide);
		req.setAttribute("u", null);
		req.setAttribute("author", user);
		req.setAttribute("isComments", comments);
		req.setAttribute("comments", comments);
		req.setAttribute("userNow", req.getAttribute("user"));

		req.getRequestDispatcher("/pages/guideInfo.ftl").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDTO user = (UserDTO) req.getAttribute("user");

		String text = req.getParameter("comment");
		int userId = user.getId();

		GuideComment comment = new GuideComment(userId, Integer.parseInt(req.getParameter("id")), text);
		guideCommentService.save(comment);

		String redirect = "/guideInfo?id=" + req.getParameter("id");
		resp.sendRedirect(redirect);
	}

	@Override
	public void init() throws ServletException {
		guideService = new GuideServiceImpl();
		userService = new UserServiceImpl();
		guideCommentService = new GuideCommentServiceImpl();
	}
}
