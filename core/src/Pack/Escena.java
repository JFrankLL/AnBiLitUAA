package Pack;

import static utiles.Constantes.PPM;
import utiles.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Escena implements Screen {
	OrthographicCamera cam;
	Pajaro pajaro;
	TextureRegion back, sling, sling2;
	World world;
	AnBiLit game;
	
	Box2DDebugRenderer dr = new Box2DDebugRenderer();

	Body bFloor;
	
	boolean click=false;
	private Vector2 vec2;
	
	public Escena(AnBiLit game){
		this.game = game;
	}

	@Override
	public void show() {
		
		world = new World(new Vector2(0, 0), true);
		back = new TextureRegion(new Texture("background.png"));
		sling = new TextureRegion(new Texture("slingshot.png"));
		sling2 = new TextureRegion(new Texture("slingshot2.png"));
		pajaro = new Pajaro(world);
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
	    
	    dr.setDrawBodies(true);
		dr.setDrawVelocities(true);
	    
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
		click = click();
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
				pajaro.lanzar(100, 100);
		
		dr.render(world, cam.combined);
	}

	@Override
	public void resize(int width, int height) {
		cam = new OrthographicCamera(Gdx.graphics.getWidth()/PPM, Gdx.graphics.getHeight()/PPM);
		cam.setToOrtho(false, width/PPM, height/PPM);
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
	
	
	public boolean click() {
		if(!click)
		vec2 = new Vector2(cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).x, cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).y);	
		System.out.println(vec2.toString());
		return Gdx.input.isTouched();
	}
	
	public void mover(){
        float iX = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x,
        		iY = Gdx.input.getY()/PPM, 
        		gH = Gdx.graphics.getHeight()/PPM, 
        		rbX = pajaro.posision().x/PPM, rbY = pajaro.posision().y/PPM, x = cam.position.x, 
        		dX = Gdx.input.getDeltaX(), scrollDx = 0.000003f*x;
        
        //Movimiento libre con mouse
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
        	if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
        		cam.position.set(cam.position.x-Gdx.input.getDeltaX(), cam.position.y+Gdx.input.getDeltaY(), 0);
        		return;
        	}
        }
        //Movimiento Gameplay
        if(click){//si click
        	if(pajaro.getSprite().getBoundingRectangle().contains(iX, (gH - iY-pajaro.getSprite().getWidth()/2))){
        		pajaro.tocado = true;
        		//TODO: perseguir pajaro con la camara y bloquear volver a tirar
        	}
        	//---Movimiento en 'x' de la camara
        	if(dX!=0) dX=(dX>0)? -10 : 10;
        	if(x+dX/PPM > (Gdx.graphics.getWidth()/2)/PPM && x+dX < (back.getRegionWidth()-Gdx.graphics.getWidth()/2)/PPM && !pajaro.tocado) {
	    		if(iX > (150+sling.getTexture().getWidth())/PPM){//despues de la resortera
	    			cam.position.x += dX/PPM;
					cam.zoom += (dX > 0 && cam.zoom-scrollDx > 0.7)? -scrollDx*PPM : (dX < 0 && cam.zoom+scrollDx <= 1 )? scrollDx*PPM : 0;
				}
        	}
        
        }
        else {//no click
        	if(!pajaro.tocado)
        		return;
    		pajaro.tocado = false;
    		pajaro.lanzar(vec2.x, vec2.y);
		}
        pajaro.mover(iX, cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y);
	}


class Pajaro{
	private Sprite sprite;
	private Body body;
	public BodyDef bodyDef;
	private Texture textura;
	private World world;
	
	boolean tocado = false;
	private boolean lanzado = false;
	
	Pajaro(World world){
		this.world = world;
		textura = new Texture(Constantes.Graficas.strTexRed);
		sprite = new Sprite(textura);
		sprite.setPosition(170/PPM, 210/PPM);
		sprite.setSize(sprite.getWidth()/PPM, sprite.getHeight()/PPM);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		
		create(world);
	}
	
	private void create(World world) {
		
		if(body != null)
			world.destroyBody(body);
		
		bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
	    body = world.createBody(bodyDef);
	    
	    CircleShape shape = new CircleShape();
	    shape.setRadius((-1/4)+sprite.getHeight()/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.density = 1f;
	    fixtureDef.friction = 1f;
	    fixtureDef.restitution = .5f;
		fixtureDef.shape = shape;
	    
		body.setAngularDamping(1);
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
		//System.out.println(cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).x+","+cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).y);
	}
	
	public void lanzar(float xi, float yi) {
		if(lanzado)
			return;
		Vector2 vec2 = new Vector2((cam.unproject(new Vector3(Gdx.input.getX(), 0, 0))).x-xi,
				(cam.unproject(new Vector3(0, Gdx.input.getY(), 0))).y-yi);
		world.setGravity(new Vector2(0, -9.8f));
		body.applyForceToCenter((float)(xi*PPM), (float)(vec2.y*PPM), true);
		lanzado = true;
	}
	
	public void mover(float x, float y) {
		if(x < (214)/PPM)
			if(tocado)
			body.setTransform(new Vector2(x, y), body.getAngle());
	}
	
	public Vector2 posision(){
		return new Vector2(body.getPosition().x, body.getPosition().y);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
}
