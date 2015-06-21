package entidades.pajaros;

import utiles.Constantes;

import com.badlogic.gdx.physics.box2d.World;

public class PajaroBlue extends Pajaro implements ComportamientoPajaro {

	public PajaroBlue(World world) {
		super(world, Constantes.Graficas.strTexBlu);//aun no estan la texturas buenas
		tipo = "azul";
		danio += 10;
	}

	@Override
	public boolean comportamiento() {
		if(comportamientoRealizado)
			return false;
		
		//TODO: dividir en tres
		
		super.comportamiento();
		return comportamientoRealizado;
	}
	
	@Override
	public boolean isComportamientoRealizado() {
		return comportamientoRealizado = true;
	}
	@Override
	public void actualizar() {
		// TODO Auto-generated method stub
		
	}
}
