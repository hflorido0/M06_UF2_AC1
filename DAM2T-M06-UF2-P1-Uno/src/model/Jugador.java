package model;

public class Jugador {

	private int id;
	private String nombre;
	private int partidas;
	private int ganadas;
	
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.partidas = 0;
		this.ganadas = 0;
	}

	public Jugador(int id, String nombre, int partidas, int ganadas) {
		this.id = id;
		this.nombre = nombre;
		this.partidas = partidas;
		this.ganadas = ganadas;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPartidas() {
		return partidas;
	}

	public int getGanadas() {
		return ganadas;
	}

	@Override
	public String toString() {
		return "Jugador [id=" + id + ", nombre=" + nombre + "]";
	}
	
}
