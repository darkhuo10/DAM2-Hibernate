package ProyectoMySQL.Hibernate;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import nba.Equipos;
import nba.Estadisticas;
import nba.EstadisticasId;
import nba.Jugadores;

public class Crud {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("NbaHibernate");
		EntityManager em = emf.createEntityManager();

//		Scanner s = new Scanner(System.in);
//		System.out.println("EJERCICIO 1");
//		System.out.println("Introduce el código del jugador que deseas consultar: ");
//		int cod = s.nextInt();
//		buscaJugador(em, cod);
//		System.out.println("\n\n");
		
		System.out.println("EJERCICIO 2");
		jugadoresEquipos(em);
		System.out.println("\n\n");

//		System.out.println("EJERCICIO 3");
//		insertaDatos(em);
//		System.out.println("\n\n");

		
	}

	private static void buscaJugador(EntityManager em, int codigo) {
		Query query1 = (Query) em.createNativeQuery("SELECT j.Nombre, j.NombreEquipo, "
				+ "e.Temporada, e.PuntosPorPartido, e.AsistenciasPorPartido, e.TaponesPorPartido, e.RebotesPorPartido"
				+ " FROM Jugadores j, Estadisticas e WHERE j.Codigo = e.CodigoJugador AND j.Codigo =" + codigo);
		List<Object[]> jugadores = (List<Object[]>) query1.getResultList();
		System.out.println();

		String nombre = (String) jugadores.get(0)[0];
		String equipo = (String) jugadores.get(0)[1];
		System.out.println("DATOS DEL JUGADOR: " + codigo + "\nNombre: " + nombre + "\nEquipo: " + equipo
				+ "\nTemporada \tPtos \tAsis \tTap \tReb");
		System.out.println("============================================");
		for (Object[] o : jugadores) {
			String temporada = (String) o[2];
			float puntos = (float) o[3];
			float asistencias = (float) o[4];
			float tapones = (float) o[5];
			float rebotes = (float) o[6];
			System.out.println(temporada + " \t\t" + puntos + " \t" + asistencias + " \t" + tapones + " \t" + rebotes);

		}
		System.out.println("============================================");
		System.out.println("Número de registros: " + jugadores.size());
		System.out.println("============================================");

	}

	private static void jugadoresEquipos(EntityManager em) {
		Query query1 = (Query) em.createQuery("SELECT e FROM Equipos e");
		List<Equipos> equipos = (List<Equipos>) query1.getResultList();
		System.out.println("Número de equipos: " + equipos.size());
		System.out.println("============================================");
		for (Equipos e : equipos) {
			System.out.println("Equipo: " + e.getNombre());
			Query query2 = (Query) em
					.createNativeQuery("SELECT j.Codigo, j.Nombre, TRUNCATE(AVG(s.PuntosPorPartido),2) "
							+ "FROM Estadisticas s, Jugadores j " + "WHERE j.NombreEquipo LIKE '" + e.getNombre()
							+ "' AND s.CodigoJugador = j.Codigo " + "GROUP BY j.Codigo ");
			List<Object[]> jugadoresEquipos = (List<Object[]>) query2.getResultList();
			for (Object[] o : jugadoresEquipos) {
				int codigo = (int) o[0];
				String nombre = (String) o[1];
				double media = (double) o[2];
				System.out.println(codigo + ", " + nombre + ": " + media);

			}
			System.out.println("·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · ");
			System.out.println("============================================");

		}

	}

	private static void insertaDatos(EntityManager em){ 
		int codigo = 123; 
			
			em.getTransaction().begin();
			EstadisticasId id1 = new EstadisticasId("05/06", codigo);
			EstadisticasId id2 = new EstadisticasId("06/07", codigo);

			Estadisticas e1 = new Estadisticas();
			e1.setId(id1);
			e1.setPuntosPorPartido(7f);
			e1.setAsistenciasPorPartido(0f);
			e1.setTaponesPorPartido(0f);
			e1.setRebotesPorPartido(5f);

			Estadisticas e2 = new Estadisticas();
			e2.setId(id2);
			e2.setPuntosPorPartido(10f);
			e2.setAsistenciasPorPartido(0f);
			e2.setTaponesPorPartido(3f);
			e2.setRebotesPorPartido(0f);
			em.persist(e1);
			em.persist(e2);

			em.getTransaction().commit();

		}

	
}
