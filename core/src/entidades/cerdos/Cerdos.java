package entidades.cerdos;

import static utiles.Constantes.PPM;
import utiles.Constantes;
import Pack.Escena;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import entidades.EntityAB;

public abstract class Cerdos{

	public static class CerdoC extends CerdoBase{
		public CerdoC(World world, float x, float y) {
			super(world, Constantes.Graficas.strTexPig);
			body.setTransform(new Vector2(x/PPM, y/PPM), body.getAngle());
		}
		@Override
		public void render(SpriteBatch sb) {
			super.render(sb);
		}
		public boolean daniar(EntityAB daniador) {
			vida -= daniador.danio;
		Escena.puntos+=75+100-vida;
		return vida<0;
	}
	}
}