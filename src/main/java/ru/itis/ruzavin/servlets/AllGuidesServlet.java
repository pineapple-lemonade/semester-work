package ru.itis.ruzavin.servlets;

import ru.itis.ruzavin.dto.GuideDTO;
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

@WebServlet(urlPatterns = "/allGuides")
public class AllGuidesServlet extends HttpServlet {

	private GuideService guideService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<GuideDTO> guides = guideService.findAll();

		mapGuidesList(req, resp, guides);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		List<GuideDTO> guideDTOS = guideService.findAllByTitle(title);
		mapGuidesList(req, resp, guideDTOS);
	}

	private void mapGuidesList(HttpServletRequest req, HttpServletResponse resp, List<GuideDTO> guideDTOS) throws ServletException, IOException {
		guideDTOS = guideDTOS.stream()
				.map(recipe -> new GuideDTO(recipe.getId(), recipe.getUserNick(), recipe.getTitle(),
						TextHelper.editText(recipe.getText()), recipe.getPhotoUrl(), recipe.getData()))
				.collect(Collectors.toList());
		Collections.reverse(guideDTOS);
		req.setAttribute("guidesList", guideDTOS);

		req.getRequestDispatcher("/pages/allGuides.ftl").forward(req, resp);
	}

	@Override
	public void init() throws ServletException {
		guideService = new GuideServiceImpl();
	}
}
