package UI;

public class CircleButton extends Button{
	public CircleButton(String path) {
		super(path);
	}
	public boolean selected (float iX, float iY, float gH) {
		float sx = getX()+getWidth()/2, sy = getY()+getWidth()/2;
    	if(getWidth()/2 > (Math.sqrt((iX-sx)*(iX-sx)+(gH-iY-sy)*(gH-iY-sy))))
    		return true;
		return false;
    }
}
