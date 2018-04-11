package br.usp.each.saeg.jaguar.plugin.data;

import java.math.BigDecimal;

import org.eclipse.swt.graphics.Color;

import br.usp.each.saeg.jaguar.plugin.utils.ColorFactory;
import br.usp.each.saeg.jaguar.plugin.utils.ColorFactory.ColorFactoryEnum;

/**
 * @author Mario Concilio (marioconcilio@gmail.com)
 */
public abstract class AbstractData {

	protected String value;
	protected float score;
	
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public float getScore() {
        return score;
    }
    public void setScore(float score) {
        this.score = score;
    }
	
    public float getRoundedScore(int scale) {
        return roundScore(score,scale);
    }
	
    public float roundScore(float originalScore, int scale) {
    	BigDecimal roundedScore = new BigDecimal(Float.toString(originalScore));
    	roundedScore = roundedScore.setScale(scale, BigDecimal.ROUND_DOWN);
    	return roundedScore.floatValue();
    }
    
    public Color getBackgroundColor() {
    	if (score <= 0.25) {
    		return ColorFactory.getColor(ColorFactoryEnum.GREEN);
    	}
    	else if (score <= 0.5) {
    		return ColorFactory.getColor(ColorFactoryEnum.YELLOW);
    	}
    	else if (score <= 0.75) {
    		return ColorFactory.getColor(ColorFactoryEnum.ORANGE);
    	}
    	else {
    		return ColorFactory.getColor(ColorFactoryEnum.RED);
    	}
    	
    }
	
}
