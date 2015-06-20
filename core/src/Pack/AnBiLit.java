package Pack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnBiLit extends Game {
	SpriteBatch batch;
	MenuPrincipal menu;
	MenuNiveles niveles;
	Escena escena;
    Music theme;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		menu = new MenuPrincipal(this);
		escena = new Escena(this);
		niveles = new MenuNiveles(this);
		theme = Gdx.audio.newMusic(Gdx.files.internal("Audio/theme.mp3"));
		setScreen(escena);
	}
	
	@Override
	public void render() {
        super.render(); //important!
    }
	
	@Override
	public void dispose(){
		batch.dispose();
	}
}
