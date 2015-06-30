package entidades.bloques;

import utiles.Constantes;
import utiles.Contacto;
import Pack.Escena;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

import entidades.pajaros.PajaroAmarillo;
import entidades.pajaros.PajaroBlue;
import entidades.pajaros.PajaroRed;
import entidades.pajaros.PajaroRedGrande;

public abstract class Bloques{
	//**********************************[VIDRIO]**********************************
	public static abstract class Vidrio extends Bloque{
		public Vidrio(World world, String[] rutasSprites, float x, float y, short angulo) {
			super(world, rutasSprites, x, y, angulo);
			normalMax += 16; tangentMax += 2.5f;
			body.setUserData(new Contacto.Rompible(normalMax, tangentMax, this));
			smash = Gdx.audio.newMusic(Gdx.files.internal("Audio/glass.wav"));
		}
		public boolean daniar(entidades.EntityAB daniador) {
			if(!smash.isPlaying())
				smash.play();
			if(daniador instanceof PajaroBlue)
				vida -= daniador.danio*10;//especialidad +
			else
				vida -= daniador.danio;
			Escena.puntos+=25+100-vida;
			return vida<0;
		}
		@Override
		public void daniarme(int danio) {
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
			normalMax += 21f; tangentMax += 5f;
			danio+=5;
			body.setUserData(new Contacto.Rompible(normalMax, tangentMax, this));
			smash = Gdx.audio.newMusic(Gdx.files.internal("Audio/wood.wav"));
		}
		public boolean daniar(entidades.EntityAB daniador) {
			if(!smash.isPlaying())
				smash.play();
			if(daniador instanceof PajaroAmarillo)
				vida -= daniador.danio*2;//especialidad +
			else
				vida -= daniador.danio;
			Escena.puntos+=50+100-vida;
			return vida<0;
		}
		@Override
		public void daniarme(int danio) {
			// TODO Auto-generated method stub
			
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
			normalMax += 31f; tangentMax += 10;
			danio+=15;
			body.setUserData(new Contacto.Rompible(normalMax, tangentMax, this));
			smash = Gdx.audio.newMusic(Gdx.files.internal("Audio/rock.wav"));
		}
		public boolean daniar(entidades.EntityAB daniador) {
			if(!smash.isPlaying())
				smash.play();
			if(daniador instanceof PajaroRed || daniador instanceof PajaroRedGrande)
				vida -= daniador.danio;//especialidad -
			else
				vida -= daniador.danio;
			Escena.puntos+=100+100-vida;
			return vida<0;
		}
		@Override
		public void daniarme(int danio) {
			// TODO Auto-generated method stub
			
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