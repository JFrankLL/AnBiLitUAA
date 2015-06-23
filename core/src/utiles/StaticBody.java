package utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class StaticBody {
	public Body body;
	public Sprite sprite;
	private Vector2 modelOrigin;
	
	public StaticBody(World world, String path, String name, Texture texture) {
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(path));
		
		BodyDef bd = new BodyDef();
		bd.position.set(-124, -124);
		bd.type = BodyType.StaticBody;
		
		FixtureDef fd = new FixtureDef();
		//fd.density = 100000;
		fd.friction = 1f;
		fd.restitution = 0f;
		
		body = world.createBody(bd);
		body.setGravityScale(0);
		loader.attachFixture(body, name, fd, texture.getWidth());
		sprite = new Sprite(texture);
		modelOrigin = loader.getOrigin(name, texture.getWidth()).cpy();
	}
	public void draw (SpriteBatch batch) {
	    Vector2 bodyPos = body.getPosition().sub(modelOrigin);
	    sprite.setPosition(bodyPos.x, bodyPos.y);
	    sprite.setOrigin(modelOrigin.x, modelOrigin.y);
	    sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
	    sprite.draw(batch);
	}
}
