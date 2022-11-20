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
			boolean checked = checkUsuario();
			if (checked && initJuego()) {
				jugarCarta();
			}
			else if (!checked) 
				System.out.println("Usuario o password incorrectos");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				dao.desconectar();
				System.out.println("Fin de jugada...");
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
				if (jugada == null)
					System.out.println("Carta incorrecta, selecciona una nueva carta...");
			}
		} while (jugada == null);
		
		dao.jugarCarta(jugada);
		this.cartas.remove(jugada);
		if (this.cartas.size() == 0) finPartida();
	}

	private void finPartida() throws SQLException {
		dao.sumarGanadas(jugador.getId());
		dao.sumarJugadas();
		dao.borrarTodo();
	}

	private Carta seleccionaCarta(int carta) {
		Carta jugada = this.cartas.get(carta);
		if (ultimaJugada == null || jugada.getColor().equals(ultimaJugada.getColor()) || jugada.getNumero().equals(ultimaJugada.getNumero()) || 
				jugada.getColor().equals(Color.NEGRO.toString()) || jugada.getColor().equals(Color.NEGRO.toString()) ||
				ultimaJugada.getColor().equals(Color.NEGRO.toString())) return jugada;
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
		System.out.println("Ultima carta Jugada: " + (ultimaJugada == null ? "No hay ultima jugada." : ultimaJugada));
		if (ultimaJugada != null && ultimaJugada.getEstado() == 0) {
			if (ultimaJugada != null && ultimaJugada.getNumero().equalsIgnoreCase(Numero.MASDOS.toString())) {
				return cartasEspeciales(2);
			}
			if (ultimaJugada != null && ultimaJugada.getNumero().equalsIgnoreCase(Numero.MASCUATRO.toString())) {
				return cartasEspeciales(4);
			}
			if (ultimaJugada != null && ultimaJugada.getNumero().equalsIgnoreCase(Numero.SALTO.toString()) || 
					ultimaJugada != null && ultimaJugada.getNumero().equalsIgnoreCase(Numero.CAMBIO.toString())) {
				return cartasEspeciales(0);
			}
		}
		return true;
	}
	
	private boolean cartasEspeciales(int robar) throws SQLException {
		robarCartas(robar);
		dao.marcarCartaEstado1(ultimaJugada);
		return false;
	}
	
	private void robarCartas(int numCartas) throws SQLException {
		for (int i = 0; i < numCartas; i++) {
			int id = dao.getUltimoIdCarta();
			String color = Color.getColorRandom();
			String numero = Numero.getNumeroRandom();
			if (color.equals(Color.NEGRO.toString())) {
				while (!numero.equals(Numero.CAMBIOCOLOR.toString()) && !numero.equals(Numero.MASCUATRO.toString())) {
					numero = Numero.getNumeroRandom();
				}
			}
			if (numero.equals(Numero.CAMBIOCOLOR.toString()) || numero.equals(Numero.MASCUATRO.toString())) {
				color = Color.NEGRO.toString();
			}
			Carta c = new Carta(id, numero, color, jugador.getId(), 0);
			dao.guardarCarta(c);
			cartas.add(c);
		}
		if (numCartas > 0) System.out.println("Has robado " + numCartas + " cartas...");
	}


}
