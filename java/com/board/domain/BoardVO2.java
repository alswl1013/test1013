package com.board.domain;

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
public class BoardVO2 {

	
	   private int articleNO;
	   private int ParentNO;
	   private String title;
	   private String content;
	   private LocalDate writeDate;
	   private String imageFileName;
	   private String id;
	   private int level;



}
