package utils;

import java.util.Random;

public enum Color {
	ROJO, AMARILLO, VERDE, AZUL, NEGRO;
	
	public static String getColorRandom() {
		Random r = new Random();
		switch (r.nextInt(5)+1) {
			case 1: return ROJO.toString();
			case 2: return AMARILLO.toString();
			case 3: return VERDE.toString();
			case 4: return AZUL.toString();
			default: return NEGRO.toString();
		}
	}
}
