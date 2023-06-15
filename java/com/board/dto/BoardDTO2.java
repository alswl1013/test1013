 package com.board.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO2 {
	
	
   private int articleNO;
   private int ParentNO;
   private String title;
   private String content;
   private LocalDate writeDate;
   private String imageFileName;
   private String id;
   private int level;
   
   
}
