package entidades.bloques;

import utiles.Constantes;

import com.badlogic.gdx.physics.box2d.World;

public abstract class Bloques{
	//**********************************[VIDRIO]**********************************
	public static abstract class Vidrio extends Bloque{
		public Vidrio(World world, String[] rutasSprites, float x, float y, short angulo) {
			super(world, rutasSprites, x, y, angulo);
			normalMax += 80f; tangentMax += 1f;
			normalMax /= 4;  tangentMax /= 4;
		}
	}
	public static class VidrioG extends Vidrio {
		public VidrioG(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Vidrio.G, x, y, angulo);
		}
	}
	public static class VidrioM extends Vidrio {
		public VidrioM(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Vidrio.M, x, y, angulo);
		}
	}
	public static class VidrioC extends Vidrio {
		public VidrioC(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Vidrio.C, x, y, angulo);
		}
	}
	//**********************************[MADERA]**********************************
	public static class Madera extends Bloque{
		public Madera(World world, String[] rutasSprites, float x, float y,short angulo) {
			super(world, rutasSprites, x, y, angulo);
			normalMax += 50f; tangentMax += 5f;
			normalMax /= 4;  tangentMax /= 4;
		}
	}
	public static class MaderaG extends Madera {
		public MaderaG(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.G, x, y, angulo);
		}
	}
	public static class MaderaM extends Madera {
		public MaderaM(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.M, x, y, angulo);
		}
	}
	public static class MaderaC extends Madera {
		public MaderaC(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.C, x, y, angulo);
		}
	}
	//**********************************[PIEDRA]**********************************
	public static abstract class Piedra extends Bloque{
		public Piedra(World world, String[] rutasSprites, float x, float y, short angulo) {
			super(world, rutasSprites, x, y, angulo);
			normalMax += 70f; tangentMax += 7;
			normalMax /= 4;  tangentMax /= 4;
		}
	}
	public static class PiedraG extends Piedra {
		public PiedraG(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.G, x, y, angulo);
		}
	}
	public static class PiedraM extends Piedra {
		public PiedraM(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.M, x, y, angulo);
		}
	}
	public static class PiedraC extends Piedra {
		public PiedraC(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.C, x, y, angulo);
		}
	}
	
}
