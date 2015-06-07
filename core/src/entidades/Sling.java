package entidades;

import static utiles.Constantes.PPM;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Sling extends EntityAB{
	
	Texture textureAlt;//segunda parte del sling//frontal (creo)
	
	private int fuerzaElastico;//fuerza de lanzamiento Nota::incremental
	public float pivote0, pivote1, pivote2;//donde esta amarrado el elastico: para poseriormente dibujarse
	public int dstMax=130/PPM;//distancia de "estiramiento" maximo de la liga
	
	public Sling(World world, String rutaSprite, String rutaSprite2) {
		super(world, rutaSprite);
		textureAlt = new Texture(rutaSprite2);
	}

	@Override
	public void render(SpriteBatch sb) {
		//Se deben renderizar por separado para
		//simular capas. Texture(entity) y textureAlt
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
}
