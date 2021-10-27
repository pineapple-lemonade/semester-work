package ru.itis.ruzavin.helper;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryHelper {
	private static Cloudinary cloudinary;

	public static Cloudinary getInstance() {
		if (cloudinary == null) {
			cloudinary = new Cloudinary(ObjectUtils.asMap(
					"cloud_name", "de5binygw",
					"api_key", "967433251694596",
					"api_secret", "DmISE6bttvpM-ZV3TaBWsTYaRIY"));
		}

		return cloudinary;
	}
}