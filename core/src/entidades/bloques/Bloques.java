package entidades.bloques;

import utiles.Constantes;

import com.badlogic.gdx.physics.box2d.World;

public class Bloques{
	//*************************************************************************************
	public static class MaderaG extends Bloque {
		public MaderaG(World world, char tam, int x, int y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.G, x, y, angulo);
		}
	}
	public static class MaderaM extends Bloque {
		public MaderaM(World world, char tam, int x, int y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.M, x, y, angulo);
		}
	}
	public static class MaderaC extends Bloque {
		public MaderaC(World world, char tam, int x, int y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.C, x, y, angulo);
		}
	}
	//*************************************************************************************
	public static class VidrioG extends Bloque {
		public VidrioG(World world, char tam, int x, int y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.G, x, y, angulo);
		}
	}
	public static class VidrioM extends Bloque {
		public VidrioM(World world, char tam, int x, int y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.M, x, y, angulo);
		}
	}
	public static class VidrioC extends Bloque {
		public VidrioC(World world, char tam, int x, int y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.C, x, y, angulo);
		}
	}
	//*************************************************************************************
	public static class PiedraG extends Bloque {
		public PiedraG(World world, int x, int y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.G, x, y, angulo);
		}
	}
	public static class PiedraM extends Bloque {
		public PiedraM(World world, int x, int y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.M, x, y, angulo);
		}
	}
	public static class PiedraC extends Bloque {
		public PiedraC(World world, int x, int y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.C, x, y, angulo);
		}
	}
	
}
