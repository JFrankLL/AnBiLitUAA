package entidades;

import static utiles.Constantes.PPM;
import sun.font.TrueTypeFont;
import utiles.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

import entidades.pajaros.Pajaro;

public class Sling extends EntityAB{
	
	Texture textureAlt;//segunda parte del sling//frontal (creo)
	private TextureRegion textureLiga;
	
	private int fuerzaElastico;//fuerza de lanzamiento Nota::incremental
	public Vector2 pivote1, pivote2;//donde esta amarrado el elástico: para posteriormente dibujarse
	public float dstMax = 128f/PPM, dstMin = 32f/PPM, dst=1;
	public boolean estiramiento, estirando = false;
	
	Pajaro pajaro;//Pájaro cargado en la resortera
	
	public Sling(World world, String rutaSprite, String rutaSprite2, Pajaro pajaro) {
		super(world, rutaSprite);
		this.pajaro = pajaro;
		textureAlt = new Texture(rutaSprite2);
		textureLiga = new TextureRegion(new Texture(Constantes.Graficas.strNegroPxl));
		pivote1 = new Vector2(-140/PPM, -20/PPM);
		pivote2 = new Vector2(-170/PPM, -17/PPM);
	}

	@Override
	public void render(SpriteBatch sb) {
		//liga derecha//
		sb.draw(textura, (2048*0.07f)/PPM, 64/PPM, textura.getWidth()/PPM, textura.getHeight()/PPM);//sling
		dibujarLigaD(pivote1.x, pivote1.y, pajaro.posision().x-320/PPM, pajaro.posision().y-240/PPM, (32f/PPM)/dst, sb);
		pajaro.render(sb);
		//liga izquierda
		if(pajaro.tocado)
			dibujarLigaI(pivote2.x, pivote2.y, pajaro.posision().x-320/PPM, pajaro.posision().y-240/PPM, (32f/PPM)/dst, sb);
		sb.draw(textureAlt, (2048*0.07f)/PPM, 64/PPM, textureAlt.getWidth()/PPM, textureAlt.getHeight()/PPM);//sling
	}
	
	public boolean estirar(float x, float y, Camera cam) {

		Vector2 posActual = new Vector2(cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).x, cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).y);	
		
		int limX = (x>208/PPM)?0:(x>80 /PPM)?1:0;//Limite rectangular x
		int limY = (y>400/PPM)?0:(y>100/PPM)?1:0;//Limite rectangular y
		
		if(!pajaro.lanzado)
			if(Gdx.input.isTouched()){//Click
				if(!pajaro.tocado)
					pajaro.tocado = (pajaro.getSprite().getBoundingRectangle().contains(posActual.x, posActual.y));
				if(pajaro.tocado){
					estiramiento = posActual.dst(Constantes.vecClickInicial) < dstMax 
							&& posActual.dst(Constantes.vecClickInicial) > dstMin;//limitar a un anillo radio dstMax-dstMin
					if(estiramiento){
						pajaro.mover(x, y);
						//pajaro.mover((limX==1)?x:pajaro.posision().x, (limY==1)?y:pajaro.posision().y);
						estirando = true;
					}
					else
						estirando = false;
				}
				
			}
			else{//NO Click
				if(!pajaro.lanzado && estirando){//no se ha lanzado pero se estaba apuntando
					pajaro.lanzar(Constantes.vecClickInicial.x, Constantes.vecClickInicial.y, this);
					estirando = false;
					//TODO: cambiar a siguiente pájaro
				}
				if(!estiramiento)//fuera de limites de estiramiento
					pajaro.mover(170f/PPM, 210f/PPM);
			}
		return estirando;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		textureAlt.dispose();
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
	
	private void dibujarLigaD(float x1, float y1, float x2, float y2, float grosor, SpriteBatch sb) {
		if(pajaro.lanzado)
			return;
		//puntoB - puntoA
	    float dx = (x2-x1);
	    float dy = (y2-y1);
	    float largoLinea = (float)(Math.sqrt(dx*dx + dy*dy)+(pajaro.sprite.getWidth()/2));//distancia entre puntos
	    float anguloRadianes = (float)Math.atan2(dy, dx);//Ángulo entre puntos
	    dst = largoLinea;
	    
	    sb.draw(textureLiga, 192/PPM, 7, 0, 0, largoLinea, grosor, 1, 1, (float) Math.toDegrees(anguloRadianes));
	    //sb.draw(textura, x1, y1, dist, thickness, 0, 0, rad);//referencia
	}
	
	private void dibujarLigaI(float x1, float y1, float x2, float y2, float grosor, SpriteBatch sb) {
		if(pajaro.lanzado)
			return;
		//puntoB - puntoA
	    float dx = (x2-x1);
	    float dy = (y2-y1);
	    float largoLinea = (float)(Math.sqrt(dx*dx + dy*dy)+pajaro.sprite.getWidth()/2);//distancia entre puntos
	    float anguloRadianes = (float)Math.atan2(dy, dx);//angulo entre puntos
	    sb.draw(textureLiga, 160/PPM, 7, 0, 0, largoLinea, grosor, 1, 1, (float) Math.toDegrees(anguloRadianes));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
