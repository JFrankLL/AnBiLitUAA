package entidades.pajaros;

import static utiles.Constantes.PPM;
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

public class Pajaro extends EntityAB implements ComportamientoPajaro{
	
	protected Body body;
	private BodyDef bodyDef;
	
	public boolean tocado = false;
	private boolean lanzado = false;
	protected boolean comportamientoRealizado = false;
	
	float fuerzaLanzamiento = 10;
	
	public Pajaro(World world, String rutaTexture){
		super(world, rutaTexture);
		sprite.setPosition(170/PPM, 210/PPM);
		
		create(world);
	}
	
	private void create(World world) {
		
		//if(body != null) world.destroyBody(body);
		
		bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
	    body = world.createBody(bodyDef);
	    
	    CircleShape shape = new CircleShape();
	    shape.setRadius((-1/4)+sprite.getHeight()/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.density = 1f;//+- peso
	    fixtureDef.friction = 1f;//para que se frene en el suelo
	    fixtureDef.restitution = .5f;//rebote
		fixtureDef.shape = shape;
	    
		body.setAngularDamping(1);//para que gire en el suelo
	    body.createFixture(fixtureDef);
	    body.setGravityScale(0);//para que no se caiga
	    
        shape.dispose();
	}
	public void dispose(){
		super.dispose();
	}
	
	public void render(SpriteBatch sb){//debe ejecutarse con sb ya empezado (sb.start)
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getWidth()/2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);//actualiza ángulo del ave (giración)
		sprite.draw(sb);
		//sb deberá terminar donde fue llamada esta función
	}
	/**
	 * 
	 * @param xib, yib: posición inicial al empezar a estirar la liga (al tomar al pájaro)
	 * @param sling
	 */
	public void lanzar(float xib, float yib, Sling sling) {
		//ya fue lanzado.. cámara!
		if(lanzado) return;
		
		body.setGravityScale(1);//actuar gravedad sobre este pajaro
		if(new Vector2(xib, yib).dst(body.getPosition().x, body.getPosition().y) < sling.dstMax)
		body.applyForceToCenter((xib-body.getPosition().x)*PPM*fuerzaLanzamiento, (yib-body.getPosition().y)*PPM*fuerzaLanzamiento, true);
		lanzado = true;
		/*System.out.println("xib: "+xib+", yib:"+yib);
		System.out.println("xfa: "+body.getPosition().x+", yfa:"+body.getPosition().y);
		System.out.println(((xib-body.getPosition().x)*PPM)+", "+((yib-body.getPosition().y)*PPM*4));*/
	}
	
	@Override //de la interface
	public void comportamiento(){
		//TODO: hacer sonido
		//O lo que sea
		if(!comportamientoRealizado){
			System.out.println("Comportamiento");
			comportamientoRealizado = true;
		}
	}
	
	public boolean isLanzado() {
		return lanzado;
	}
	 
	public void mover(float x, float y) {
		body.setTransform(new Vector2(x, y), body.getAngle());
	}
	
	public Vector2 posision(){
		return new Vector2(body.getPosition().x, body.getPosition().y);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Body getBody() {
		return body;
	}
}

