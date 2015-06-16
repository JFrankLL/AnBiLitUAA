package UI;

public class SquareButton extends Button{
	public SquareButton(String path, float x, float y) {
		super(path, x, y);
	}
	public boolean selected (float ix, float iy, float gH) {
		float sX = skin.getX(), sY = skin.getY();
    	if (ix > sX && ix < sX + skin.getWidth())
    		if ((gH-iy) > sY && (gH-iy) < (sY+skin.getHeight()))
            	return true;
		return false;
    }
}
