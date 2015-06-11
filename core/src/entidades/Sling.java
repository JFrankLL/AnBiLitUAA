package entidades;

import static utiles.Constantes.PPM;
import utiles.Constantes;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import entidades.pajaros.Pajaro;

public class Sling extends EntityAB{
	
	Texture textureAlt;//segunda parte del sling//frontal (creo)
	private TextureRegion textureLiga;
	ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	private int fuerzaElastico;//fuerza de lanzamiento Nota::incremental
	public Vector2 pivote1, pivote2;//donde esta amarrado el elastico: para poseriormente dibujarse
	public int dstMax = 150/PPM;//distancia de "estiramiento" maximo de la liga
	private float estiramiento;
	
	Pajaro pajaro;
	
	private Camera cam;
	
	public Sling(World world, String rutaSprite, String rutaSprite2, Camera cam) {
		super(world, rutaSprite);
		this.cam=cam;
		textureAlt = new Texture(rutaSprite2);
		textureLiga = new TextureRegion(new Texture(Constantes.Graficas.strNegroPxl));
		pivote1 = new Vector2(-140/PPM, -20/PPM);
		pivote2 = new Vector2(-170/PPM, -17/PPM);
	}

	@Override
	public void render(SpriteBatch sb) {
		if(pajaro.posision().x > ((2048*0.07f)+32)/PPM && pajaro.isLanzado())
			return;
		
		ShapeRenderer sr = new ShapeRenderer();//ligas
        sr.setColor(Color.BLACK);
        sr.setProjectionMatrix(cam.combined);
		//liga derecha//s
        //drawLine(pivote1.x, pivote1.y, pajaro.posision().x-Gdx.graphics.getWidth()/2/PPM, pajaro.posision().y-Gdx.graphics.getHeight()/2/PPM, 0.2f, sb);
		dibujarLiga(pivote1.x, pivote1.y, pajaro.posision().x-320/PPM, pajaro.posision().y-240/PPM, 6.4f/PPM, sb);
        sb.draw(textura, (2048*0.07f)/PPM, 64/PPM, textura.getWidth()/PPM, textura.getHeight()/PPM);//sling
		pajaro.render(sb);
		//liga izquierda
		//drawLine(pivote2.x, pivote2.y, pajaro.posision().x-Gdx.graphics.getWidth()/2/PPM, pajaro.posision().y-Gdx.graphics.getHeight()/2/PPM, 0.2f, sb);
		dibujarLiga(pivote2.x, pivote2.y, pajaro.posision().x-320/PPM, pajaro.posision().y-240/PPM, 6.4f/PPM, sb);
		sb.draw(textureAlt, (2048*0.07f)/PPM, 64/PPM, textureAlt.getWidth()/PPM, textureAlt.getHeight()/PPM);//sling
	}
	
	public void estirar(float x, float y) {
		if(estiramiento < dstMax){
			if(!pajaro.isLanzado())
			pajaro.mover(x, y)
			;
		}
		//TODO: problema de limites
	}
	
	public void lanzar(){
		pajaro.lanzar(pivote1.x, pivote1.y, this);
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
	
	private void dibujarLiga(float x1, float y1, float x2, float y2, float grosor, SpriteBatch sb) {
		//puntoB - puntoA
	    float dx = (x2-x1);
	    float dy = (y2-y1);
	    float largoLinea = (float)Math.sqrt(dx*dx + dy*dy)+0.5f;//distancia entre puntos
	    float anguloRadianes = (float)Math.atan2(dy, dx);//angulo entre puntos
	    
	    if(x1 > -5)//liga derecha (inicio)
	    	sb.draw(textureLiga, 192/PPM, 7, 0, 0, largoLinea, grosor, 1, 1, (float) Math.toDegrees(anguloRadianes));
	    else//liga derecha (inicio)
	    	sb.draw(textureLiga, 160/PPM, 7, 0, 0, largoLinea, grosor, 1, 1, (float) Math.toDegrees(anguloRadianes));
	    //sb.draw(textura, x1, y1, dist, thickness, 0, 0, rad);
	    estiramiento = largoLinea;
	}
}
