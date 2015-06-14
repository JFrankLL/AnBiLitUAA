package utiles;


public class Constantes {
	public static int PPM = 32;//NO debera exceder de 32
	
	public static boolean click = false;
	public static boolean seguirPajaro = false;
	
	public static class Graficas{
		
		public static final String strTexRed = "red.png";
		
		public static final String strTexRedG = "redGrande.png";//TODO:no Sirve
		public static final String strTexYel = "yellow.png";
		public static final String strTexBlu = "blue.png";//TODO:no Sirve
		public static final String strTexWhi = "white.png";//TODO:no Sirve
		public static final String strTexBla = "black.png";//TODO:no Sirve
		public static final String strTexPig = "pig.png";//TODO:no Sirve
		
		public static final String strNegroPxl = "negroPixel.png";
		
		public static class Bloques{
			
			public static class Madera{
				private static final String G1 = "bloques/madera/maderaG_1.png";
				private static final String G2 = "bloques/madera/maderaG_2.png";
				private static final String G3 = "bloques/madera/maderaG_3.png";
				private static final String G4 = "bloques/madera/maderaG_4.png";
				public static final String[] G = {G1,G2,G3,G4};
				
				private static final String M1 = "bloques/madera/maderaM_1.png";
				private static final String M2 = "bloques/madera/maderaM_2.png";
				private static final String M3 = "bloques/madera/maderaM_3.png";
				private static final String M4 = "bloques/madera/maderaM_4.png";
				public static final String[] M = {M1,M2,M3,M4};
				
				private static final String C1 = "bloques/madera/maderaC_1.png";
				private static final String C2 = "bloques/madera/maderaC_2.png";
				private static final String C3 = "bloques/madera/maderaC_3.png";
				private static final String C4 = "bloques/madera/maderaC_4.png";
				public static final String[] C = {C1,C2,C3,C4};
			}
			
			public static class Vidrio{
				private static final String G1 = "bloques/vidrio/vidrioG_1.png";
				private static final String G2 = "bloques/vidrio/vidrioG_2.png";
				private static final String G3 = "bloques/vidrio/vidrioG_3.png";
				private static final String G4 = "bloques/vidrio/vidrioG_4.png";
				public static final String[] G = {G1,G2,G3,G4};
				
				private static final String M1 = "bloques/vidrio/vidrioM_1.png";
				private static final String M2 = "bloques/vidrio/vidrioM_2.png";
				private static final String M3 = "bloques/vidrio/vidrioM_3.png";
				private static final String M4 = "bloques/vidrio/vidrioM_4.png";
				public static final String[] M = {M1,M2,M3,M4};
				
				private static final String C1 = "bloques/vidrio/vidrioC_1.png";
				private static final String C2 = "bloques/vidrio/vidrioC_2.png";
				private static final String C3 = "bloques/vidrio/vidrioC_3.png";
				private static final String C4 = "bloques/vidrio/vidrioC_4.png";
				public static final String[] C = {C1,C2,C3,C4};
			}
			
			public static class Piedra{
				private static final String G1 = "bloques/piedra/piedraG_1.png";
				private static final String G2 = "bloques/piedra/piedraG_2.png";
				private static final String G3 = "bloques/piedra/piedraG_3.png";
				private static final String G4 = "bloques/piedra/piedraG_4.png";
				public static final String[] G = {G1,G2,G3,G4};
				
				private static final String M1 = "bloques/piedra/piedraM_1.png";
				private static final String M2 = "bloques/piedra/piedraM_2.png";
				private static final String M3 = "bloques/piedra/piedraM_3.png";
				private static final String M4 = "bloques/piedra/piedraM_4.png";
				public static final String[] M = {M1,M2,M3,M4};
				
				private static final String C1 = "bloques/piedra/piedraC_1.png";
				private static final String C2 = "bloques/piedra/piedraC_2.png";
				private static final String C3 = "bloques/piedra/piedraC_3.png";
				private static final String C4 = "bloques/piedra/piedraC_4.png";
				public static final String[] C = {C1,C2,C3,C4};
			}
			
		}
		
	}
	
	public static class Configuracion{
		
		public static boolean debugRender = false;//debugrenderer = circulos y cuadros de box2D
		
	}
	
}
