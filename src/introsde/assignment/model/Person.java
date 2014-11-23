package introsde.assignment.model;

import introsde.assignment.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name="\"Person\"")
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Person implements Serializable {
	private static final long serialVersionUID = 1573094360811963821L;

	@Id
	@GeneratedValue(generator="sqlite_person")
	@TableGenerator(name="sqlite_person", table="\"sqlite_sequence\"", pkColumnName="\"name\"", valueColumnName="\"seq\"", pkColumnValue="Person")
	@Column(name="\"idPerson\"")
	@XmlElement
	private Long idPerson;
	
	@Column(name="\"firstname\"")
	@XmlElement
	private String firstname;
	
	@Column(name="\"lastname\"")
	@XmlElement
	private String lastname;
	
	
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Measure> currentHealth;
	
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Measure> healthHistory;

	
	//GETTER
	public Long getIdPerson() {
		return idPerson;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public List<Measure> getCurrentHealth() {
		this.currentHealth.clear();
		this.currentHealth.addAll(LifeCoachDao.instance.getCurrentHealthMeasures(this.idPerson.intValue()));
		return this.currentHealth;
	}

	public List<Measure> getHealthHistory() {
		this.healthHistory.clear();
		this.healthHistory.addAll(LifeCoachDao.instance.getHistoryHealthMeasures(this.idPerson.intValue()));
		return this.healthHistory;
	}

	
	//SETTER
	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setCurrentHealth(List<Measure> currentHealth) {
		this.currentHealth = currentHealth;
	}

	public void setHealthHistory(List<Measure> healthHistory) {
		this.healthHistory = healthHistory;
	}

	
	
	
	
}