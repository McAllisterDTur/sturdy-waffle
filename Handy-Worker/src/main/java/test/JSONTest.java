
package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import utilities.internal.SchemaPrinter;
import utilities.json.Conversion;
import domain.Category;

public class JSONTest {

	public static void main(final String[] args) {

		//Creamos una categoría 
		final Category category1 = new Category();
		category1.setName("CATEGORIA");
		//Creamos una categoría hija de la primera
		final Category category2 = new Category();
		category2.setName("Hija");
		category2.setFather(category1);
		//Convertimos la segunda categoría a JSON
		final String jsonFromClass = Conversion.objectToJson(category2);

		System.out.println(jsonFromClass);

		System.out.println("-----------------");
		//Leemos el JSON en la ruta "src/main/java/test/JSON.txt"
		final String json = JSONTest.stringLoader("src/main/java/test/JSON.txt");
		//Creamos un objeto a partir del JSON
		final Object obj = Conversion.jsonToObject(json, Category.class);
		//Lo imprimimos
		SchemaPrinter.print(obj);
	}
	private static String stringLoader(final String file) {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		final BufferedReader br = new BufferedReader(fr);
		String ln = "";
		final StringBuilder sb = new StringBuilder();
		try {
			while ((ln = br.readLine()) != null)
				sb.append(ln);
		} catch (final IOException e) {
			System.err.println(e.toString());
		}
		return sb.toString();
	}
}
