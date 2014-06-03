package br.usp.each.saeg.jaguar.infra;


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

			switch (soParametros.charAt(0)) {
			case '[':
				result += "[";
				closeArray = true;
			case 'Z':
				result += "boolean";
				soParametros = soParametros.substring(1);
				break;
			case 'B':
				result += "byte";
				soParametros = soParametros.substring(1);
				break;
			case 'C':
				result += "char";
				soParametros = soParametros.substring(1);
				break;
			case 'D':
				result += "double";
				soParametros = soParametros.substring(1);
				break;
			case 'F':
				result += "float";
				soParametros = soParametros.substring(1);
				break;
			case 'I':
				result += "int";
				soParametros = soParametros.substring(1);
				break;
			case 'J':
				result += "long";
				soParametros = soParametros.substring(1);
				break;
			case 'S':
				result += "short";
				soParametros = soParametros.substring(1);
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
			
			isFirst = false;
			if (closeArray){
				result += "]";
				closeArray = false;
			}
		}

		return result;
	}
}
