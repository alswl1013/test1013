package com.member.dispacher;

import lombok.Setter;



@Setter
public class ViewResolver {

	
	public String prefix;
	public String suffix;
	
	
	public String getView(String viewName) {
		//index =>"/member/" +"test/index + ".jsp"
	
		return prefix+viewName+suffix;
	}
	
}
