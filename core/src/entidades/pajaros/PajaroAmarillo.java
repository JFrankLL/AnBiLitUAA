package entidades.pajaros;

import utiles.Constantes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class PajaroAmarillo extends Pajaro implements ComportamientoPajaro {

	public PajaroAmarillo(World world) {
		super(world, Constantes.Graficas.strTexYel);
	}

	@Override
	public void comportamiento() {
		if(!comportamientoRealizado){
			System.out.println("Comportamiento");
			body.applyForceToCenter(
					new Vector2(body.getLinearVelocity().x * fuerzaLanzamiento * fuerzaLanzamiento,
							body.getLinearVelocity().y * fuerzaLanzamiento * fuerzaLanzamiento), false);
			comportamientoRealizado = true;
		}
		
	}

}
