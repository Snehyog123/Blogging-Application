package com.Infopeer.Blogging_Application.payloads;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AppResponce {
	
	private String message;
	
	private boolean success;
	
	private LocalDateTime localDateTime;

}
