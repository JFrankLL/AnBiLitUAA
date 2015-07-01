package entidades.cerdos;

import static utiles.Constantes.PPM;
import utiles.Constantes;
import utiles.Contacto;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Cerdos{

	public static class CerdoC extends CerdoBase{
		public CerdoC(World world, float x, float y) {
			super(world, Constantes.Graficas.strTexPig);
			body.setTransform(new Vector2(x/PPM, y/PPM), body.getAngle());
			body.setUserData(new Contacto.Rompible(5f, 500f, this));//31 5
		}
		@Override
		public void render(SpriteBatch sb) {
			super.render(sb);
		}
		@Override
		public boolean daniar(Object daniador) {
			return super.daniar(daniador);
		}
	}
}