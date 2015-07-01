package utiles;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import entidades.EntityAB;
import entidades.pajaros.Pajaro;

public class Contacto implements ContactListener {
	
	public void postSolve(Contact contact, ContactImpulse impulse) {
 		Fixture fixA = contact.getFixtureA(), fixB = contact.getFixtureB();
 		if(checarDanio(fixA, impulse))
			if(fixA.getBody().getUserData() instanceof Rompible)
				if(((Rompible)fixA.getBody().getUserData()).thisObject instanceof EntityAB)
					((EntityAB)((Rompible)fixA.getBody().getUserData()).thisObject).daniar(fixB.getBody().getUserData());
 		if(checarDanio(fixB, impulse))
			if(fixB.getBody().getUserData() instanceof Rompible)
				if(((Rompible)fixB.getBody().getUserData()).thisObject instanceof EntityAB)
					((EntityAB)((Rompible)fixB.getBody().getUserData()).thisObject).daniar(fixA.getBody().getUserData());
		
		//bloquear pajaro
		if(fixA.getBody().getUserData()!=null && fixA.getBody().getUserData() instanceof Rompible)
			if(((Rompible)fixA.getBody().getUserData()).thisObject instanceof Pajaro)
				((Pajaro)((Rompible)fixA.getBody().getUserData()).thisObject).bloqueo();
		if(fixA.getBody().getUserData()!=null && fixA.getBody().getUserData() instanceof Rompible)
			if(((Rompible)fixB.getBody().getUserData()).thisObject instanceof Pajaro)
				((Pajaro)((Rompible)fixB.getBody().getUserData()).thisObject).bloqueo();
		
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
 		Constantes.ultimaFuerza = 0;
 		for(float f: a)
 			Constantes.ultimaFuerza+=f;
 		return Constantes.ultimaFuerza;
	 }
 	
 	////////////////////////////////////////////////////////////////
 	//  ----------------------[clase]---------------------------  //
 	////////////////////////////////////////////////////////////////
 	public static class Rompible{
 		float normalMax, tangentMax;
 		public EntityAB thisObject;
 		
 		public Rompible(float normalMax, float tangentMax, EntityAB thisObject) {
 			this.normalMax = normalMax;
 			this.tangentMax = tangentMax;
 			this.thisObject = thisObject;
 		}
 	}
 	
}
