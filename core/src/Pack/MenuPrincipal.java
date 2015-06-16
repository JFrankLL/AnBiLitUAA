package Pack;

import UI.CircleButton;
import utiles.Constantes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuPrincipal extends ScreenAdapter {
    Music theme;
	AnBiLit game;
	CircleButton play;
	OrthographicCamera cam;
    TextureRegion background, logo;
	
	public MenuPrincipal(AnBiLit game){
		this.game = game;
	}
	
	@Override
	public void show() {
	    cam = new OrthographicCamera();
		game.batch = new SpriteBatch();
		play = new CircleButton("btnMaderaPlay.png", 0, 0);
		logo  = new TextureRegion(new Texture("Imagenes/logo.png"));
		theme = Gdx.audio.newMusic(Gdx.files.internal("Audio/theme.mp3"));
		background  = new TextureRegion(new Texture("menuBackground.png"));
		Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("Imagenes/cursor1.png")), 0, 0);
	}

	@Override
	public void render(float delta) {
		int gW=Gdx.graphics.getWidth(), gH=Gdx.graphics.getHeight(), iX=Gdx.input.getX(), iY=Gdx.input.getY();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor((0/255f), (0/255f), (0/255f), 1); //RGB
	    cam.update();
	    
	    theme.play();
	    game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
			game.batch.draw(background, 0, 0, gW, gH);
			game.batch.draw(logo, gW/3.5f, gH/2, gW/2, gH/3);
			play.skin.draw(game.batch);
		game.batch.end();
		if(Gdx.input.isTouched()){
			Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("Imagenes/cursor2.png")), 0, 0);
			if(play.selected(iX, iY, gH)){
				play.press("btnMaderaPlayPress.png");
				Constantes.click = true;
			}
			else{
				play.press("btnMaderaPlay.png");
				Constantes.click = false;
			}
		}
		else{
			Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("Imagenes/cursor1.png")), 0, 0);
			if(Constantes.click)
				game.setScreen(game.escena);
			play.skin.setScale((play.selected(iX, iY, gH))?1.08f:1f);
		}
	}
	
	@Override
	public void resize(int width, int height){
		cam.viewportWidth = width;
		cam.viewportHeight = height;
	    cam.position.x = Gdx.graphics.getWidth()/2;
	    cam.position.y = Gdx.graphics.getHeight()/2;
	}
	public void hide(){
		theme.pause();
		Constantes.click = false;
	}
	@Override
	public void dispose(){
		theme.dispose();
	}
}


