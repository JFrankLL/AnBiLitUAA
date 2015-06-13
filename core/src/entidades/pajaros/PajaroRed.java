package entidades.pajaros;

import utiles.Constantes;

import com.badlogic.gdx.physics.box2d.World;

public class PajaroRed extends Pajaro implements ComportamientoPajaro {

	public PajaroRed(World world) {
		super(world, Constantes.Graficas.strTexRed);
	}
	
	@Override
	public boolean comportamiento() {
		if(comportamientoRealizado)
			return false;
		super.comportamiento();
		return comportamientoRealizado;
	}

}
