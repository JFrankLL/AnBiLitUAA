package Pack;

import UI.CircleButton;
import UI.SquareButton;
import utiles.Constantes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuNiveles extends ScreenAdapter{
    int gW=0, gH=0;
	AnBiLit game;
	CircleButton play; 
	SquareButton [][] lvl = new SquareButton[2][3];
	OrthographicCamera cam;
    TextureRegion background;
	
	public MenuNiveles(AnBiLit game) {
		this.game = game;
	}
	
	@Override
	public void show() {
	    cam = new OrthographicCamera();
		game.batch = new SpriteBatch();
		play = new CircleButton("btnMaderaPlay.png");
		for (int i = 0; i < lvl.length; i++)
			for (int j = 0; j < lvl[i].length; j++)
				lvl[i][j] = new SquareButton("Imagenes/Niveles/locked.png");
		background  = new TextureRegion(new Texture("menuBackground.png"));
	}

	@Override
	public void render(float delta) {
		int iX=Gdx.input.getX(), iY=Gdx.input.getY();
		boolean playSelected = play.selected(iX, iY, gH), lvlSelected[][] = new boolean [2][3];
		for (int i = 0; i < lvlSelected.length; i++)
			for (int j = 0; j < lvlSelected[i].length; j++)
				lvlSelected[i][j] = lvl[i][j].selected(iX, iY, gH);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor((0/255f), (0/255f), (0/255f), 1); //RGB
	    cam.update();
	    
	    game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
			game.batch.draw(background, 0, 0, gW, gH);
			play.draw(game.batch);
			for (int i = 0; i < lvl.length; i++)
				for (int j = 0; j < lvl[i].length; j++){
					lvl[i][j].draw(game.batch);
					lvl[i][j].setScale(lvlSelected[i][j]?1.08f:1f);
			}
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
					game.setScreen(game.menu);
			}
			Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("Imagenes/cursor1.png")), 0, 0);
			play.setScale(playSelected?1.08f:1f);
		}
	}

	@Override
	public void resize(int width, int height) {
	    gW = Gdx.graphics.getWidth();
	    gH = Gdx.graphics.getHeight();
		cam.viewportWidth = width;
		cam.viewportHeight = height;
	    cam.position.x = gW/2;
	    cam.position.y = gH/2;
	    play.setBounds(gW/12, gH/12, (gW+gH)/12, (gW+gH)/12);
	    //MODIFICAR
		for (int i=lvl.length-1; i>=0 ; i--)
			for (int j=0; j<lvl[i].length; j++)
				lvl[i][j].setBounds((j*3+2)*gW/12, (i*3+5.4f)*gH/12, (gW+gH)/12, (gW+gH)/12); //1,7 //4, 7
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
}
