package com.ex01.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TMemberVO {

	 private String id;
	 private String pwd;
	 private String name;
	 private String email;
	 private LocalDate joinData;
	 
}
