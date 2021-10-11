package ru.itis.ruzavin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	private String nick;
	private String email;
	private String login;
	private String password;
}
