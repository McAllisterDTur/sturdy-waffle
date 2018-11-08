
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Atributes

	private String							ticker;

	// Relationships
	private PersonalRecord					personalRecord;

	private Collection<ProfessionalRecord>	professionalRecord;

	private Collection<EducationRecord>		educationRecord;

	private Collection<EndorserRecord>		endorserRecord;

	private Collection<MiscellaneousRecord>	miscellaneousRecord;


	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^(\\d{2})(\\d{2})(\\d{2})\\-([A-Z]{6})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@OneToOne(optional = false)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalRecord() {
		return this.professionalRecord;
	}

	public void setProfessionalRecord(final Collection<ProfessionalRecord> professionalRecord) {
		this.professionalRecord = professionalRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationRecord> getEducationRecord() {
		return this.educationRecord;
	}

	public void setEducationRecord(final Collection<EducationRecord> educationRecord) {
		this.educationRecord = educationRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EndorserRecord> getEndorserRecord() {
		return this.endorserRecord;
	}

	public void setEndorserRecord(final Collection<EndorserRecord> endorserRecord) {
		this.endorserRecord = endorserRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}

	public void setMiscellaneousRecord(final Collection<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}

	@Override
	public String toString() {
		return "Curricula [ticker=" + this.ticker + ", personalRecord=" + this.personalRecord + ", professionalRecord=" + this.professionalRecord + ", educationRecord=" + this.educationRecord + ", endorserRecord=" + this.endorserRecord
			+ ", miscellaneousRecord=" + this.miscellaneousRecord + "]";
	}

}
