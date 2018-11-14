
package utilities.internal;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import utilities.DatabaseConfig;
import domain.FixUpTask;

public class ConsoleFinder {

	public static void run() throws Throwable {

		System.out.println("FinderSearch v1.2");
		final Scanner sc = new Scanner(System.in);
		System.out.println("Introduzca un parámetro de búsqueda: ('exit' para salir.)");
		String word = sc.nextLine();

		while (!word.equals("exit")) {
			ConsoleFinder.busqueda(word);
			System.out.println("Introduzca un parámetro de búsqueda:");
			word = sc.nextLine();
		}

		sc.close();

	}
	private static void busqueda(final String word) throws Throwable {

		//TODO: Consulta
		final HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
		final EntityManagerFactory emf = provider.createEntityManagerFactory(DatabaseConfig.PersistenceUnit, null);
		final EntityManager em = emf.createEntityManager();
		final FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		//fullTextEntityManager.createIndexer().startAndWait();
		em.getTransaction().begin();

		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(FixUpTask.class).get();

		final org.apache.lucene.search.Query query = qb.keyword().onFields("ticker", "description", "address").matching(word.trim()).createQuery();

		final javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(query, FixUpTask.class);

		final List<?> res = jpaQuery.getResultList();
		if (res.isEmpty()) {
			System.err.println("No hay resultados..");
			System.out.println();
		} else
			SchemaPrinter.print(res);
		em.getTransaction().commit();
		em.close();
	}
}
