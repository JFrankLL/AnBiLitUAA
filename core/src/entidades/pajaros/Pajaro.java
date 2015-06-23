package entidades.pajaros;

import utiles.Constantes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import entidades.EntityAB;
import entidades.Sling;

public abstract class Pajaro extends EntityAB implements ComportamientoPajaro{
	
	public boolean tocado = false, lanzado = false;
	public boolean comportamientoRealizado = false;
	
	public String tipo;
	public float fuerzaLanzamiento = 10;
	
	public Pajaro(World world, String rutaTexture){
		super(rutaTexture);
		sprite.setPosition(170, 210);
		
		bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
	    body = world.createBody(bodyDef);
	    
	    CircleShape shape = new CircleShape();
	    shape.setRadius(sprite.getHeight()/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.density = 5f;//+- peso
	    fixtureDef.friction = 1f;//para que se frene en el suelo
	    fixtureDef.restitution = .6f;//rebote
		fixtureDef.shape = shape;
	    
		body.setAngularDamping(10.5f);//para que gire en el suelo
	    body.createFixture(fixtureDef);
	    body.setGravityScale(0);//para que no se caiga
	    
	    normalMax = 10f;
	    danio = 25;
	    body.setUserData(this);
	    
        shape.dispose();
	}
	
	public void dispose(){
		super.dispose();
	}
	
	public void render(SpriteBatch sb){//debe ejecutarse con sb ya empezado (sb.start)
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, (body.getPosition().y) - sprite.getHeight()/2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);//actualiza �ngulo del ave (giraci�n)
		sprite.draw(sb);
		//sb deber� terminar donde fue llamada esta funci�n
	}

	public void lanzar(float xib, float yib, Sling sling) {
		//ya fue lanzado.. c�mara!
		if(lanzado) return;
		
		body.setGravityScale(1);//actuar gravedad sobre este pajaro
		//if(new Vector2(xib, yib).dst(body.getPosition().x, body.getPosition().y) < sling.dstMax)
		body.applyForceToCenter((xib-body.getPosition().x)*fuerzaLanzamiento, (yib-body.getPosition().y)*fuerzaLanzamiento, true);
		lanzado = true;
		Constantes.seguirPajaro = true;
	}
	
	@Override //de la interface
	public boolean comportamiento(){
		if(lanzado)
			if(!comportamientoRealizado){
				comportamientoRealizado = true;
				System.out.println("comportamiento");
			}
		return comportamientoRealizado;
	}
	 
	public void mover(float x, float y) {
		body.setTransform(x, y, body.getAngle());
	}
	
	public void mover(Vector2 vec2) {
		body.setTransform(vec2.x, vec2.y, body.getAngle());
	}
	
	public Vector2 posision(){
		return new Vector2(body.getPosition().x, body.getPosition().y);
	}
	
	public abstract boolean isComportamientoRealizado();
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Body getBody() {
		return body;
	}
}

