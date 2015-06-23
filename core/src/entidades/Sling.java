package entidades;

import utiles.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

import entidades.pajaros.Pajaro;

public class Sling extends EntityAB{
	
	Texture textureAlt;//segunda parte del sling//frontal (creo)
	private TextureRegion textureLiga;
	
	private int fuerzaElastico;//fuerza de lanzamiento Nota::incremental
	public Vector2 pivote0, pivote1, pivote2;//donde esta amarrado el elástico: para posteriormente dibujarse
	public float dstMax = 128f, dstMin = 32f, dst=1, grosor = (32f)/dst;
	public boolean estiramiento, estirando = false;
	
	Pajaro pajaro;//Pájaro cargado en la resortera
	
	public Sling(String rutaSprite, String rutaSprite2, Pajaro pajaro) {
		super(rutaSprite);
		this.pajaro = pajaro;
		textureAlt = new Texture(rutaSprite2);
		textureLiga = new TextureRegion(new Texture(Constantes.Graficas.strNegroPxl));
		pivote0 = new Vector2(170f, 210f);
		pivote1 = new Vector2(-140, -20);
		pivote2 = new Vector2(-180, -17);
	}
	//RENDER
	//------------------------------------------------------------------------------------------------
	@Override
	public void render(SpriteBatch sb) {
		//parte derecha sling//
		sb.draw(textura, (2048*0.07f), 64, textura.getWidth(), textura.getHeight());//sling
		//liga derecha//
		dibujarLigaD(pivote1.x, pivote1.y, pajaro.posision().x-320, pajaro.posision().y-240, grosor, sb);
		//Pájaro//
		pajaro.render(sb);
		//liga izquierda/
		if(pajaro.tocado)//para que no estorbe
			dibujarLigaI(pivote2.x, pivote2.y, pajaro.posision().x-320, pajaro.posision().y-240, grosor, sb);
		//parte izquierda sling//
		sb.draw(textureAlt, (2048*0.07f), 64, textureAlt.getWidth(), textureAlt.getHeight());//sling
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
	    
	    sb.draw(textureLiga, 192, 7, 0, 0, largoLinea, grosor, 1, 1, (float) Math.toDegrees(anguloRadianes));
	    //sb.draw(textura, x1, y1, dist, thickness, 0, 0, rad);//referencia
	}
	private void dibujarLigaI(float x1, float y1, float x2, float y2, float grosor, SpriteBatch sb) {
		if(pajaro.lanzado)
			return;
		//puntoB - puntoA
	    float dx = (x2-x1);
	    float dy = (y2-y1);
	    float largoLinea = (float)(Math.sqrt(dx*dx + dy*dy)+pajaro.sprite.getWidth()/2);//distancia entre puntos
	    float anguloRadianes = (float)Math.atan2(dy, dx);//Ángulo entre puntos
	    sb.draw(textureLiga, 160, 7, 0, 0, largoLinea, grosor, 1, 1, (float) Math.toDegrees(anguloRadianes));
	}
	//MECANICA
	//------------------------------------------------------------------------------------------------
	public boolean estirar(float x, float y, Camera cam) {

		Vector2 posActual = new Vector2(cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).x, cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)).y);	
		
		/*int limX = (x>208)?0:(x>80 )?1:0;//Limite rectangular x
		int limY = (y>400)?0:(y>100)?1:0;//Limite rectangular y*/
		
		if(!pajaro.lanzado)
			if(Gdx.input.isTouched()){//Click
				if(!pajaro.tocado)
					pajaro.tocado = (pajaro.getSprite().getBoundingRectangle().contains(posActual.x, posActual.y));
				if(pajaro.tocado){
					estiramiento = posActual.dst(pivote0) < dstMax && posActual.dst(pivote0) > dstMin;//limitar a un anillo radio dstMax-dstMin
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
					pajaro.lanzar((float)(Constantes.vecClickInicial.x*(Math.pow(33, 2))), (float)(Constantes.vecClickInicial.y*(Math.pow(33, 2))), this);
					estirando = false;
					pajaro.tocado = false;
					//TODO: cambiar a siguiente pájaro
				}
				else if(posActual.dst(Constantes.vecClickInicial) > dstMin-1)//fuera de limites de estiramiento
					pajaro.mover(pivote0);
			}
		grosor = (dst<dstMin*2)? 15f : 32f/dst;
		return estirando;
	}
	@Override
	public void actualizar() {}
	//UTILES
	//------------------------------------------------------------------------------------------------
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
	
}
