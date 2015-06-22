package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public abstract class EntityAB {
	
	protected Texture textura;
	protected Sprite sprite;
	protected Body body;
	protected BodyDef bodyDef;
	
	public float danio, normalMax=0f, tangentMax=0f;
	public int vida = 100;
	
	public EntityAB(String rutaSprite) {
		textura = new Texture(rutaSprite);
		sprite = new Sprite(textura);
		sprite.setOrigin((sprite.getWidth()/2), (sprite.getHeight()/2));
		sprite.setSize(sprite.getWidth(), sprite.getHeight());
	}
	
	public abstract void render(SpriteBatch sb);
	protected abstract void actualizar();
	
	public void dispose() {
		textura.dispose();
	}
	
	public Body getBody() {
		return body;
	}
	
}
