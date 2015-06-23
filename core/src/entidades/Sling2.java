package entidades;

import utiles.Constantes;

import com.badlogic.gdx.Gdx;

import entidades.pajaros.Pajaro;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sling2 {
	Pajaro pajaro;
	Sprite sprites[] = new Sprite[2];
	Vector2 pivotes[] = new Vector2[2], P, I;
	boolean lanzado = false;
	float x, y;
	float distMax, fuerza;
	float pX1, pY1, pX2, pY2;
	TextureRegion textureLiga;
	
	public Sling2(){
		textureLiga = new TextureRegion(new Texture(Constantes.Graficas.strNegroPxl));
		for (int i = 0; i < 2; i++){
			sprites[i] = new Sprite(new Texture("slingshot"+i+".png"));
			pivotes[i] = new Vector2();
		}
		P = new Vector2();
		I = new Vector2();
	}
	public void setBounds(float x, float y, Pajaro pajaro){
    	for (int i=0; i<2; i++){
    		pivotes[i].set(150-i*38, 224-i*2);
    		sprites[i].setBounds(x, y, sprites[i].getWidth(), sprites[i].getHeight());
    	}
    	P.set((pivotes[0].x+pivotes[1].x)/2, (pivotes[0].y+pivotes[1].y)/2);
    	pajaro.mover(P.x, P.y);
    	this.pajaro = pajaro;
	}
    private void dibujarLiga(Vector2 pivote, float grosor, SpriteBatch batch) {
	    float largoLinea = (float)(Math.sqrt((Gdx.input.getX())*(Gdx.input.getX()) + (Gdx.input.getY())*(Gdx.input.getY())));
	    batch.draw(textureLiga, pivote.x, pivote.y, 0, 0, largoLinea, grosor, 1, 1, (float)(Math.atan2(I.y, I.x)));
	}
    public void draw(SpriteBatch batch){
    	I.x = Gdx.input.getX();
    	I.y = Gdx.graphics.getHeight()-Gdx.input.getY();
    	sprites[0].draw(batch);
    	dibujarLiga(pivotes[0], 10, batch);
    	pajaro.render(batch);
    	dibujarLiga(pivotes[1], 10, batch);
    	sprites[1].draw(batch);
    	//System.out.println("PIVOTE\nX: " + I.x + "\nY: " + I.y);
    }
    public void estirar() {
    	I.x = Gdx.input.getX();
    	I.y = Gdx.graphics.getHeight()-Gdx.input.getY();
		pajaro.body.setGravityScale(1);
    	pajaro.mover(I.x, I.y);
	}
    public void lanzar(){
		pajaro.body.applyForceToCenter(P.x-I.x, P.y-I.y, true);
		pajaro.body.setGravityScale(1);
		lanzado = true;
		Constantes.seguirPajaro = true;
    }
}
