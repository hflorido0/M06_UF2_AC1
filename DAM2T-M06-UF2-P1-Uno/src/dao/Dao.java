package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Carta;
import model.Jugador;
import utils.Constants;

public class Dao {
	
	private Connection conexion;	


	public void connectar() throws SQLException {
		String url = Constants.CONNECTION;
        String user = Constants.USER_CONNECTION;
        String pass = Constants.PASS_CONNECTION;
        conexion = DriverManager.getConnection(url, user, pass);
	}

	public void desconectar() throws SQLException {
		if (conexion != null) {
            conexion.close();
        }
	}	
	
	public int getUltimoIdCarta() throws SQLException {
		int carta = 0;	
		try (Statement ps = conexion.createStatement()) { 
			System.out.println(ps.toString());
			try (ResultSet rs = ps.executeQuery(Constants.GET_CARTA_LAST_ID)) {
				if (rs.next()) {
					carta = rs.getInt(1);
				}
			}
		}
		return carta;
		
	}
	
	public Carta getUltimaCartaJugada() throws SQLException {
		Carta carta = null;	
		PreparedStatement ps = conexion.prepareStatement(Constants.GET_ULTIMA_CARTA);
		System.out.println(ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				if (rs.getInt(2) != 0) {
					carta = new Carta(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5));
				}
			}
		}
		return carta;
		
	}
	
	public Jugador getJugadorByUsuPass(String usuario, String pass) throws SQLException {
		Jugador jugador = null;	
    	try (PreparedStatement ps = conexion.prepareStatement(Constants.GET_JUGADOR_POR_USU_PASS)) { 
    		ps.setString(1,usuario);
    	  	ps.setString(2,pass);
    	  	System.out.println(ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		jugador = new Jugador(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
            	}
            }
        }
    	return jugador;
	}
	
	public ArrayList<Carta> getManoJugador(int id) throws SQLException {
    	ArrayList<Carta> cartas = new ArrayList<>();
    	
    	try (PreparedStatement ps = conexion.prepareStatement(Constants.GET_MANO_JUGADOR_POR_ID)) { 
    		ps.setInt(1, id);
    		System.out.println(ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                	cartas.add(new Carta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
                }
            }
        }
    	return cartas;	
	}
	
	public void jugarCarta(Carta carta) throws SQLException {
		try (PreparedStatement ps =  conexion.prepareStatement(Constants.JUGAR_CARTA)) { 
			ps.setInt(1, carta.getId());
			System.out.println(ps.toString());
			ps.executeUpdate();
		}
	}
	
	public void guardarCarta(Carta carta) throws SQLException {
		try (PreparedStatement ps =  conexion.prepareStatement(Constants.GUARDAR_MANO)) { 
			ps.setInt(1, carta.getId_jugador());
			ps.setString(2, carta.getNumero());
			ps.setString(3, carta.getColor());
    		System.out.println(ps.toString());
			ps.executeUpdate();
		}
	}

	public void updateJugador(Jugador jugador) throws SQLException {
		try (PreparedStatement ps =  conexion.prepareStatement(Constants.UPDATE_JUGADOR)) { 
			ps.setInt(1, jugador.getPartidas());
			ps.setInt(2, jugador.getGanadas());
			ps.setInt(3, jugador.getId());
    		System.out.println(ps.toString());
            ps.execute();
        }
	}

	public void borrarUltimaCartaJugada(Carta carta) throws SQLException {
		try (PreparedStatement ps =  conexion.prepareStatement(Constants.BORRAR_ULTIMA_CARTA_JUGADA)) { 
			System.out.println(ps.toString());
			ps.execute();
		}
		
		try (PreparedStatement ps =  conexion.prepareStatement(Constants.BORRAR_ULTIMA_CARTA_JUGADA_TABLA_CARTA)) { 
			ps.setInt(1, carta.getId());
    		System.out.println(ps.toString());
            ps.execute();
        }
	}

	public void borrarTodo() throws SQLException {
		try (PreparedStatement ps =  conexion.prepareStatement(Constants.BORRAR_TABLA_CARTA)) { 
			System.out.println(ps.toString());
			ps.execute();
		}
		
		try (PreparedStatement ps =  conexion.prepareStatement(Constants.BORRAR_TABLA_PARTIDA)) {
    		System.out.println(ps.toString());
            ps.execute();
        }
	}

	public void sumarGanadas(int id) throws SQLException {
		try (PreparedStatement ps =  conexion.prepareStatement(Constants.SUMAR_GANADAS)) { 
			ps.setInt(1, id);
			System.out.println(ps.toString());
			ps.execute();
		}
	}

	public void sumarJugadas() throws SQLException {
		try (PreparedStatement ps =  conexion.prepareStatement(Constants.SUMAR_JUGADAS)) { 
			System.out.println(ps.toString());
			ps.execute();
		}
	}

}
