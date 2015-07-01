package UI;

import static utiles.Constantes.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Puntos {
	int tam = 8;
	Numero[] numeros = new Numero[tam];
	public Puntos() {
		for(int i=0; i<numeros.length; i++)
			numeros[i] = new Numero(0);
	}
	
	public void actualizar(int puntos){
		String puntajeString = String.format("%0"+String.valueOf(tam)+"d", puntos);//rellena con ceros
		for(int i=0; i<numeros.length; i++)
			numeros[i].actualizar(Character.getNumericValue(puntajeString.charAt(numeros.length-i-1)));//cada numero
	}
	
	public void render(SpriteBatch sb, int x, int y, Camera cam){
		int gW = Gdx.graphics.getWidth();
		for(int i=0; i<numeros.length; i++){
			Vector3 vecAux = cam.unproject(new Vector3(gW-i*25-35, y-25, 0));
			numeros[i].sprite.setPosition(vecAux.x, vecAux.y);
			numeros[i].sprite.draw(sb);
		}
	}
}

class Numero{
	int num;
	public Sprite sprite;
	
	public Numero(int num){
		actualizar(num);
	}
	
	public void actualizar(int num){//cambia numero
		this.num = num;
		sprite = new Sprite(new Texture("numeros/"+num+".png"));
		sprite.setSize(sprite.getWidth()/PPM/2, sprite.getHeight()/PPM/2);
		sprite.setPosition(100/PPM, 100/PPM);
	}
}