package com.ex01.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TMemberDTO {

	private String id;
	 private String pwd;
	 private String name;
	 private String email;
	 private LocalDate joinDate;
	private String uuid;
	
	
	
}
