
package forms;

import javax.validation.constraints.Pattern;

public class Calculator {

	private Double	x;
	private Double	y;
	private String	operator;
	private Double	result;


	public Double getX() {
		return this.x;
	}

	public void setX(final Double x) {
		this.x = x;
	}

	public Double getY() {
		return this.y;
	}

	public void setY(final Double y) {
		this.y = y;
	}

	@Pattern(regexp = "^[\\+\\-\\*\\/]$")
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(final String operator) {
		this.operator = operator;
	}

	public Double getResult() {
		return this.result;
	}

	public void setResult(final Double result) {
		this.result = result;
	}

	public void compute() {
		switch (this.getOperator()) {
		case "+":
			this.setResult(this.getX() + this.getY());
			break;
		case "-":
			this.setResult(this.getX() - this.getY());
			break;
		case "*":
			this.setResult(this.getX() * this.getY());
			break;
		case "/":
			this.setResult(this.getX() / this.getY());
			break;
		}
	}
}
