package entidades.cerdos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import entidades.EntityAB;

public class CerdoBase extends EntityAB {
	public CerdoBase(World world, String rutaSprite) {
		super(world, rutaSprite);
		
		bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
	    body = world.createBody(bodyDef);
	    
	    CircleShape shape = new CircleShape();
	    shape.setRadius(sprite.getHeight()/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.density = 1f;//+- peso
	    fixtureDef.friction = 5f;//para que se frene en el suelo
	    fixtureDef.restitution = 0.2f;//rebote
		fixtureDef.shape = shape;
	    
		body.setAngularDamping(10.5f);//para que gire en el suelo
	    body.createFixture(fixtureDef);
	    body.setGravityScale(1);
	    
	    //body.setUserData(new Vector2(1f, 1f));
	    aguanteGolpe = 1f;
	    body.setUserData(this);
	    
        shape.dispose();
		
	}

	@Override
	public void render(SpriteBatch sb){//debe ejecutarse con sb ya empezado (sb.start)
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, (body.getPosition().y) - sprite.getHeight()/2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);//actualiza ángulo del ave (giración)
		sprite.draw(sb);
		//sb deberá terminar donde fue llamada esta función
	}
}	