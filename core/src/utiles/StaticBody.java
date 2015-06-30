package utiles;

import static utiles.Constantes.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class StaticBody {
	public Body body;
	public Sprite sprite;
	private Vector2 modelOrigin;
	
	public StaticBody(World world, String path, String name, Texture texture) {
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(path));
		
		BodyDef bd = new BodyDef();
		bd.position.set(0, -128/PPM);
		bd.type = BodyType.StaticBody;
		
		FixtureDef fd = new FixtureDef();
		fd.friction = 0.5f;
		fd.restitution = 0.3f;
		
		body = world.createBody(bd);
		loader.attachFixture(body, name, fd, texture.getWidth()/PPM);
		sprite = new Sprite(texture);
		sprite.setSize(texture.getWidth()/PPM, texture.getHeight()/PPM);
		modelOrigin = loader.getOrigin(name, texture.getWidth()/PPM).cpy();
	}
	public void draw (SpriteBatch batch) {
	    Vector2 bodyPos = body.getPosition().sub(modelOrigin);
	    sprite.setPosition(bodyPos.x, bodyPos.y);
	    sprite.draw(batch);
	}
	public void dispose(){
		sprite.getTexture().dispose();
	}
}
