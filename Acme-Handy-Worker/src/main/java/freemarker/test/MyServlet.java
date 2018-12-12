
package freemarker.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// @WebServlet(name = "MyServlet", urlPatterns = {
// "/"
// })
public class MyServlet extends HttpServlet {

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		final Car c1 = new Car("Audi", 52642);
		final Car c2 = new Car("Volvo", 29000);
		final Car c3 = new Car("Skoda", 9000);

		final List<Car> cars = new ArrayList<>();
		cars.add(c1);
		cars.add(c2);
		cars.add(c3);

		request.setAttribute("cars", cars);

		request.getRequestDispatcher("/index.ftl").forward(request, response);

	}
}
