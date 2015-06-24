package entidades.pajaros;

import static utiles.Constantes.PPM;

import java.util.LinkedList;

import utiles.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import entidades.EntityAB;
import entidades.Sling;

public abstract class Pajaro extends EntityAB implements ComportamientoPajaro{
	
	public boolean tocado = false, lanzado = false;
	public boolean comportamientoRealizado = false;
	
	public LinkedList<Vector3> trayectoria;
	Sprite trayectoriaSprite;
	public boolean dibujarTrayectoria = true;
	long ultimoStep;
	Sound flying;
	public String tipo;
	public float fuerzaLanzamiento = 10;
	
	
	public Pajaro(World world, String rutaTexture){
		super(rutaTexture);
		sprite.setPosition(170/PPM, 210/PPM);
		
		trayectoriaSprite = new Sprite(new Texture("pTrayectoria.png"));
		
		ultimoStep = System.currentTimeMillis();
		
		bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
	    body = world.createBody(bodyDef);
	    
	    CircleShape shape = new CircleShape();
	    shape.setRadius(sprite.getHeight()/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.density = 1f;//+- peso
	    fixtureDef.friction = 1f;//para que se frene en el suelo
	    fixtureDef.restitution = .5f;//rebote
		fixtureDef.shape = shape;
	    
		body.setAngularDamping(10.5f);//para que gire en el suelo
	    body.createFixture(fixtureDef);
	    body.setGravityScale(0);//para que no se caiga
	    
	    normalMax = 10f;
	    danio = 25;
	    trayectoria = new LinkedList<Vector3>();
	    body.setUserData(this);
	    
        shape.dispose();
        flying = Gdx.audio.newSound(Gdx.files.internal("Audio/flying.wav"));
	}
	
	public void dispose(){
		super.dispose();
	}
	
	public void render(SpriteBatch sb){//debe ejecutarse con sb ya empezado (sb.start)
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, (body.getPosition().y) - sprite.getHeight()/2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);//actualiza �ngulo del ave (giraci�n)
		//trayectoria
		for(Vector3 vec: trayectoria){
			if(vec.z == 0)
				sb.draw(trayectoriaSprite, vec.x-sprite.getWidth()/2, vec.y-sprite.getHeight()/2,0.3f,0.3f);
			else
				sb.draw(trayectoriaSprite, vec.x-sprite.getWidth()/2, vec.y-sprite.getHeight()/2,0.5f,0.5f);
		}
		//pajaro
		sprite.draw(sb);
		if(lanzado && dibujarTrayectoria && System.currentTimeMillis()>ultimoStep+50){
			trayectoria.add(new Vector3(posision(),0));
			ultimoStep = System.currentTimeMillis();
		}
		//sb deber� terminar donde fue llamada esta funci�n
	}

	public void lanzar(float xib, float yib, Sling sling) {
		//ya fue lanzado.. c�mara!
		if(lanzado) return;
		flying.play();
		body.setGravityScale(1);//actuar gravedad sobre este pajaro
		//if(new Vector2(xib, yib).dst(body.getPosition().x, body.getPosition().y) < sling.dstMax)
		body.applyForceToCenter((xib-body.getPosition().x)*PPM*fuerzaLanzamiento, (yib-body.getPosition().y)*PPM*fuerzaLanzamiento, true);
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
		trayectoria.add(new Vector3(posision(),1));//agraga un punto mas grande "" uso z de vector 3
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
	
	public void bloqueo(){
		dibujarTrayectoria = false;
		comportamientoRealizado = true;
	}
}
