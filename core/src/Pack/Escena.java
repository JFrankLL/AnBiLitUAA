package Pack;

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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Escena implements Screen {
	OrthographicCamera cam;
	Pajaro red;
	TextureRegion back, sling, sling2;
	World world;
	AnBiLit game;
	/*int chido =0;
        Que onda pakensio
    */
	Box2DDebugRenderer dr = new Box2DDebugRenderer();
	
	Sprite sFloor;
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
		red = new Pajaro(world, "red.png");
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		sFloor = new Sprite(new Texture("ground.png"));
		
		//CUERPO ESTATICO (Ground)
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(sFloor.getX() + sFloor.getWidth()/2, sFloor.getY() + sFloor.getHeight()/2);
        bFloor = world.createBody(groundBodyDef);

	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(sFloor.getWidth()/2, sFloor.getHeight()/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = 1f;
	    bFloor.createFixture(fixtureDef);
	    
        shape.dispose();
	}

	@Override
	public void render(float delta) {
		
		//ACTUALIZAR
		cam.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor((245/255f), (255/255f), (255/255f), 1); //RGB
		boolean bul =true;
		
		/*if(true)
        	world.step(0, 6, 2);
		else
			world.step(Gdx.graphics.getDeltaTime()*2, 6, 2);*/
		
		/**
		 * 
		 * Comentario chidongongo azul
		 * 
		 */
		
		
		//MOVER 
        //---------------------------------------------------------------------------------------------------
        mover();
        //DIBUJAR
        //---------------------------------------------------------------------------------------------------
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
			game.batch.draw(back, 0, 0, back.getRegionWidth(), back.getRegionHeight());
			sFloor.setPosition(0,0);
			sFloor.draw(game.batch);
			game.batch.draw(sling, 150, 75, sling.getRegionWidth(), sling.getRegionHeight());
			red.render(game.batch);
			game.batch.draw(sling2, 150, 75, sling.getRegionWidth(), sling.getRegionHeight());
		game.batch.end();
		
			if(Gdx.input.isKeyJustPressed(Input.Keys.A))
				game.setScreen(game.menu);
			/*
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
				red.body.applyForceToCenter(new Vector2(1000000000, 1000000000), true);	*/
		
		dr.setDrawBodies(true);
		dr.setDrawVelocities(true);
		dr.render(world, cam.combined);
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);
		cam.position.set(50 + Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
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
		red.dispose();
	}
	

	public void mover(){
        float iX = Gdx.input.getX(), iY = Gdx.input.getY(), 
        		gH = Gdx.graphics.getHeight(), 
        		rbX = red.body.getPosition().x, rbY = red.body.getPosition().y, x = cam.position.x, 
        		dX = Gdx.input.getDeltaX();
        if(Gdx.input.isTouched()){
        	if(red.sprite.getBoundingRectangle().contains(iX + 50, gH - iY))
        		red.create(world);
        	if(cam.position.x >= 50+Gdx.graphics.getWidth()/2 && cam.position.x <= 1024)
    			cam.position.set(x-dX, cam.position.y, 0);
        	cam.zoom = (dX > 0 && cam.zoom >= 1)? cam.zoom-0.000006f*x : (dX < 0 && cam.zoom <= 1.3)? cam.zoom+0.000006f*x : cam.zoom;
        }
        else
        	red.sprite.setPosition(red.body.getPosition().x - red.sprite.getWidth()/2,
        			red.body.getPosition().y - red.sprite.getWidth()/2);
	}
	/*
	public void lanzar(int x1, int y1, int x2, int y2){
		float t=1/Gdx.graphics.getDeltaTime();
		float hip = (float) Math.sqrt(((x2-x1)*(x2-x1)) + ((y2-y1)*(y2-y1)));
		float Vi = hip/t;
		float cos = Math.abs(x1-x2)/hip;
		float sen = Math.abs(y1-y2)/hip;
		float posX = Vi*cos*t + x1;
		float posY = -(world.getGravity().y*t*t)/2 + Vi*sen*t + y1;
		float velX = Vi*cos;
		float velY = -(world.getGravity().y*t) + Vi*((Math.abs(x1-x2))/(hip));
	
		red.body.setLinearVelocity(velX, velY);
		red.sprite.setPosition(posX, posY);
	*/

}


class Pajaro{
	Sprite sprite;
	Body body;
	public BodyDef bodyDef;
	Texture textura;
	
	Pajaro(World world, String texture){
		textura = new Texture(texture);
		sprite = new Sprite(textura);
		sprite.setPosition(170, 210);
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
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		//sb.begin();
			sprite.draw(sb);
		//sb.end();
	}
}
