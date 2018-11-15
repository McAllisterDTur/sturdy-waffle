
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
	private HandyWorker					handyWorker;
	private PersonalRecord	personalRecord;
	private Collection<ProfessionalRecord> ProfessionalRecords;
	private Collection<EndorserRecord> endorserRecords;
	private Collection<MiscellaneousRecord> miscellaneousRecords;
	private Collection<EducationRecord> educationRecord;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "(\\d{2})(([0][1-9])|([1][0-2]))(([0][1-9])|[1-2][0-9]|[3][0-1])-(([A-Z]|[0-9]){6})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@OneToOne(optional=false)
	public HandyWorker getHandyWorker() {
		return handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	public PersonalRecord getPersonalRecord() {
		return personalRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	@OneToMany(cascade=CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return ProfessionalRecords;
	}

	public void setProfessionalRecords(
			final Collection<ProfessionalRecord> professionalRecords) {
		ProfessionalRecords = professionalRecords;
	}

	@OneToMany(cascade=CascadeType.ALL)
	public Collection<EndorserRecord> getEndorserRecords() {
		return endorserRecords;
	}

	public void setEndorserRecords(final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}
	@OneToMany(cascade=CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return miscellaneousRecords;
	}

	public void setMiscellaneousRecords(
			final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	public Collection<EducationRecord> getEducationRecord() {
		return educationRecord;
	}

	public void setEducationRecord(final Collection<EducationRecord> educationRecord) {
		this.educationRecord = educationRecord;
	}
	@Override
	public String toString() {
		return "Curricula [ticker=" + ticker + ", handyWorker=" + handyWorker + "]";
	}

}
