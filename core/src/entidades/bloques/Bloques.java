package entidades.bloques;

import utiles.Constantes;

import com.badlogic.gdx.physics.box2d.World;

public class Bloques{
	//*************************************************************************************
	public static class MaderaG extends Bloque {
		public MaderaG(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.G, x, y, angulo);
			aguanteGolpe += 5f; aguantePression += 2f;
		}
	}
	public static class MaderaM extends Bloque {
		public MaderaM(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.M, x, y, angulo);
			aguanteGolpe += 5f; aguantePression += 2f;
		}
	}
	public static class MaderaC extends Bloque {
		public MaderaC(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.C, x, y, angulo);
			aguanteGolpe += 5f; aguantePression += 2f;
		}
	}
	//*************************************************************************************
	public static class VidrioG extends Bloque {
		public VidrioG(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Vidrio.G, x, y, angulo);
			aguanteGolpe += 2f; aguantePression += 0.3f;
		}
	}
	public static class VidrioM extends Bloque {
		public VidrioM(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Vidrio.M, x, y, angulo);
			aguanteGolpe += 2f; aguantePression += 0.3f;
		}
	}
	public static class VidrioC extends Bloque {
		public VidrioC(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Vidrio.C, x, y, angulo);
			aguanteGolpe += 2f; aguantePression += 0.3f;
		}
	}
	//*************************************************************************************
	public static class PiedraG extends Bloque {
		public PiedraG(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.G, x, y, angulo);
			aguanteGolpe += 7f; aguantePression += 3f;
		}
	}
	public static class PiedraM extends Bloque {
		public PiedraM(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.M, x, y, angulo);
			aguanteGolpe += 7f; aguantePression += 3f;
		}
	}
	public static class PiedraC extends Bloque {
		public PiedraC(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.C, x, y, angulo);
			aguanteGolpe += 7f; aguantePression += 3f;
		}
	}
	
}
