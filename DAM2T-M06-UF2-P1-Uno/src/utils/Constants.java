package utils;

public class Constants {
	
	public static final String SCHEMA_NAME = "dam2tm06uf2p1";
	public static final String CONNECTION = 
			"jdbc:mysql://localhost:3306/" + 
			SCHEMA_NAME + 
			"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
	public static final String USER_CONNECTION = "root";
	public static final String PASS_CONNECTION = "root";
	
	public static final String GET_CARTA_LAST_ID = "select ifnull(max(id),0)+1 from carta";
	public static final String GET_ULTIMA_CARTA = "select max(id), id_carta, (select numero from carta where id = id_carta), "
			+ "(select color from carta where id = id_carta), "
			+ "(select id_jugador from carta where id = id_carta) "
			+ "from partida";
	public static final String GET_MANO_JUGADOR_POR_ID = "select c.id, c.numero, c.color, c.id_jugador from carta c left join partida p on p.id_carta = c.id where id_jugador = ? and p.id is null";
	public static final String GET_JUGADOR_POR_USU_PASS = "select * from jugador where usuario = ? and password = ?";
	public static final String GUARDAR_MANO = "insert into carta (id_jugador, numero, color) values (?,?,?)";
	public static final String UPDATE_JUGADOR = "update jugador set partidas = ?, ganadas = ? where id = ?";
	public static final String JUGAR_CARTA = "insert into partida (id_carta) values (?)";
	public static final String BORRAR_ULTIMA_CARTA_JUGADA = "delete from partia where id = (select max(id) from partida)";
	public static final String BORRAR_ULTIMA_CARTA_JUGADA_TABLA_CARTA = "delete from carta where id = ?";
	public static final String BORRAR_TABLA_CARTA = "delete from carta";
	public static final String BORRAR_TABLA_PARTIDA = "delete from partida";
	public static final String SUMAR_GANADAS = "update jugador set ganadas = ganadas + 1";
	public static final String SUMAR_JUGADAS = "update jugador set jugadas = jugadas + 1 where id in (select id_jugador from carta where id in (select id_carta from partida))";
	
}
