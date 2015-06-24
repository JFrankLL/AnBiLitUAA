package UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button extends Sprite{
    static Texture texture;
    public boolean pressed;
    
    /**
     * Crea un Button a partir de la ruta {@link path} de la imagen enviada por parámetro 
     * @param path = URL de la imagen asignada a la textura del botón
     */
    public Button(String path) {
    	super(texture = new Texture(path));
    	setSize(texture.getWidth(), texture.getHeight());
    	pressed = false;
    }

    /**
     * Posiciona el botón dadas las coordenadas en el eje X y Y, además de asignarle un alto y un ancho
     */
    public void setBounds(float x, float y, float width, float height){
    	setPosition(x, y);
    	setSize(width, height);
    }
    
    /**
     * Cambia la imagen relacionada al botón por la nueva imagen asociada a la URL.
     * @param path = ruta de una imagen .png
     */
    public void press(String path){
    	setTexture(texture = new Texture(path));
    }
}
