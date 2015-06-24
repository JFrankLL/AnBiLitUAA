package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class CircleButton extends Button{
    /**
     * Crea un <b>CircleButton</b> que hereda de ({@link Button}) y le asigna una imagen dada por la URL procporcionada.
     * @param path = ruta de una imagen .png
     */
	public CircleButton(String path) {
		super(path);
	}
	/**
	 * Verifica si el mouse está posicionado sobre el botón
	 * @param iX = Gdx.input.getX()
	 * @param iY = Gdx.graphics.getHeight()-Gdx.input.getY()
	 * @return <b>boolean</b>
	 */
	public boolean selected (float iX, float iY) {
		float sx = getX()+getWidth()/2, sy = getY()+getWidth()/2;
    	if(getWidth()/2 > (Math.sqrt((iX-sx)*(iX-sx)+(iY-sy)*(iY-sy))))
    		return true;
		return false;
    }
	public void render(SpriteBatch batch, float x, float y, float width, float height, Camera cam){
		Vector3 vecAux = cam.unproject(new Vector3(x, y, 0));
		setBounds(vecAux.x, vecAux.y, width, height);
		draw(batch);
	}
	public boolean selectedPPM(Camera cam, float x, float y, float width, float height){
		Vector3 vecAux = cam.unproject(new Vector3(x, y, 0));
		float sOx = vecAux.x+width/2, sOy = vecAux.y+height/2;
		Vector3 i = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(i);
    	if(width/2 >= (Math.sqrt((i.x-sOx)*(i.x-sOx)+(i.y-sOy)*(i.y-sOy))))
    		return true;
		return false;
	}
}
