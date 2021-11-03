package ru.itis.ruzavin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guide {
	private int id;

	public Guide(int userId, String title, String text, String photoUrl, String data) {
		this.userId = userId;
		this.title = title;
		this.text = text;
		this.photoUrl = photoUrl;
		this.data = data;
	}

	private int userId;
	private String title;
	private String text;
	private String photoUrl;
	private String data;
}
