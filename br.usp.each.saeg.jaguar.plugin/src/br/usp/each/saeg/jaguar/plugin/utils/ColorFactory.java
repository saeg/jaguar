package br.usp.each.saeg.jaguar.plugin.utils;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @author Mario Concilio (marioconcilio@gmail.com)
 */
public class ColorFactory {
	
	public enum ColorFactoryEnum {
		GREEN(new Color(Display.getCurrent(),192, 255, 192)),
		YELLOW(new Color(Display.getCurrent(),255,255,128)),
		ORANGE(new Color(Display.getCurrent(),255, 204, 153)),
		RED(new Color(Display.getCurrent(),255,160,160));
		
		private Color color;
		
		ColorFactoryEnum(Color color) {
			this.color = color;
		}
		
		public Color getColor() {
			return this.color;
		}
	}
	
	public static Color getColor(ColorFactoryEnum color) {
		return color.getColor();
	}

}
