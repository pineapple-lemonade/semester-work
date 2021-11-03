package ru.itis.ruzavin.servlets;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.helper.CloudinaryHelper;
import ru.itis.ruzavin.helper.ImageHelper;
import ru.itis.ruzavin.models.Guide;
import ru.itis.ruzavin.services.GuideService;
import ru.itis.ruzavin.services.GuideServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(urlPatterns = "/addGuide")
@MultipartConfig(
		maxFileSize = 5 * 1024 * 1024,
		maxRequestSize = 10 * 1024 * 1024
)
public class AddGuideServlet extends HttpServlet {

	private GuideService guideService;

	private Cloudinary cloudinary;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/pages/addGuide.ftl").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String text = req.getParameter("content");
		UserDTO userDTO = (UserDTO) req.getAttribute("user");
		int userId = userDTO.getId();

		Part part = req.getPart("photo");
		File file = ImageHelper.makeFile(part);
		Map upload = cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", file.getName()));
		String url = (String) upload.get("url");

		Date date = new Date();
		SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

		Guide guide = new Guide(userId, title, text, url, formatForDateNow.format(date));
		guideService.save(guide);

		resp.sendRedirect("/addGuide");
	}

	@Override
	public void init() throws ServletException {
		guideService = new GuideServiceImpl();
		cloudinary = CloudinaryHelper.getInstance();
	}
}
