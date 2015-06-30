package Pack.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import Pack.AnBiLit;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	    
		config.width = 1280;
		config.height = 720;

	    // fullscreen
		//config.fullscreen = true;
	    // vSync
		config.vSyncEnabled = true;
		config.allowSoftwareMode = true;
		config.useGL30 = true;
		config.title = "AnBiLitUAA";
		
		new LwjglApplication(new AnBiLit(), config);
	}
}

//Cambio a GitHub for desktop bi