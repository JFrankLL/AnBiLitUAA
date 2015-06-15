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
    CircleButton play;
	OrthographicCamera cam;
    TextureRegion background;
	
	public MenuPrincipal(AnBiLit game){
		this.game = game;
	}
	
	@Override
	public void show() {
	    cam = new OrthographicCamera();
		game.batch = new SpriteBatch();
		background  = new TextureRegion(new Texture("menuBackground.png"));
		play = new CircleButton("btnMaderaPlay.png", 0, 0);
	}

	@Override
	public void render(float delta) {
		int gW = Gdx.graphics.getWidth(), gH = Gdx.graphics.getHeight();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor((0/255f), (0/255f), (0/255f), 1); //RGB
	    cam.update();
	    
	    game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
			game.batch.draw(background, 0, 0, gW, gH);
			play.skin.draw(game.batch);
		game.batch.end();
		if(Gdx.input.isTouched()){
			if(play.checkIfClicked(Gdx.input.getX(), Gdx.input.getY(), gH)){
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
    Texture texture;

    public SimpleButton(String path, float x, float y) {
    	skin = new Sprite(texture = new Texture(path));
    	skin.setPosition(x, y);
    	skin.setSize(texture.getWidth(), texture.getHeight());
    }
    public void press(String path){
    	skin.setTexture(texture = new Texture(path));
    }
}

class CircleButton extends SimpleButton{
	public CircleButton(String path, float x, float y) {
		super(path, x, y);
	}
	public boolean checkIfClicked (float ix, float iy, float gH) {
    	if(skin.getWidth()/2 > (Math.sqrt(Math.pow((ix-skin.getOriginX()), 2)+Math.pow((gH-iy)-skin.getOriginY(), 2))))
    		return true;
		return false;
    }
}

class SquareButton extends SimpleButton{
	public SquareButton(String path, float x, float y) {
		super(path, x, y);
	}
	public boolean checkIfClicked (float ix, float iy, float gH) {
    	if (ix > skin.getX() && ix < skin.getX() + skin.getWidth())
    		if ((Gdx.graphics.getWidth()-iy) > skin.getY() && (Gdx.graphics.getHeight()-iy) < skin.getY() + skin.getHeight())
            	return true;
		return false;
    }
}
