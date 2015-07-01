package entidades.bloques;

import utiles.Constantes;
import utiles.Contacto;
import Pack.Escena;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.World;

import entidades.pajaros.PajaroAmarillo;
import entidades.pajaros.PajaroBlue;
import entidades.pajaros.PajaroRed;
import entidades.pajaros.PajaroRedGrande;

public abstract class Bloques{
	//**********************************[VIDRIO]**********************************
	public static abstract class Vidrio extends Bloque{
		static Music smash;
		public Vidrio(World world, String[] rutasSprites, float x, float y, short angulo) {
			super(world, rutasSprites, x, y, angulo);
			body.setUserData(new Contacto.Rompible(10f, 2.5f, this));
			smash = Gdx.audio.newMusic(Gdx.files.internal("Audio/glass.wav"));
		}
		@Override
		public boolean daniar(Object daniador) {
			if(!smash.isPlaying())
				smash.play();
			if(daniador instanceof PajaroBlue)
				vida -= Constantes.ultimaFuerza*2;//especialidad +
			else
				vida -= Constantes.ultimaFuerza;
			Escena.puntos+=100-vida;
			super.actualizar();
			return vida<0;
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
	public static abstract class Madera extends Bloque{
		static Music smash;
		public Madera(World world, String[] rutasSprites, float x, float y,short angulo) {
			super(world, rutasSprites, x, y, angulo);
			body.setUserData(new Contacto.Rompible(21f, 5f, this));
			smash = Gdx.audio.newMusic(Gdx.files.internal("Audio/wood.wav"));
		}
		@Override
		public boolean daniar(Object daniador) {
			if(!smash.isPlaying())
				smash.play();
			if(daniador instanceof PajaroAmarillo)
				vida -= Constantes.ultimaFuerza*2;//especialidad +
			else
				vida -= Constantes.ultimaFuerza;
			Escena.puntos+=100-vida;
			super.actualizar();
			return vida<0;
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
		static Music smash;
		public Piedra(World world, String[] rutasSprites, float x, float y, short angulo) {
			super(world, rutasSprites, x, y, angulo);
			body.setUserData(new Contacto.Rompible(31f, 10f, this));
			smash = Gdx.audio.newMusic(Gdx.files.internal("Audio/rock.wav"));
		}
		@Override
		public boolean daniar(Object daniador) {
			if(!smash.isPlaying())
				smash.play();
			if(daniador instanceof PajaroRedGrande || daniador instanceof PajaroRed)
				vida -= Constantes.ultimaFuerza/2;//especialidad -
			else
				vida -= Constantes.ultimaFuerza;
			Escena.puntos+=100-vida;
			super.actualizar();
			return vida<0;
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