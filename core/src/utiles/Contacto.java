package utiles;

import Pack.Escena;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import entidades.pajaros.Pajaro;

public class Contacto implements ContactListener {
	
	public void postSolve(Contact contact, ContactImpulse impulse) {
 		Fixture fixA = contact.getFixtureA(), fixB = contact.getFixtureB();
 		
 		if(checarDanio(fixA, impulse))
 			Escena.fixturesPorQuitar.add(fixA.getBody());
 		if(checarDanio(fixB, impulse))
 			Escena.fixturesPorQuitar.add(fixB.getBody());
 		
 	}
 	public void beginContact(Contact contact) {}
 	public void endContact(Contact contact) {}
 	public void preSolve(Contact contact, Manifold oldManifold) {}
 	
 	//COMPLEMENTOS A CONTACT LISTENER
 	//------------------------------------------------------------------------------------------------------------------
 	private boolean checarDanio(Fixture fix, ContactImpulse impulse){
 		if(!(fix.getBody().getUserData() instanceof Rompible))
 			 return false;
 		
 		//bloquear comportamiento y trayectoria de pajaro puesto que ya ha impactado
 		if(((Rompible)fix.getBody().getUserData()).thisObject instanceof Pajaro)
 			((Pajaro)((Rompible)fix.getBody().getUserData()).thisObject).bloqueo();
 		
 		//checar magnitud de impacto/presion
 		if(((Rompible)fix.getBody().getUserData()).normalMax < sum(impulse.getNormalImpulses()) ||
 				((Rompible)fix.getBody().getUserData()).tangentMax < sum(impulse.getTangentImpulses()))
 			return true;
 		return false;
 	}	
 	private static float sum(float[] a){//sumatoria -.-
 		int sumaFuerzas = 0;
 		for(float f: a)
 			sumaFuerzas+=f;
 		return sumaFuerzas;
	 }
 	
 	////////////////////////////////////////////////////////////////
 	//  ----------------------[clase]---------------------------  //
 	////////////////////////////////////////////////////////////////
 	public static class Rompible{
 		float normalMax, tangentMax;
 		public Object thisObject;
 		
 		public Rompible(float normalMax, float tangentMax, Object thisObject) {
 			this.normalMax = normalMax;
 			this.tangentMax = tangentMax;
 			this.thisObject = thisObject;
 		}
 	}
 	
}
