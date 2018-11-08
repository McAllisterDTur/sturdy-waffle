
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String		name;

	private Category	father;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ManyToOne(optional = true)
	public Category getFather() {
		return this.father;
	}

	public void setFather(final Category father) {
		this.father = father;
	}

	@Override
	public String toString() {
		return "Category [name=" + this.name + ", father=" + this.father + "]";
	}

}
