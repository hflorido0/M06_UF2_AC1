package model;

import utils.Color;
import utils.Numero;

public class Carta {
	
	private int id;
	private Numero numero;
	private Color color;
	private int id_jugador;
	
	public Carta(int id, String numero, String color, int id_jugador) {
		this.id = id;
		this.numero = Numero.valueOf(numero);
		this.color = Color.valueOf(color);
		this.id_jugador = id_jugador;
	}

	public int getId() {
		return id;
	}


	public String getNumero() {
		return numero.toString();
	}


	public String getColor() {
		return color.toString();
	}

	public int getId_jugador() {
		return id_jugador;
	}

	@Override
	public String toString() {
		return "Carta [id=" + id + ", numero=" + numero + ", color=" + color
				+ "]";
	}
	
}
