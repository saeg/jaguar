package br.usp.each.saeg.jaguar.core.infra;


/**
 * @author Henrique Ribeiro
 * 
 */
public class StringUtils {
	
	//TODO javadoc e testes
	public static String getParametros(String desc) {
		String result = "";

		String soParametros = desc.split("\\)")[0];
		soParametros = soParametros.substring(1);
		boolean isFirst = true;
		while (soParametros.length() != 0) {
			boolean closeArray = false;
			
			if (!isFirst) {
				result += ",";
			}
			
						
			if (soParametros.charAt(0) == '['){
				closeArray = true;
				soParametros = soParametros.substring(1);
			}

			switch (soParametros.charAt(0)) {
			case 'Z':
				result += "boolean";
				break;
			case 'B':
				result += "byte";
				break;
			case 'C':
				result += "char";
				break;
			case 'D':
				result += "double";
				break;
			case 'F':
				result += "float";
				break;
			case 'I':
				result += "int";
				break;
			case 'J':
				result += "long";
				break;
			case 'S':
				result += "short";
				break;
			case 'L':
				String className = soParametros.split(";")[0];
				soParametros = soParametros.substring(className.length() + 1);
				if (className.equals("Ljava/lang/Boolean")) {
					result += "boolean";
				} else if (className.equals("Ljava/lang/Byte")) {
					result += "byte";
				} else if (className.equals("Ljava/lang/Character")) {
					result += "char";
				} else if (className.equals("Ljava/lang/Double")) {
					result += "double";
				} else if (className.equals("Ljava/lang/Float")) {
					result += "float";
				} else if (className.equals("Ljava/lang/Integer")) {
					result += "int";
				} else if (className.equals("Ljava/lang/Long")) {
					result += "long";
				} else if (className.equals("Ljava/lang/Short")) {
					result += "short";
				} else {
					String[] classPack = className.split("/");
					result += classPack[classPack.length - 1];
				}
				break;
			}
			soParametros = soParametros.substring(1);
			
			isFirst = false;
			if (closeArray){
				result += "[]";
				closeArray = false;
			}
		}

		return result;
	}
}
