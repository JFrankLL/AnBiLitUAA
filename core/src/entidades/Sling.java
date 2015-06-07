package entidades;

import static utiles.Constantes.PPM;
import utiles.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;

import entidades.pajaros.Pajaro;

public class Sling extends EntityAB{
	
	Texture textureAlt;//segunda parte del sling//frontal (creo)
	private TextureRegion textureLiga;
	ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	private int fuerzaElastico;//fuerza de lanzamiento Nota::incremental
	public Vector2 pivote0, pivote1, pivote2;//donde esta amarrado el elastico: para poseriormente dibujarse
	public int dstMax=1150/PPM;//distancia de "estiramiento" maximo de la liga
	
	Pajaro pajaro;
	
	private Camera cam;
	
	public Sling(World world, String rutaSprite, String rutaSprite2, Camera cam) {
		super(world, rutaSprite);
		this.cam=cam;
		textureAlt = new Texture(rutaSprite2);
		textureLiga = new TextureRegion(new Texture(Constantes.Graficas.strNegroPxl));
		pivote0 = new Vector2(-150, 0);
		pivote1 = new Vector2(0, 0);
		pivote2 = new Vector2(0, 0);
	}

	@Override
	public void render(SpriteBatch sb) {
		if(pajaro.isLanzado())
			return;
		
		ShapeRenderer sr = new ShapeRenderer();//ligas
        sr.setColor(Color.BLACK);
        sr.setProjectionMatrix(cam.combined);
		
		//liga derecha//s
        drawLine(-140/PPM, -20/PPM, pajaro.posision().x-Gdx.graphics.getWidth()/2/PPM, pajaro.posision().y-Gdx.graphics.getHeight()/2/PPM, 0.2f, sb);
		sb.draw(textura, (2048*0.07f)/PPM, 64/PPM, textura.getWidth()/PPM, textura.getHeight()/PPM);
		pajaro.render(sb);
		//liga izquierda
		drawLine(-170/PPM, -17/PPM, pajaro.posision().x-Gdx.graphics.getWidth()/2/PPM, pajaro.posision().y-Gdx.graphics.getHeight()/2/PPM, 0.2f, sb);
		sb.draw(textureAlt, (2048*0.07f)/PPM, 64/PPM, textureAlt.getWidth()/PPM, textureAlt.getHeight()/PPM);
		
	}
	
	public void lanzar(){
		//TODO NO FUNCA
		Vector2 vec2 = new Vector2(cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).x, cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).y);	
		pajaro.lanzar(pivote0.x, pivote0.y, this);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		textureAlt.dispose();
	}
	
	public void setPajaro(Pajaro pajaro) {
		this.pajaro = pajaro;
	}
	
	public Texture getTextura() {
		return textura;
	}
	
	public Texture getTexturaAlt() {
		return textureAlt;
	}
	
	public int getFuerzaElastico() {
		//TODO rango de estiramiento devuelve diferente valor
		// a mayor distancia mayor fuerza;
		return fuerzaElastico;
	}
	
	private void drawLine(float x1, float y1, float x2, float y2, float thickness, SpriteBatch sb) {
	    float dx = (x2-x1);
	    float dy = (y2-y1);
	    float dist = (float)Math.sqrt(dx*dx + dy*dy)+0.5f;
	    float rad = (float)Math.atan2(dy, dx);
	    
	    if(x1 > -5)
	    	sb.draw(textureLiga, 6, 7, 0, 0, dist, thickness, 1, 1, (float) Math.toDegrees(rad));
	    else
	    	sb.draw(textureLiga, 5, 7, 0, 0, dist, thickness, 1, 1, (float) Math.toDegrees(rad));
	    //sb.draw(textura, x1, y1, dist, thickness, 0, 0, rad);
	}
}
