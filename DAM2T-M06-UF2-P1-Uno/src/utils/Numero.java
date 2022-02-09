package utils;

import java.util.Random;

public enum Numero {
	CERO,UNO,DOS,TRES,CUATRO,CINCO,SEIS,SIETE,OCHO,NUEVE,CAMBIO,MASDOS,SALTO,CAMBIOCOLOR,MASCUATRO;
	
	public static String getNumeroRandom() {
		Random r = new Random();
		switch (r.nextInt(15)+1) {
			case 1: return CERO.toString();
			case 2: return UNO.toString();
			case 3: return DOS.toString();
			case 4: return TRES.toString();
			case 5: return CUATRO.toString();
			case 6: return CINCO.toString();
			case 7: return SEIS.toString();
			case 8: return SIETE.toString();
			case 9: return OCHO.toString();
			case 10: return NUEVE.toString();
			case 11: return CAMBIO.toString();
			case 12: return MASDOS.toString();
			case 13: return SALTO.toString();
			case 14: return CAMBIOCOLOR.toString();
			default: return MASCUATRO.toString();
		}
	}
}
