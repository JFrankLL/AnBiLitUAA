package Pack;

import UI.CircleButton;
import utiles.Constantes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuPrincipal extends ScreenAdapter {
    int gW=0, gH=0;
	AnBiLit game;
	OrthographicCamera cam;
	CircleButton play, conf;
    TextureRegion background, logo;
	
	public MenuPrincipal(AnBiLit game){
		this.game = game;
	}
	
	@Override
	public void show() {
	    cam = new OrthographicCamera();
		game.batch = new SpriteBatch();
		conf = new CircleButton("btnMute.png");
		play = new CircleButton("btnMaderaPlay.png");
		logo  = new TextureRegion(new Texture("Imagenes/logo.png"));
		background  = new TextureRegion(new Texture("menuBackground.png"));
		Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("Imagenes/cursor1.png")), 0, 0);
	}

	@Override
	public void render(float delta) {
		int iX=Gdx.input.getX(), iY=Gdx.graphics.getHeight()-Gdx.input.getY();
		boolean playSelected = play.selected(iX, iY), confSelected = conf.selected(iX, iY);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor((0/255f), (0/255f), (0/255f), 1); //RGB
	    cam.update();
	    
	    game.theme.play();
	    game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
			game.batch.draw(background, 0, 0, gW, gH);
			game.batch.draw(logo, gW/50f, gH/2, gW, gH/2.5f);
			play.draw(game.batch);
			conf.draw(game.batch);
		game.batch.end();
		if(Gdx.input.isTouched()){
			Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("Imagenes/cursor2.png")), 0, 0);
			play.press(playSelected?"btnMaderaPlayPress.png":"btnMaderaPlay.png");
			Constantes.click = true;
		}
		else{
			if(Constantes.click){
				Constantes.click = false;
				if(playSelected)
					game.setScreen(game.niveles);
				if(confSelected){
					conf.pressed = !conf.pressed;
					game.theme.setVolume((conf.pressed)?0:1);
					conf.press((conf.pressed)?"btnMutePress.png":"btnMute.png");
				}
			}
			Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("Imagenes/cursor1.png")), 0, 0);
			play.setScale(playSelected?1.08f:1f);
			conf.setScale(confSelected?1.08f:1f);
		}
	}
	
	@Override
	public void resize(int width, int height){
	    gW = Gdx.graphics.getWidth();
	    gH = Gdx.graphics.getHeight();
		cam.viewportWidth = gW;
		cam.viewportHeight = gH;
	    cam.position.x = gW/2;
	    cam.position.y = gH/2;
	    play.setBounds(gW/12, gH/12, (gW+gH)/12, (gW+gH)/12);
	    conf.setBounds(gW-(gW+gH)/12-gW/12, gH/12, (gW+gH)/12, (gW+gH)/12);
	}
	public void hide(){
		Constantes.click = false;
	}
	@Override
	public void dispose(){
		game.theme.dispose();
	}
}