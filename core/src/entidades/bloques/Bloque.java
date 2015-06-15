package entidades.bloques;

import static utiles.Constantes.PPM;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import entidades.EntityAB;

public abstract class Bloque extends EntityAB {
	
	public int vida = 100;
	
	public Bloque(World world, String[] rutasSprites, float x, float y, short angulo) {
		super(world, rutasSprites[0]);
		
		sprite.setPosition(x/PPM, y/PPM);
		sprite.setOriginCenter();
		sprite.setScale(0.5f);
		
		bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
	    bodyDef.angle = (float) Math.toRadians(angulo);
	    body = world.createBody(bodyDef);
	    
	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox((sprite.getWidth()-0.1f)/4, (sprite.getHeight()-0.1f)/4);
	    
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.density = 1f;//+- peso
	    fixtureDef.friction = 5f;//para que se frene en el suelo
	    fixtureDef.restitution = 0.1f;//rebote
		fixtureDef.shape = shape;
	    
		body.setAngularDamping(1);//para que se frene en el suelo
	    body.createFixture(fixtureDef);
	    
	    //body.setUserData(new Vector2(8f, 3f));//aguante [presion, golpe]
	    body.setUserData(this);
	    
        shape.dispose();
	}

	@Override
	public void render(SpriteBatch sb) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);//actualiza ángulo del ave (giración)
		sprite.draw(sb);
	}
	
}
