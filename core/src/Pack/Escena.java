package Pack;

import static utiles.Constantes.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Escena implements Screen {
	OrthographicCamera cam;
	Pajaro pajaro;
	TextureRegion back, sling, sling2;
	World world;
	AnBiLit game;
	
	Box2DDebugRenderer dr = new Box2DDebugRenderer();

	Body bFloor;
	
	public Escena(AnBiLit game){
		this.game = game;
	}

	@Override
	public void show() {
		
		world = new World(new Vector2(0, -9.8f), true);
		back = new TextureRegion(new Texture("background.png"));
		sling = new TextureRegion(new Texture("slingshot.png"));
		sling2 = new TextureRegion(new Texture("slingshot2.png"));
		pajaro = new Pajaro(world, "red.png");
		cam = new OrthographicCamera(Gdx.graphics.getWidth()/PPM, Gdx.graphics.getHeight()/PPM);
		
		//CUERPO ESTATICO (Ground)
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(1024/PPM, 32/PPM);
        bFloor = world.createBody(groundBodyDef);
	    
        PolygonShape shape = new PolygonShape();
	    shape.setAsBox(1024/PPM, 32/PPM);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = 1f;
	    
	    bFloor.createFixture(fixtureDef);
	    
        shape.dispose();
	}

	@Override
	public void render(float delta) {
		world.step(1/60f, 6, 2);
		//ACTUALIZAR
		cam.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor((245/255f), (255/255f), (255/255f), 1); //RGB
		
		//MOVER 
        //---------------------------------------------------------------------------------------------------
        mover();
        //DIBUJAR
        //---------------------------------------------------------------------------------------------------
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
			game.batch.draw(back, 0, 0, back.getRegionWidth()/PPM, back.getRegionHeight()/PPM);
			game.batch.draw(sling, (back.getRegionWidth()*0.07f)/PPM, 64/PPM, sling.getRegionWidth()/PPM, sling.getRegionHeight()/PPM);
			pajaro.render(game.batch);
			game.batch.draw(sling2, (back.getRegionWidth()*0.07f)/PPM, 64/PPM, sling.getRegionWidth()/PPM, sling.getRegionHeight()/PPM);
		game.batch.end();
		
			if(Gdx.input.isKeyJustPressed(Input.Keys.A))
				game.setScreen(game.menu);
			
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				pajaro.body.applyForceToCenter(new Vector2(100, 100), true);
		
		dr.setDrawBodies(true);
		dr.setDrawVelocities(true);
		dr.render(world, cam.combined);
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);
		cam.setToOrtho(false, width/PPM, height/PPM);
		
		//cam.position.set(50 + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		game.batch.dispose();
		world.dispose();
		pajaro.dispose();
	}
	

	public void mover(){
        float iX = cam.unproject(new Vector3(Gdx.input.getX()/PPM, Gdx.input.getY()/PPM, 0)).x,
        		iY = Gdx.input.getY()/PPM, 
        		gH = Gdx.graphics.getHeight()/PPM, 
        		rbX = pajaro.body.getPosition().x/PPM, rbY = pajaro.body.getPosition().y/PPM, x = cam.position.x/PPM, 
        		dX = Gdx.input.getDeltaX()/PPM, scrollDx = 0.000006f*x;
        
        //Movimiento libre con mouse
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
        	if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
        		cam.position.set(cam.position.x-Gdx.input.getDeltaX(), cam.position.y+Gdx.input.getDeltaY(), 0);
        		return;
        	}
        }
        //movimiento Gameplay
        if(Gdx.input.isTouched()){
        	if(pajaro.sprite.getBoundingRectangle().contains(iX/PPM, (gH - iY)/PPM)){
        		System.out.println("Red Tocado");
        		return;
        	}
        	//---Movimiento en 'x'
        	if(dX!=0)
        		dX=(dX>0)? -10:10;
	    	if(x+dX >= (Gdx.graphics.getWidth()/2)/PPM && x+dX <= 1024/PPM){
	    		if(iX > (150+sling.getTexture().getWidth())/PPM){	
	    			cam.position.x += dX;
		    		//---Zoom de cam
					cam.zoom += (dX/10 > 0 && cam.zoom-scrollDx > 0.5)? -scrollDx/PPM : (dX/10 < 0 && cam.zoom+scrollDx <= 1 )? scrollDx/PPM : 0;
					//cam.position.y += (dX/10 > 0 && cam.zoom-scrollDx > 0.5)? -scrollDx/PPM : (dX/10 < 0 && cam.zoom+scrollDx <= 1 )? scrollDx/PPM : 0;
	    		}
	    	}
        }
        //cam.position.set(red.posision(), 0);
	}

}

class Pajaro{
	Sprite sprite;
	Body body;
	public BodyDef bodyDef;
	Texture textura;
	
	Pajaro(World world, String texture){
		textura = new Texture(texture);
		sprite = new Sprite(textura);
		sprite.setPosition(170/PPM, 210/PPM);
		sprite.setSize(sprite.getWidth()/PPM, sprite.getHeight()/PPM);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		
		create(world);
	}
	public void create(World world) {
		
		if(body != null)
			world.destroyBody(body);
		
		bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
	    body = world.createBody(bodyDef);
	    
	    CircleShape shape = new CircleShape();
	    shape.setRadius(sprite.getHeight()/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.density = 1f;
	    fixtureDef.friction = 1f;
	    fixtureDef.restitution = .5f;
		fixtureDef.shape = shape;
	    
		body.setAngularDamping(5);
	    body.createFixture(fixtureDef);
	    
        shape.dispose();
	}
	public void dispose(){
		textura.dispose();
	}
	
	public void render(SpriteBatch sb){
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getWidth()/2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		//sb.begin();
			sprite.draw(sb);
		//sb.end();
	}
	
	public Vector2 posision(){
		return new Vector2(body.getPosition().x, body.getPosition().y);
	}
}
