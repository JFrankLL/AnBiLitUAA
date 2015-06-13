package Pack;

import static utiles.Constantes.PPM;
import utiles.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import entidades.Sling;
import entidades.pajaros.Pajaro;
import entidades.pajaros.PajaroAmarillo;
import entidades.pajaros.PajaroRedGrande;

public class Escena implements Screen {
	OrthographicCamera cam;
	Pajaro pajaro;
	Sling sling;
	TextureRegion back;
	World world;
	AnBiLit game;
	
	Box2DDebugRenderer dr = new Box2DDebugRenderer();

	Body bFloor;
	
	private Vector2 vec2;
	
	public Escena(AnBiLit game){
		this.game = game;
	}

	@Override
	public void show() {
		
		world = new World(new Vector2(0, -9.8f), true);
		back = new TextureRegion(new Texture("background.png"));
		//pajaro = new Pajaro(world, Constantes.Graficas.strTexRed);
		
		pajaro = new PajaroRedGrande(world);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth()/PPM, Gdx.graphics.getHeight()/PPM);
		sling = new Sling(world, "slingshot.png", "slingshot2.png", cam);
		sling.setPajaro(pajaro);
		
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
		Constantes.click = click();
        mover();
        //DIBUJAR
        //---------------------------------------------------------------------------------------------------
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
			game.batch.draw(back, 0, 0, back.getRegionWidth()/PPM, back.getRegionHeight()/PPM);
			game.batch.draw(sling.getTextura(), (back.getRegionWidth()*0.07f)/PPM, 64/PPM, sling.getTextura().getWidth()/PPM, sling.getTextura().getHeight()/PPM);
			pajaro.render(game.batch);
			game.batch.draw(sling.getTexturaAlt(), (back.getRegionWidth()*0.07f)/PPM, 64/PPM, sling.getTexturaAlt().getWidth()/PPM, sling.getTexturaAlt().getHeight()/PPM);
			sling.render(game.batch);
		game.batch.end();
		
		
			if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
				game.setScreen(game.menu);
				return;
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.F4))
					Constantes.Configuracion.debugRender=(Constantes.Configuracion.debugRender)?false:true;
		
		if(Constantes.Configuracion.debugRender)
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
		if(!Constantes.click)
			vec2 = new Vector2(cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).x, cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).y);	
		//System.out.println(vec2.toString());
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
        if(Constantes.click){//si click
        	if(pajaro.getSprite().getBoundingRectangle().contains(iX, (gH - iY))){
        		pajaro.tocado = true;
        	}
        	if(pajaro.tocado){
        		sling.estirar(iX, cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y);
        		return;
        	}
        	if(pajaro.isLanzado())
        		pajaro.comportamiento();
        	//---Movimiento en 'x' de la cámara
        	if(dX!=0) dX=(dX>0)? -10 : 10;
        	if(x+dX/PPM > (Gdx.graphics.getWidth()/2)/PPM && x+dX < (back.getRegionWidth()-Gdx.graphics.getWidth()/2)/PPM && !pajaro.tocado) {
	    		if(iX > (150+sling.getTextura().getWidth())/PPM){//Después de la resortera
	    			cam.position.x += dX/PPM;
					cam.zoom += (dX > 0 && cam.zoom-scrollDx > 0.7)? -scrollDx*PPM : (dX < 0 && cam.zoom+scrollDx <= 1 )? scrollDx*PPM : 0;
				}
        	}
        
        }
        else {//no click
        	if(!pajaro.tocado)
        		return;
        	else{
	    		pajaro.tocado = false;
	    		pajaro.lanzar(vec2.x, vec2.y, sling);
        	}
		}
        
	}
}