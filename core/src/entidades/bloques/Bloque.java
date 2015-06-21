package entidades.bloques;

import static utiles.Constantes.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import entidades.EntityAB;

public abstract class Bloque extends EntityAB {
	
	Sprite[] sprites = new Sprite[4];
	
	public Bloque(World world, String[] rutasSprites, float x, float y, short angulo) {
		super(rutasSprites[0]);
		
		for(int i=0; i<rutasSprites.length; i++){
			sprites[i] = new Sprite(new Texture(rutasSprites[i]));
			sprites[i].setOrigin((sprite.getWidth()/4), (sprite.getHeight()/4));
			sprites[i].setSize(sprite.getWidth()/2, sprite.getHeight()/2);
		}
		
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
	    fixtureDef.friction = 1.0f;//para que se frene en el suelo
	    fixtureDef.restitution = 0.1f;//rebote
		fixtureDef.shape = shape;
	    
		body.setAngularDamping(1);//para que se frene en el suelo
	    body.createFixture(fixtureDef);
	    
	    body.setUserData(this);
	    danio = 10;
	    
        shape.dispose();
	}

	@Override
	public void render(SpriteBatch sb) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);//actualiza ángulo del ave (giración)
		sprite.draw(sb);
	}	
	public void actualizar(){
		try{
			sprite = sprites[4-(int)(vida/25)];
		}catch(Exception e){
			sprite = sprites[0];
		}
		System.out.println("actualizado "+ this.getClass().getSimpleName()+" vida: "+vida);
	}
	public abstract boolean daniar(EntityAB daniador);

}
