package Pack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuPrincipal implements Screen {
	OrthographicCamera cam;
	ShapeRenderer shaper;
	ImageButton button;
	Stage stage;
	AnBiLit game;
	
	public MenuPrincipal(AnBiLit game){
		this.game = game;
	}
	
	@Override
	public void show() {
		shaper = new ShapeRenderer();
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("btnMaderaPlay.png"))));
		button.setVisible(true);
		button.setBounds(0, 0, 100, 100);
		/*	
		Skin skin = new Skin(new TextureAtlas("skin/uiskin.atlas"));
		
		final Button btnNewGame, btnLoadGame, btnSettings, btnQuit;
		btnNewGame = new TextButton("New Game", skin);
		btnLoadGame = new TextButton("Load Game", skin);
		btnSettings = new TextButton("Settings", skin);
		btnQuit = new TextButton("Quit", skin);
		
		final int w = 300, h = 50, sep = 20;
		Table tblLayout = new Table();
		tblLayout.add(btnNewGame).width(w).height(h).space(sep).row();
		tblLayout.add(btnLoadGame).width(w).height(h).space(sep).row();
		tblLayout.add(btnSettings).width(w).height(h).space(sep).row();
		tblLayout.add(btnQuit).width(w).height(h).space(sep).row();
		
		stage = new Stage();
		tblLayout.setFillParent(true);
		stage.addActor(tblLayout);
		Gdx.input.setInputProcessor(stage);*/
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor((255/255f), (255/255f), (0/255f), 1); //RGB
		cam.update();
		
		//COORDENADAS DE APOYO
		shaper.setProjectionMatrix(cam.combined);
		shaper.begin(ShapeType.Line);
		shaper.line(-1000, 0, 1000, 0);
		shaper.line(0, -1000, 0, 1000);
		for (int i = -1000; i <= 1000; i += 50) {
			shaper.line(-10, i, 10, i);
			shaper.line(i, -10, i, 10);
		}
		shaper.end();/*
		if(button.i)
			System.out.println("Hoooola");*/
		button.drawDebug(shaper);
		
		/*
		stage.act();
		stage.draw();
		
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
		game.batch.end();*/
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.S))
			game.setScreen(game.escena);
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
