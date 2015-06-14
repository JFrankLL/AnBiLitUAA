package entidades.cerdos;

import com.badlogic.gdx.physics.box2d.World;

import entidades.pajaros.Pajaro;

public class CerdoBase extends Pajaro {
	public CerdoBase(World world, String rutaSprite) {
		super(world, rutaSprite);
		body.setGravityScale(1);
	}
}	