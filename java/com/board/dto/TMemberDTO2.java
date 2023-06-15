package com.board.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TMemberDTO2{
	private String id;
	private String pwd;
	private String name;
	private String email;
	private LocalDate joinDate;
	private String uuid;
	
}
