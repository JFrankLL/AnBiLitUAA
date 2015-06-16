package UI;

public class CircleButton extends Button{
	public CircleButton(String path, float x, float y) {
		super(path, x, y);
	}
	public boolean selected (float ix, float iy, float gH) {
		float sx = skin.getOriginX(), sy = skin.getOriginY();
    	if(skin.getWidth()/2.1 > (Math.sqrt((ix-sx)*(ix-sx)+(gH-iy-sy)*(gH-iy-sy))))
    		return true;
		return false;
    }
}
