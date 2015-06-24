package Pack;
 
 import static utiles.Constantes.PPM;

import javax.swing.text.rtf.RTFEditorKit;

import utiles.Constantes;
 



import UI.Puntos;

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
import entidades.pajaros.PajaroBlue;
import entidades.pajaros.PajaroRed;
import entidades.pajaros.PajaroRedGrande;
 
 public class Escena implements Screen, ContactListener {
 	AnBiLit game;
 	OrthographicCamera cam;
 	TextureRegion back, ground;
 	
 	World world;
 	Sling sling;
 	Pajaro pajaro;
 	Body bFloor;
 	
 	Array<EntityAB> entidades = new Array<EntityAB>();
 	Array<Body> fixturesPorQuitar = new Array<Body>();
 	
 	static float sumaFuerzas;
 	public static int puntos = 0;
 	
 	Puntos puntaje;
 	
 	Box2DDebugRenderer dr = new Box2DDebugRenderer();
 	
 	public Escena(AnBiLit game){
 		this.game = game;
 	}
 
 	@Override
 	public void show() {
 		world = new World(new Vector2(0, -9.8f), true);
 		back = new TextureRegion(new Texture("background.png"));
 		ground = new TextureRegion(new Texture("ground.png"));
 		puntaje = new Puntos();
 		
 		pajaro = new PajaroRedGrande(world);
 		//Nivel Temporal//------------------------------------------------------------------------------
 		System.out.println("\n\n\n\n");
 		
 		entidades.clear();
 		entidades.add(new CerdoC(world, 470f, 200f));
 		
 		entidades.add(new Bloques.PiedraG(world,430f, 100f, (short)90));
		entidades.add(new Bloques.MaderaG(world,430f, 200f, (short)90));
		entidades.add(new Bloques.VidrioG(world,430f, 300f, (short)90));
		
		entidades.add(new Bloques.PiedraG(world,440f, 100f, (short)90));
		entidades.add(new Bloques.MaderaG(world,440f, 200f, (short)90));
		entidades.add(new Bloques.VidrioG(world,440f, 300f, (short)90));
		
		entidades.add(new Bloques.PiedraG(world,450f, 100f, (short)90));
		entidades.add(new Bloques.MaderaG(world,450f, 200f, (short)90));
		entidades.add(new Bloques.VidrioG(world,450f, 300f, (short)90));
 		//Nivel Temporal//------------------------------------------------------------------------------
 		
 		Constantes.seguirPajaro = false;
 		cam = new OrthographicCamera(Gdx.graphics.getWidth()/PPM, Gdx.graphics.getHeight()/PPM);
 		sling = new Sling("slingshot.png", "slingshot2.png", pajaro);
 		
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
         puntos=0;
         world.setContactListener(this);
 	}
 	@Override
 	public void render(float delta) {
 		//MECANICA DE OPCIONES 
        //---------------------------------------------------------------------------------------------------
 		//TODO: esto debe ser un switch, creo..
 		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
 			game.setScreen(game.menu);
 		else if(Gdx.input.isKeyJustPressed(Input.Keys.F4))
 			Constantes.Configuracion.debugRender=(Constantes.Configuracion.debugRender)?false:true;
 		else if(Gdx.input.isKeyJustPressed(Input.Keys.F2))
 			game.setScreen(this);//TODO
 		else if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS))
 			cam.zoom+=cam.zoom*0.5f;
 		if(Gdx.input.isKeyPressed(Input.Keys.PLUS))
 			cam.zoom-=cam.zoom*0.1f;
 		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
 			cam.position.y-=0.5f;
 		if(Gdx.input.isKeyPressed(Input.Keys.UP))
 			cam.position.y+=0.5f;
 		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
 			cam.position.x-=0.5f;
 		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
 			cam.position.x+=0.5f;
 		//---------------------------------------------------------------------------------------------------
 		world.step(1/60f, 6, 2);
 		//ACTUALIZAR
 		camUpdate();
 		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 		Gdx.gl.glClearColor((245/255f), (255/255f), (255/255f), 1); //RGB
 		puntaje.actualizar(puntos);//TODO ALTIRO CON ESE 10
 		//MECANICA DE JUEGO// 
         //---------------------------------------------------------------------------------------------------
 		click();//click inicial principalmente. para poder lanzar
         mover();//Scroll en el juego
         if(Constantes.click && !(sling.estirando))//si click y no apuntando para lanzar
         	pajaro.comportamiento();
         removerRotos();//quitar entidades 'muertas'
         if(terminoNivel()){
        	//TODO////TODO////TODO////TODO////TODO////TODO////TODO////TODO////TODO////TODO////TODO//
        	 System.out.println("Nivel se acabo!!!!!!!!!!!!!!!!!!! "+"Linea 53 en escena +-");
        	 game.setScreen(game.niveles);
        	//TODO////TODO////TODO////TODO////TODO////TODO////TODO////TODO////TODO////TODO////TODO//
         }
         
         //DIBUJAR//
         //---------------------------------------------------------------------------------------------------
 		game.batch.setProjectionMatrix(cam.combined);
 		game.batch.begin();
 			//fondo
 			game.batch.draw(back, 0, -170f/PPM, back.getRegionWidth()/PPM, back.getRegionHeight()/PPM);
 			//Pájaro
 			if(pajaro.lanzado) 
 				pajaro.render(game.batch);
 			//render el pájaro (a veces), entre el sling y ligas.
 			sling.render(game.batch);
 			for(EntityAB entidad: entidades)//render elementos en el nivel
 				entidad.render(game.batch);
 			//piso/suelo
 			game.batch.draw(ground, 0, -125f/PPM, back.getRegionWidth()/PPM, back.getRegionHeight()/PPM);
 			//puntaje
 			puntaje.render(game.batch, 50/PPM, (Gdx.graphics.getHeight()+2000)/PPM, cam);
 		game.batch.end();
 		if(Constantes.Configuracion.debugRender)
 			dr.render(world, cam.combined);
 		//---------------------------------------------------------------------------------------------------
 		
 	}
 	//METODOS T.EJECUCION
 	//------------------------------------------------------------------------------------------------------------------
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
 		//Creo que faltan mas dispose (bloques y demás)
 	}
 	//MECANICA GAMEPLAY
 	//------------------------------------------------------------------------------------------------------------------
 	private void click() {//Entrada usuario
 		if(!Constantes.click)
 			Constantes.vecClickInicial = new Vector2(cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).x, cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).y);	
 			
 		if(sling.estirar(cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y, cam))
 			return;
 		
 		//System.out.println(Constantes.vecClickInicial.toString());
 		Constantes.click = Gdx.input.isTouched();
 	}	
 	private void mover() {//Scroll de pantalla
 		if(!Gdx.input.isTouched())
 			return;
         float iX = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x,
         	  iY = Gdx.input.getY()/PPM, 
         	  gH = Gdx.graphics.getHeight()/PPM, 
         	  rbX= pajaro.posision().x/PPM, rbY = pajaro.posision().y/PPM, x = cam.position.x, 
         	  dX = Gdx.input.getDeltaX(), scrollDx = 0.000003f*x;
         
     	//---Movimiento en 'x' de la cámara
     	if(dX!=0) dX=(dX>0)? -10 : 10;
     	if(x+dX/PPM > (Gdx.graphics.getWidth()/2)/PPM && x+dX < (back.getRegionWidth()-Gdx.graphics.getWidth()/2)/PPM && !pajaro.tocado) {
     		if(iX > (150+sling.getTextura().getWidth())/PPM){//Después de la resortera
     			Constantes.seguirPajaro = false;
     			cam.position.x += dX/PPM;
 				cam.zoom += (dX > 0 && cam.zoom-scrollDx > 0.7)? -scrollDx*PPM : (dX < 0 && cam.zoom+scrollDx <= 1 )? scrollDx*PPM : 0;
     		}
     	}
 	}
 	private void camUpdate() {//Actualizar/Refrescar Camera
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
 	private void removerRotos(){
 		for(Body b: fixturesPorQuitar){
 			for(EntityAB entidad: entidades){
 				if(entidad.getBody()==b/* && entidad.vida<0*/){
 					world.destroyBody(b);
 					fixturesPorQuitar.removeValue(b, true);
 					entidades.removeValue(entidad, true);
 					break;
 				}
 			}
 		}
 	}
 	private boolean terminoNivel(){
 		for(EntityAB entidad: entidades)
 			if(entidad instanceof CerdoBase)
 				return false;//primer cerdo encontrado.. No termino
 		return true;//no cerdo.. nivel terminado
 	}
 	//CONTACT LISTENER
 	//------------------------------------------------------------------------------------------------------------------
 	public void postSolve(Contact contact, ContactImpulse impulse) {
 		Fixture golpeado = contact.getFixtureA(), golpeador = contact.getFixtureB();
 		//--------------------------NO  BORRAR--------------------------
 		//							[version0]creo que esta es mas "simple"
 		
 		if(golpeado.getBody()!=bFloor && golpeador.getBody()!=bFloor)//el piso no extiende de entity; se arroja excepción
 		try{
 			if(checarDanio(golpeado, golpeador, impulse)){
 				((Bloque)golpeado.getBody().getUserData()).actualizar();
 				if(((Bloque)golpeado.getBody().getUserData()).daniar((EntityAB)golpeador.getBody().getUserData()))
 					fixturesPorQuitar.add(golpeado.getBody());//se agrega si la vida es cero o menor
 			}
 		}catch(Exception e){
 			//e.printStackTrace();
 			//-------------------------cerdos
 			if(golpeador.getBody().getUserData() instanceof CerdoBase){
 				if(((CerdoBase)golpeador.getBody().getUserData()).daniar((EntityAB)golpeador.getBody().getUserData()))
 					fixturesPorQuitar.add(golpeador.getBody());//se agrega si la vida es cero o menor
 			}
 			else {
				System.out.println("algo pasa");
			}
 		}
 		
 		/*//otro metodo que no sirve del todo bien
 		 if(!(golpeado.getBody().getUserData() instanceof EntityAB))
 			return;
 		if(((EntityAB)golpeado.getBody().getUserData()).normalMax < sum(impulse.getNormalImpulses()) ||
 				((EntityAB)golpeado.getBody().getUserData()).tangentMax < sum(impulse.getTangentImpulses()))
 			fixturesPorQuitar.add(golpeado.getBody());
 		if(!(golpeador.getBody().getUserData() instanceof EntityAB))
 			return;
 		if(((EntityAB)golpeador.getBody().getUserData()).normalMax < sum(impulse.getNormalImpulses()) ||
 				((EntityAB)golpeador.getBody().getUserData()).tangentMax < sum(impulse.getTangentImpulses()))
 			fixturesPorQuitar.add(golpeador.getBody());
 		 * */
 	}
 	public void beginContact(Contact contact) {}
 	public void endContact(Contact contact) {}
 	public void preSolve(Contact contact, Manifold oldManifold) {}
 	//COMPLEMENTOS A CONTACT LISTENER
 	//------------------------------------------------------------------------------------------------------------------
 	private boolean checarDanio(Fixture golpeado, Fixture golpeador, ContactImpulse impulse){
 		// impacto
 		if(((EntityAB)golpeado.getBody().getUserData()).normalMax < sum(impulse.getNormalImpulses()))
 			return true;
 		// Fricción
 		if(((EntityAB)golpeado.getBody().getUserData()).tangentMax < sum(impulse.getTangentImpulses()))
 			return true;
 		return false;
 	}	
 	private static float sum(float[] a){//sumatoria -.-
 		sumaFuerzas = 0;
 		for(float f: a)
 			sumaFuerzas+=f;
 		return sumaFuerzas;
 	}
 	//------------------------------------------------------------------------------------------------------------------
 }