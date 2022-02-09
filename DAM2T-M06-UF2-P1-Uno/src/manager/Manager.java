package manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dao.Dao;
import model.Carta;
import model.Jugador;
import utils.Color;
import utils.Numero;

public class Manager {
	private static Manager manager;
	private Dao dao;
	private Jugador jugador;
	private ArrayList<Carta> cartas;
	private Scanner s;
	private Carta ultimaJugada;

	private Manager () {
		dao = new Dao();
		s = new Scanner(System.in);
	}
	
	public static Manager getInstance() {
		if (manager == null) {
			manager = new Manager();
		}
		return manager;
	}

	public void init() {
		try {
			dao.connectar();
			if (checkUsuario() && initJuego()) {
				jugarCarta();
			}
			else System.out.println("Usuario o password incorrectos");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				dao.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void jugarCarta() throws SQLException {
		Carta jugada = null;
		do {
			mostrarCartas();
			System.out.println("Seleciona la carta a jugar. -1 para robar.");
			int carta = s.nextInt();
			if (carta == -1) {
				robarCartas(1);
			} else {
				jugada = seleccionaCarta(carta);
				jugada = validarCarta(jugada);
			}
		} while (jugada == null);
		
		dao.jugarCarta(jugada);
		this.cartas.remove(jugada);
		if (this.cartas.size() == 0) finPartida();
	}

	private Carta validarCarta(Carta jugada) {
		if (ultimaJugada != null) {
			if (ultimaJugada.getColor().equalsIgnoreCase(jugada.getColor())) return jugada;
			if (ultimaJugada.getColor().equalsIgnoreCase(Color.NEGRO.name())) return jugada;
			if (jugada.getColor().equalsIgnoreCase(Color.NEGRO.name())) return jugada;
			return null;
		} else {
			return jugada;
		}
	}

	private void finPartida() throws SQLException {
		dao.sumarGanadas(jugador.getId());
		dao.sumarJugadas();
		dao.borrarTodo();
	}

	private Carta seleccionaCarta(int carta) {
		Carta jugada = this.cartas.get(carta);
		if (ultimaJugada == null || jugada.getColor().equals(ultimaJugada.getColor()) || jugada.getNumero().equals(Numero.CAMBIOCOLOR.toString()) || 
				jugada.getNumero().equals(Numero.MASCUATRO.toString())) return jugada;
		return null;
	}

	private void mostrarCartas() {
		System.out.println("\n");
		for (int i = 0; i < cartas.size(); i++) {
			System.out.println(i + " - " + cartas.get(i).toString());
		}
	}

	private boolean checkUsuario() throws SQLException {
		System.out.print("Nombre de usuario: ");
		String usuario = s.next();
		System.out.print("Password: ");
		String pass = s.next();
		
		jugador = dao.getJugadorByUsuPass(usuario, pass);
		if (jugador != null) {
			return true;
		}
		return false;
		
	}
	
	private boolean initJuego() throws SQLException {
		cartas = dao.getManoJugador(jugador.getId());
		if (cartas.size() == 0) robarCartas(7);
		ultimaJugada = dao.getUltimaCartaJugada();
		if (ultimaJugada != null && ultimaJugada.getNumero().equalsIgnoreCase(Numero.MASDOS.toString())) robarCartas(2);
		if (ultimaJugada != null && ultimaJugada.getNumero().equalsIgnoreCase(Numero.MASCUATRO.toString())) robarCartas(4);
		if (ultimaJugada != null && ultimaJugada.getNumero().equalsIgnoreCase(Numero.SALTO.toString()) || ultimaJugada != null && ultimaJugada.getNumero().equalsIgnoreCase(Numero.CAMBIO.toString())) {
			dao.borrarUltimaCartaJugada(ultimaJugada);
			return false;
		}
		return true;
	}
	
	private void robarCartas(int numCartas) throws SQLException {
		for (int i = 0; i < numCartas; i++) {
			int id = dao.getUltimoIdCarta();
			Carta c = new Carta(id, Numero.getNumeroRandom(), Color.getColorRandom(), jugador.getId());
			dao.guardarCarta(c);
			cartas.add(c);
		}
	}


}
