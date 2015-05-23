package Pack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnBiLit extends Game {
	SpriteBatch batch;
	MenuPrincipal menu;
	Escena escena;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		menu = new MenuPrincipal(this);
		escena = new Escena(this);
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
