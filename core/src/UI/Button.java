package UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button {
	public Sprite skin;
    Texture texture;

    public Button(String path, float x, float y) {
    	skin = new Sprite(texture = new Texture(path));
    	skin.setPosition(x, y);
    	skin.setSize(texture.getWidth(), texture.getHeight());
    }
    public void press(String path){
    	skin.setTexture(texture = new Texture(path));
    }
}
