package entidades.pajaros;

import utiles.Constantes;

import com.badlogic.gdx.physics.box2d.World;

public final class PajaroAmarillo extends Pajaro implements ComportamientoPajaro {

	public PajaroAmarillo(World world) {
		super(world, Constantes.Graficas.strTexYel);
		tipo = "amarillo";
	}

	@Override
	public boolean comportamiento() {
		if(comportamientoRealizado)
			return false;
		
		danio+=20;
		body.applyForceToCenter(body.getLinearVelocity().setLength(1000), true);
		
		//body.applyForceToCenter( new Vector2((float) Math.pow(fuerzaLanzamiento, 3), (float) Math.pow(fuerzaLanzamiento, 3)), false);
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
	@Override
	public void daniarme(int danio) {
		// TODO Auto-generated method stub
		
	}

}
