package com.member.dispacher;

import java.util.HashMap;
import java.util.Map;

import com.member.controller.MemberListcontroller;
import com.member.dispacher.controller.MemberupdatetController;
import com.member.dispacher.controller.MemberRegisterController;
import com.member.dispacher.controller.MemberListController;

public class HandlerMapping {
	
	//Controller를 구현한 객체들을 저장하는 Map

	private Map<String, Controller> mappings;
	
	public HandlerMapping() {
		mappings = new HashMap<>();
		
		mappings.put("/index.do", new MemberListController());
		mappings.put("/reister.do", new MemberRegisterController());
		mappings.put("/insert.do", new MemberupdatetController());
	
	
	}
	public Controller getController(String path) {
		//Map에 등록된 Controller들 중에서 특정경로에 해당하는 Controller반환
		
		return  mappings.get(path);
		
	}

}
