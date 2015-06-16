package entidades;

import utiles.Constantes;
import entidades.pajaros.Pajaro;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sling2 {
	Pajaro pajaro;
	Sprite s1, s2;
	Texture t1, t2;
	Vector2 p1, p2, p;
	float distMax, fuerza;
	float pX1, pY1, pX2, pY2;
	TextureRegion textureLiga;
	
	public Sling2(float x, float y, Pajaro pajaro){
		textureLiga = new TextureRegion(new Texture(Constantes.Graficas.strNegroPxl));
		s1 = new Sprite(t1 = new Texture("slingshot.png"));
		setBounds(s1, x, y, t1.getWidth(), t1.getHeight());
		s2 = new Sprite(t2 = new Texture("slingshot2.png"));
		setBounds(s2, x, y, t2.getWidth(), t2.getHeight());
		this.pajaro = pajaro;
		p1 = new Vector2();
		p2 = new Vector2();
		p = new Vector2();
	}
	public void setBounds(float x, float y, float width, float height){
    	p1.set((46*t1.getWidth())/64, (108*t1.getHeight())/256);
    	p2.set((8*t2.getWidth())/64, (110*t2.getHeight())/256);
    	p.set((p2.x-p1.x)/2, (p2.y-p1.y)/2);
    	pajaro.mover(p.x, p.y);
	}
    public void setBounds(Sprite s, float x, float y, float width, float height){
		setBounds(x, y, width, height);
    	s.setPosition(x, y);
    	s.setSize(width, height);
    }
    public void draw(SpriteBatch batch){
    	s1.draw(batch);
    	dibujarLiga(p1, 5, batch);
    	dibujarLiga(p2, 5, batch);
    	s2.draw(batch);
    }
    private void dibujarLiga(Vector2 pivote, float grosor, SpriteBatch sb) {
	    float largoLinea = (float)Math.sqrt(p.x*p.x + p.y*p.y)+(pajaro.sprite.getWidth()/2);
	    float anguloRadianes = (float)Math.atan2(p.y, p.x);
	    sb.draw(textureLiga, pivote.x, pivote.y, p.x, p.y, largoLinea, grosor, 1, 1, (float) Math.toDegrees(anguloRadianes));
	}
}
