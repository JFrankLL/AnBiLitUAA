package UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button extends Sprite{
    static Texture texture;

    public Button(String path) {
    	super(texture = new Texture(path));
    	setSize(texture.getWidth(), texture.getHeight());
    }
    public void setBounds(float x, float y, float width, float height){
    	setPosition(x, y);
    	setSize(width, height);
    }
    public void press(String path){
    	setTexture(texture = new Texture(path));
    }
}
