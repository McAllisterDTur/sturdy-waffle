
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String		nameEn;	//English name

	private String		name;	//Spanish name

	private Category	father;


	@NotNull
	public String getNameEn() {
		return this.nameEn;
	}

	public void setNameEn(final String nameEn) {
		this.nameEn = nameEn;
	}

	@NotNull
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
		return "Category [nameEs=" + this.name + "nameEn=" + this.nameEn + ", father=" + this.father + "]";
	}
}
