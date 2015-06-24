package UI;

import utiles.Constantes;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Puntos {
	
	Numero[] numeros;
	
	public Puntos() {
		numeros = new Numero[5];
		for(int i=0; i<numeros.length; i++)
			numeros[i] = new Numero(0);
	}
	
	public void actualizar(int puntos){
		String puntajeString = String.format("%05d", puntos);//rellena con ceros
		for(int i=0; i<numeros.length; i++)
			numeros[i].actualizar(Character.getNumericValue(puntajeString.charAt(i)));//cada numero
	}
	
	public void render(SpriteBatch sb, int x, int y, Camera cam){
		for(int i=0; i<numeros.length; i++){
			Vector3 vecAux = cam.unproject(new Vector3(i*50, y, 0));
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
		String[] numerosStrings = {"numeros/0.png","numeros/1.png","numeros/2.png","numeros/3.png",
				"numeros/4.png","numeros/5.png","numeros/6.png","numeros/7.png","numeros/8.png","numeros/9.png"};
		this.num = num;
		sprite = new Sprite(new Texture("numeros/"+String.valueOf(num)+".png"));
		sprite.setSize(sprite.getWidth()/Constantes.PPM, sprite.getHeight()/Constantes.PPM);
		sprite.setPosition(100/Constantes.PPM, 100/Constantes.PPM);
	}
}