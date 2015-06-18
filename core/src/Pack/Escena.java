package Pack;

import static utiles.Constantes.PPM;

import java.util.ArrayList;

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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import entidades.EntityAB;
import entidades.Sling;
import entidades.bloques.Bloque;
import entidades.bloques.Bloques;
import entidades.cerdos.CerdoBase;
import entidades.cerdos.Cerdos.CerdoC;
import entidades.pajaros.Pajaro;
import entidades.pajaros.PajaroAmarillo;

public class Escena implements Screen {
	OrthographicCamera cam;
	Pajaro pajaro;
	Array<Bloque> bloques = new Array<Bloque>();
	ArrayList<entidades.cerdos.CerdoBase> cerdos = new ArrayList<entidades.cerdos.CerdoBase>();
	
	//Array<Body> fixturesToDestroy = new Array<Body>();
	Array<EntityAB> fixturesPorQuitar = new Array<EntityAB>();
	
	Sling sling;
	TextureRegion back, ground;
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
		ground = new TextureRegion(new Texture("ground.png"));
		
		pajaro = new PajaroAmarillo(world);
		//Nivel Temporal//------------------------------------------------------------------------------
		System.out.println("\n\n\n");
		
		cerdos.clear();
		cerdos.add(new CerdoC(world, 300f, 200f));
		
		bloques.clear();
		bloques.add(new Bloques.VidrioG(world,300f, 100f, (short)90));
		bloques.add(new Bloques.PiedraG(world,300f, 200f, (short)90));
		bloques.add(new Bloques.MaderaG(world,400f, 100*5f, (short)90));
		bloques.add(new Bloques.PiedraG(world,450f, 100*5f, (short)90));
		bloques.add(new Bloques.MaderaG(world,500f, 100*5f, (short)90));
		bloques.add(new Bloques.PiedraG(world,1350f, 160*5f, (short)0));
		bloques.add(new Bloques.VidrioG(world,1450f, 160*5f, (short)0));
		//Nivel Temporal//------------------------------------------------------------------------------
		
		Constantes.seguirPajaro = false;
		cam = new OrthographicCamera(Gdx.graphics.getWidth()/PPM, Gdx.graphics.getHeight()/PPM);
		sling = new Sling(world, "slingshot.png", "slingshot2.png", pajaro);
		
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
        
        //Contact listener----------------------------------------------------------------------------
        world.setContactListener(new ContactListener() {
			public void postSolve(Contact contact, ContactImpulse impulse) {
				Fixture golpeado = contact.getFixtureA(), golpeador = contact.getFixtureB();
				try{
					if((golpeador.getBody().getUserData() instanceof Pajaro && golpeado.getBody().getUserData() instanceof Bloque)){
						System.out.println("paj pega bloq");
						pajaro.comportamientoRealizado = true;
						if(checarDanio(golpeado, golpeador, impulse))
							((Bloque)golpeado.getBody().getUserData()).vida -= ((Pajaro)golpeador.getBody().getUserData()).fuerzaLanzamiento;
					}
					else if((golpeador.getBody().getUserData() instanceof CerdoBase && golpeado.getBody().getUserData() instanceof Pajaro)){
						System.out.println("cer pega paj");
						pajaro.comportamientoRealizado = true;
					}
					else if((golpeador.getBody().getUserData() instanceof CerdoBase && golpeado.getBody().getUserData() instanceof Bloque)){
						System.out.println("cer pega bloq");
					}
					else{//cualquier otra cosa
						if(golpeador.getBody().getUserData() instanceof Pajaro)
							pajaro.isComportamientoRealizado();//bloquea el comportamiento según su tipo
						return;
					}
					//solo pasa aquí si los contactos fue entre pájaro-cerdo, pájaro-bloque, cerdo-bloque
					
					//TODO: Checar tipo de material y pájaro para así poder 
					//		dañar adecuadamente.
					
					if(((EntityAB)golpeado.getBody().getUserData()).aguanteGolpe < sum(impulse.getNormalImpulses()))
						fixturesPorQuitar.add((EntityAB)golpeado.getBody().getUserData());
					//checar apachurramiento 
					if(((EntityAB)golpeado.getBody().getUserData()).aguantePression < sum(impulse.getTangentImpulses()))
						fixturesPorQuitar.add((EntityAB)golpeado.getBody().getUserData());
					 
				}catch (Exception e) {
					System.out.println("error");
					e.printStackTrace();
				}
			}
			public void beginContact(Contact contact) {}
			public void endContact(Contact contact) {}
			public void preSolve(Contact contact, Manifold oldManifold) {}
		});
        //--------------------------------------------------------------------------------------------
	}

	@Override
	public void render(float delta) {
		world.step(1/60f, 6, 2);
		//ACTUALIZAR
		camUpdate();
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
			game.batch.draw(back, 0, -170f/PPM, back.getRegionWidth()/PPM, back.getRegionHeight()/PPM);
			if(pajaro.lanzado) 
				pajaro.render(game.batch);
			sling.render(game.batch);
			for(Bloque b: bloques)
				b.render(game.batch);
			for(CerdoBase c: cerdos)
				c.render(game.batch);
			game.batch.draw(ground, 0, -125f/PPM, back.getRegionWidth()/PPM, back.getRegionHeight()/PPM);
		game.batch.end();
		
			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				game.setScreen(game.menu);
				return;
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.F4))
				Constantes.Configuracion.debugRender=(Constantes.Configuracion.debugRender)?false:true;
			else if(Gdx.input.isKeyJustPressed(Input.Keys.F2))
				game.setScreen(game.escena);
		
		if(Constantes.Configuracion.debugRender)
			dr.render(world, cam.combined);
		
		for(EntityAB b: fixturesPorQuitar){
			for(Bloque bl: bloques){
				if(bl.getBody() == b.getBody()){
					/*if(bl.estado>=1){
						bl.estado--;
						break;
					}*/
					//else{
						world.destroyBody(b.getBody());
						fixturesPorQuitar.removeValue(b, true);
						bloques.removeValue(bl, true);
						break;
					//}
				}
			}
		}
		
	}

	private void camUpdate() {
		if(Constantes.seguirPajaro){
			Vector2 pajPos = pajaro.posision();
			if(pajPos.x > Gdx.graphics.getWidth()/2/PPM &&
				pajPos.x < (2048/PPM)-Gdx.graphics.getWidth()/2/PPM)
				cam.position.x = pajPos.x;
			if(pajPos.y > (Gdx.graphics.getHeight())*0.75f/PPM)
				cam.position.y = pajPos.y - (Gdx.graphics.getHeight()+PPM)*0.25f/PPM;
		}
		cam.update();
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width/PPM, height/PPM);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		game.batch.dispose();
		world.dispose();
		pajaro.dispose();
	}
	
	public boolean click() {
		if(!Constantes.click)
			vec2 = new Vector2(cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).x, cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).y);	
			
		if(Gdx.input.isTouched()){
			if(pajaro.getSprite().getBoundingRectangle().contains(vec2.x, vec2.y))
				pajaro.tocado = true;
			Constantes.click = true;
		}
		else if(pajaro.tocado){
			pajaro.lanzar(vec2.x, vec2.y, sling);
			pajaro.tocado = false;
		}
		sling.estirar(cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y);
		
		//System.out.println(vec2.toString());
		return Gdx.input.isTouched();
	}
	
	public void mover(){
        float iX = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x,
        		iY = Gdx.input.getY()/PPM, 
        		gH = Gdx.graphics.getHeight()/PPM, 
        		rbX = pajaro.posision().x/PPM, rbY = pajaro.posision().y/PPM, x = cam.position.x, 
        		dX = Gdx.input.getDeltaX(), scrollDx = 0.000003f*x;
        	
        if(Constantes.click){//si click
        	
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
	}
	//para contact listener//------------------------
	
	private boolean checarDanio(Fixture golpeado, Fixture golpeador, ContactImpulse impulse){
			if(((EntityAB)golpeado.getBody().getUserData()).aguanteGolpe < sum(impulse.getNormalImpulses()))
				//fixturesToDestroy.add((EntityAB)golpeado.getBody().getUserData());
				return true;
			//checar apachurramiento 
			if(((EntityAB)golpeado.getBody().getUserData()).aguantePression < sum(impulse.getTangentImpulses()))
				//fixturesToDestroy.add((EntityAB)golpeado.getBody().getUserData());
				return true;
			return false;
	}
	
	public static float sum(float[] a){
		float s=0;
		for(float f: a)
			s+=f;
		return s;
	}
}