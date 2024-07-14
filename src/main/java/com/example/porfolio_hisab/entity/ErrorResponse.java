package com.example.porfolio_hisab.entity;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class ErrorResponse {
		
		private int status;
		private String message;
		private LocalDateTime timeStamp;
	

}
