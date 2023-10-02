package ProyectoMySQL.Hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import nba.Equipos;

import nba.Jugadores;


public class ExamenHibernate {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("NbaHibernate");

		EntityManager em = emf.createEntityManager();
		
		consulta1(em);
		
		consulta2(em);
		
		
		
		
		
	}

	private static void consulta2(EntityManager em) {
		System.out.println("CONSULTA 2");
		Equipos e = new Equipos("Mavericks");
		
		em.getTransaction().begin();
		Jugadores j1 = new Jugadores(700, e, "Jugador 1", "Florida", "6-9", 185, "F-G", null);
		Jugadores j2 = new Jugadores(701, e, "Jugador 2", "Florida", "6-9", 185, "F-G", null);
		Jugadores j3 = new Jugadores(702, e, "Jugador 3", "Florida", "6-9", 185, "F-G", null);

		em.persist(j1);
		em.persist(j2);
		em.persist(j3);

		em.getTransaction().commit();
	}

	private static void consulta1(EntityManager em) {
		System.out.println("CONSULTA 1");
		String select = "SELECT j.Nombre, p.EquipoLocal, p.EquipoVisitante, p.PuntosLocal, p.PuntosVisitante, p.Temporada"
				+ " FROM jugadores j"
				+ " INNER JOIN partidos p ON j.NombreEquipo = p.EquipoLocal OR j.NombreEquipo = p.EquipoVisitante"
				+ " WHERE j.NombreEquipo = 'Mavericks';";
		Query query = em.createNativeQuery(select);
		List<Object[]> datos = (List<Object[]>) query.getResultList();
		for (Object[] o : datos) {
			String nombre = (String) o[0];
			String local = (String) o[1];
			String visitante = (String) o[2];
			int puntosLocal = (int) o[3];
			int puntosVisitante = (int) o[4];
			System.out.println("JUGADOR: \n\tNombre: " + nombre + "\nPARTIDO: \n\tEquipo local: " + local 
					+ "\n\tEquipo Visitante: " + visitante + "\n\tPuntos local: " + puntosLocal + "\n\tPuntos visitante: " + puntosVisitante);

		}
	}
	
}

