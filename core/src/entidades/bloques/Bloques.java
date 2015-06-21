package entidades.bloques;

import utiles.Constantes;

import com.badlogic.gdx.physics.box2d.World;

public class Bloques{
	//*************************************************************************************
	public static abstract class Madera extends Bloque{
		public Madera(World world, String[] rutasSprites, float x, float y,short angulo) {
			super(world, rutasSprites, x, y, angulo);
		}
	}
	public static class MaderaG extends Madera {
		public MaderaG(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.G, x, y, angulo);
			aguanteGolpe += 0f; aguantePression += 0f;
		}
	}
	public static class MaderaM extends Madera {
		public MaderaM(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.M, x, y, angulo);
			aguanteGolpe += 5f; aguantePression += 2f;
		}
	}
	public static class MaderaC extends Madera {
		public MaderaC(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Madera.C, x, y, angulo);
			aguanteGolpe += 5f; aguantePression += 2f;
		}
	}
	//*************************************************************************************
	public static abstract class Vidrio extends Bloque{
		public Vidrio(World world, String[] rutasSprites, float x, float y, short angulo) {
			super(world, rutasSprites, x, y, angulo);
		}
	}
	public static class VidrioG extends Vidrio {
		public VidrioG(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Vidrio.G, x, y, angulo);
			aguanteGolpe += 2f; aguantePression += 0.3f;
		}
	}
	public static class VidrioM extends Vidrio {
		public VidrioM(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Vidrio.M, x, y, angulo);
			aguanteGolpe += 2f; aguantePression += 0.3f;
		}
	}
	public static class VidrioC extends Vidrio {
		public VidrioC(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Vidrio.C, x, y, angulo);
			aguanteGolpe += 2f; aguantePression += 0.3f;
		}
	}
	//*************************************************************************************
	public static abstract class Piedra extends Bloque{
		public Piedra(World world, String[] rutasSprites, float x, float y, short angulo) {
			super(world, rutasSprites, x, y, angulo);
		}
	}
	public static class PiedraG extends Piedra {
		public PiedraG(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.G, x, y, angulo);
			aguanteGolpe += 0f; aguantePression += 999f;
		}
	}
	public static class PiedraM extends Piedra {
		public PiedraM(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.M, x, y, angulo);
			aguanteGolpe += 7f; aguantePression += 3f;
		}
	}
	public static class PiedraC extends Piedra {
		public PiedraC(World world, float x, float y, short angulo) {
			super(world, Constantes.Graficas.Bloques.Piedra.C, x, y, angulo);
			aguanteGolpe += 7f; aguantePression += 3f;
		}
	}
	
}
