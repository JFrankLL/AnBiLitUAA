package utiles;

import com.badlogic.gdx.math.Vector2;


public class Constantes {
	public static int PPM = 1;//NO debera exceder de 32
	
	public static boolean click = false;
	public static boolean seguirPajaro = false;
	public static Vector2 vecClickInicial;
	
	public static class Graficas{
		
		public static final String strTexRed = "red.png";
		
		public static final String strTexRedG = "redGrande.png";
		public static final String strTexYel = "yellow.png";
		public static final String strTexBlu = "blue.png";//TODO:no Sirve
		public static final String strTexWhi = "white.png";//TODO:no Sirve
		public static final String strTexBla = "black.png";//TODO:no Sirve
		public static final String strTexPig = "pig.png";
		
		public static final String strNegroPxl = "negroPixel.png";
		
		public static class Bloques{//G:grande, M:mediano, C:chico
			public static class Madera{
				public static final String[] G = {"bloques/madera/maderaG_1.png","bloques/madera/maderaG_2.png","bloques/madera/maderaG_3.png","bloques/madera/maderaG_4.png" };
				public static final String[] M = {"bloques/madera/maderaM_1.png","bloques/madera/maderaM_2.png","bloques/madera/maderaM_3.png","bloques/madera/maderaM_4.png" };
				public static final String[] C = {"bloques/madera/maderaC_1.png","bloques/madera/maderaC_2.png","bloques/madera/maderaC_3.png","bloques/madera/maderaC_4.png" };
			}
			public static class Vidrio{
				public static final String[] G = {"bloques/vidrio/vidrioG_1.png","bloques/vidrio/vidrioG_2.png","bloques/vidrio/vidrioG_3.png","bloques/vidrio/vidrioG_4.png" };
				public static final String[] M = {"bloques/vidrio/vidrioM_1.png","bloques/vidrio/vidrioM_2.png","bloques/vidrio/vidrioM_3.png","bloques/vidrio/vidrioM_4.png" };
				public static final String[] C = {"bloques/vidrio/vidrioC_1.png","bloques/vidrio/vidrioC_2.png","bloques/vidrio/vidrioC_3.png","bloques/vidrio/vidrioC_4.png" };
			}
			public static class Piedra{
				public static final String[] G = {"bloques/piedra/piedraG_1.png","bloques/piedra/piedraG_2.png","bloques/piedra/piedraG_3.png","bloques/piedra/piedraG_4.png"};
				public static final String[] M = {"bloques/piedra/piedraM_1.png","bloques/piedra/piedraM_2.png","bloques/piedra/piedraM_3.png","bloques/piedra/piedraM_4.png" };
				public static final String[] C = {"bloques/piedra/piedraC_1.png","bloques/piedra/piedraC_2.png","bloques/piedra/piedraC_3.png","bloques/piedra/piedraC_4.png" };
			}
		}
	}
	
	public static class Configuracion{
		
		public static boolean debugRender = false;//debugrenderer = circulos y cuadros de box2D
		
	}
	
}
