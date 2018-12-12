
package freemarker.test;

public class Car {

	private String	name;
	private int		price;


	public Car() {
	}

	public Car(final String name, final int price) {

		this.name = name;
		this.price = price;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(final int price) {
		this.price = price;
	}
}
