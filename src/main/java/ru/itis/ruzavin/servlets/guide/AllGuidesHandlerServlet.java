package ru.itis.ruzavin.servlets.guide;

import ru.itis.ruzavin.dto.GuideDTO;
import ru.itis.ruzavin.helper.HTMLHelper;
import ru.itis.ruzavin.helper.TextHelper;
import ru.itis.ruzavin.services.GuideService;
import ru.itis.ruzavin.services.GuideServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/allGuidesHandler")
public class AllGuidesHandlerServlet extends HttpServlet {

	private GuideService guideService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		if (title == null){
			List<GuideDTO> guides = guideService.findAll();
			mapGuides(resp, guides);
			return;
		}
		List<GuideDTO> guides = guideService.findAllByTitle(title);
		mapGuides(resp, guides);
	}

	private void mapGuides(HttpServletResponse resp, List<GuideDTO> guides) throws IOException, ServletException {
		guides = guides.stream()
				.map(recipe -> new GuideDTO(recipe.getId(), recipe.getUserNick(), recipe.getTitle(),
						TextHelper.editText(recipe.getText()), recipe.getPhotoUrl(), recipe.getData()))
				.collect(Collectors.toList());

		Collections.reverse(guides);

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(HTMLHelper.makeGuideHTML(guides));
	}


	@Override
	public void init() throws ServletException {
		guideService = new GuideServiceImpl();
	}
}
