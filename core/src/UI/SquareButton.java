package UI;

public class SquareButton extends Button{
	public SquareButton(String path) {
		super(path);
	}
	public boolean selected (float iX, float iY, float gH) {
		float sX = getX(), sY = getY();
    	if (iX > sX && iX < sX + getWidth())
    		if ((gH-iY) > sY && (gH-iY) < (sY+getHeight()))
            	return true;
		return false;
    }
}
