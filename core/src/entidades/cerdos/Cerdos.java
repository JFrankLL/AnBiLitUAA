package entidades.cerdos;

import static utiles.Constantes.PPM;
import utiles.Constantes;

import com.badlogic.gdx.physics.box2d.World;

public abstract class Cerdos{

	public static class CerdoC extends CerdoBase{
		public CerdoC(World world, int x, int y) {
			super(world, Constantes.Graficas.strTexPig);
			mover(x/PPM, y/PPM);
		}

	}
}	
