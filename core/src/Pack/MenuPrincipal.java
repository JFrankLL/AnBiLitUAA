package Pack;

import utiles.Constantes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuPrincipal extends ScreenAdapter {
	AnBiLit game;
    SimpleButton play;
	OrthographicCamera cam;
    TextureRegion background;
	
	public MenuPrincipal(AnBiLit game){
		this.game = game;
	}
	
	@Override
	public void show() {
		game.batch = new SpriteBatch();
		play = new SimpleButton("btnMaderaPlay.png", 0, 0);
		background  = new TextureRegion(new Texture("menuBackground.png"));
	    cam = new OrthographicCamera();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor((0/255f), (0/255f), (0/255f), 1); //RGB
	    cam.update();
	    
	    game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
			game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			play.skin.draw(game.batch);
		game.batch.end();
		if(Gdx.input.isTouched()){
			if(play.checkIfClicked(Gdx.input.getX(), Gdx.input.getY())){
				play.press("btnMaderaPlayPress.png");
				Constantes.click = true;
			}
			else{
				play.press("btnMaderaPlay.png");
				Constantes.click = false;
			}
		}
		else
			if(Constantes.click){
				Constantes.click = false;
				game.setScreen(game.escena);
			}
	}
	
	@Override
	public void resize(int width, int height){
		cam.viewportWidth = width;
		cam.viewportHeight = height;
	    cam.position.x = Gdx.graphics.getWidth()/2;
	    cam.position.y = Gdx.graphics.getHeight()/2;
	}
}

class SimpleButton {
    Sprite skin;
    private Texture texture;

    public SimpleButton(String path, float x, float y) {
    	skin = new Sprite(texture = new Texture(path));
    	skin.setPosition(x, y);
    	skin.setSize(texture.getWidth(), texture.getHeight());
    }
    public boolean checkIfClicked (float ix, float iy) {
        	if (ix > skin.getX() && ix < skin.getX() + skin.getWidth())
        		if ((Gdx.graphics.getWidth()-iy) > skin.getY() && (Gdx.graphics.getHeight()-iy) < skin.getY() + skin.getHeight())
                	return true;
		return false;
    }
    public void press(String path){
    	skin.setTexture(texture = new Texture(path));
    }
}
