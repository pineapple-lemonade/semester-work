package ru.itis.ruzavin.helper;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public class ImageHelper {
	public static File makeFile(Part part) throws IOException {
		String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		InputStream content = part.getInputStream();
		File file = new File(fileName);
		FileOutputStream outputStream = new FileOutputStream(file);
		byte[] buffer = new byte[content.available()];
		content.read(buffer);
		outputStream.write(buffer);

		return file;
	}
}
