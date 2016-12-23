package br.usp.each.saeg.jaguar.plugin.utils;

import org.eclipse.jface.dialogs.IInputValidator;

import br.usp.each.saeg.jaguar.plugin.Configuration;

public class IDValidator implements IInputValidator{

	@Override
	public String isValid(String text) {
		if(text.length() != 8){
			return (Configuration.LANGUAGE_EN)?"Invalid ID!":"ID invalido!";
		}
		for(int i = 0; i < text.length(); i++){
			if(text.charAt(i) >= '0' && text.charAt(i) <= '9'){
				
			}else{
				return (Configuration.LANGUAGE_EN)?"Invalid ID!":"ID invalido!";
			}
		}
		return null;
	}

}
