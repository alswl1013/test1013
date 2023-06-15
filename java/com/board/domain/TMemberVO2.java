package com.board.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TMemberVO2 {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private LocalDate joinDate;
	private String uuid;
	
}
