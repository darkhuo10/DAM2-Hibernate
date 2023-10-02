package ProyectoMySQL.Hibernate;

import java.util.List;
import java.util.concurrent.Flow.Publisher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.persistence.Query;

import nba.Equipos;
import nba.Estadisticas;

public class App {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("NbaHibernate");

		EntityManager em = emf.createEntityManager();

		listarEquipos(em);
		System.out.println("--------------------------------------");
		equiposConferenciaOeste(em);
		System.out.println("--------------------------------------");
		estadisticasJugadores(em);
		System.out.println("--------------------------------------");
		puntosPartido(em);
		em.close();
		emf.close();
	}

	@SuppressWarnings("unchecked")
	private static void listarEquipos(EntityManager em) {
		// a) Lista todos los registros de la entidad equipos.
		System.out.println("CONSULTA 1.");
		String selectEquipos = "SELECT e from Equipos e";
		Query query = em.createQuery(selectEquipos);

		List<Equipos> listaEquipos = (List) query.getResultList();
		for (Equipos e : listaEquipos) {
			System.out.println("EQUIPO: \n Nombre: " + e.getNombre() + "\n Ciudad: " + e.getCiudad()
					+ "\n Conferencia: " + e.getConferencia() + "\n Division: " + e.getDivision());
		}

	}

	private static void equiposConferenciaOeste(EntityManager em) {
		// b) En la entidad equipos, filtra aquellos equipos que corresponden a la
		// conferencia oeste (west).
		System.out.println("CONSULTA 2.");
		Query query2 = (Query) em.createQuery("SELECT e FROM Equipos e WHERE e.conferencia = 'West'");
		List<Equipos> listaEquipos2 = (List<Equipos>) query2.getResultList();
		for (Equipos e : listaEquipos2) {
			System.out.println("EQUIPO: \n Nombre: " + e.getNombre() + "\n Ciudad: " + e.getCiudad()
					+ "\n Conferencia: " + e.getConferencia() + "\n Division: " + e.getDivision());
		}

	}

	private static void estadisticasJugadores(EntityManager em) {
		// c) Se quiere obtener la siguiente informacion en una sola consulta el nombre,
		// peso
//		y posición de los jugadores, así como puntos por Partido y TaponesPorPartido y 
//		RebotesPorPartido de la entidad estadísticas.

		System.out.println("CONSULTA 3.");
		Query query3 = (Query) em.createQuery(
				"SELECT j.nombre ,j.peso ,j.posicion ,e.puntosPorPartido ,e.taponesPorPartido ,e.rebotesPorPartido FROM Jugadores j, Estadisticas e WHERE j.codigo=e.jugadores.codigo");
		List<Object[]> lista = (List<Object[]>) query3.getResultList();
		for (Object[] o : lista) {
			String nombre = (String) o[0];
			int peso = (int) o[1];
			String altura = (String) o[2];
			float puntos = (float) o[3];
			float tapones = (float) o[4];
			float rebotes = (float) o[5];
			System.out.println("DATOS: \n Nombre: " + nombre + "\n Peso: " + peso + "\n Altura: " + altura
					+ "\n Puntos por partido: " + puntos + "\n Tapones por partido: " + tapones
					+ "\n Rebotes por partido: " + rebotes);
		}

	}

	private static void puntosPartido(EntityManager em) {
		System.out.println("CONSULTA 4");
		Query query4 = em.createQuery("SELECT e FROM Estadisticas e ORDER BY e.puntosPorPartido asc");
		List<Estadisticas> listEstadisticas1 = (List<Estadisticas>) query4.getResultList();
		for (Estadisticas estadisticas : listEstadisticas1) {
			System.out.println("ESTADÍSTICAS: \n Código jugador: " + estadisticas.getJugadores().getCodigo()
					+ "\n Puntos por partido: " + estadisticas.getPuntosPorPartido() + "\n Asistencias Por Partido: "
					+ estadisticas.getAsistenciasPorPartido() + "\n Tapones por partido: "
					+ estadisticas.getTaponesPorPartido() + "\n Rebotes por partido: "
					+ estadisticas.getRebotesPorPartido());
		}

	}

}
