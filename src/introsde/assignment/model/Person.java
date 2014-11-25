package introsde.assignment.model;

import introsde.assignment.adapter.DateAdapter;
import introsde.assignment.converter.DateConverter;
import introsde.assignment.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(name="\"Person\"")
@NamedQueries({
	@NamedQuery(name="Person.getAll", query="SELECT p FROM Person p"),
	@NamedQuery(name="Person.getOne", query="SELECT p FROM Person p WHERE p.idPerson=:id"),
	@NamedQuery(name="Person.getIdFromFullName", 
				query="SELECT p.idPerson FROM Person p WHERE p.firstname=:fname AND p.lastname=:lname")
})
@XmlRootElement(name="person")
@XmlAccessorType(XmlAccessType.NONE)
public class Person implements Serializable {
	private static final long serialVersionUID = 1573094360811963821L;

	@Id
	@GeneratedValue(generator="sqlite_person")
	@TableGenerator(name="sqlite_person", table="\"sqlite_sequence\"", pkColumnName="name", valueColumnName="seq", pkColumnValue="Person")
	@Column(name="\"idPerson\"")
	@XmlElement(name="personId")
	private Long idPerson;
	
	@Column(name="\"firstname\"")
	@XmlElement
	private String firstname;
	
	@Column(name="\"lastname\"")
	@XmlElement
	private String lastname;
	
	@Column(name="\"birthdate\"")
	@XmlElement
	@Convert(converter=DateConverter.class)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date birthdate;
	
	
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

	@XmlElementWrapper(name="currentHealth")
	@XmlElement(name="measure")
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
	
	

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setCurrentHealth(List<Measure> currentHealth) {
		this.currentHealth = currentHealth;
	}

	public void setHealthHistory(List<Measure> healthHistory) {
		this.healthHistory = healthHistory;
	}

	public static List<Person> getAll(){
		List<Person> res = null;
		try{
				res = LifeCoachDao.instance.getEntityManager().createNamedQuery("Person.getAll", Person.class)
															 .getResultList();
				LifeCoachDao.instance.destroyEntityManager();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		for(Person p: res){
			System.out.println(p.toString());
		}
		
		return res;
	}
	
	public static Person getOne(Long id){
		Person res = null;
		try{
			res = LifeCoachDao.instance.getEntityManager().createNamedQuery("Person.getOne", Person.class)
														  .setParameter("id", id.intValue()).getSingleResult();
			LifeCoachDao.instance.destroyEntityManager();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			res = null;
		}
		
		return res;
	}
	
	public static Long updatePerson(Person target)
	{
		Long id = null;
		
		try{
			id = LifeCoachDao.instance.getEntityManager().createNamedQuery("Person.getIdFromFullName", Long.class)
														 .setParameter("fname", target.getFirstname())
														 .setParameter("lname", target.getLastname()).getSingleResult();
			Person updated = new Person();
			updated.setIdPerson(id);
			updated.setFirstname(target.getFirstname());
			updated.setLastname(target.getLastname());
			updated.setBirthdate(target.getBirthdate());
			
			EntityTransaction tx = LifeCoachDao.instance.getEntityManager().getTransaction();
			tx.begin();
			updated = LifeCoachDao.instance.getEntityManager().merge(updated);
			tx.commit();
			LifeCoachDao.instance.destroyEntityManager();
		}catch(Exception ex){
			ex.printStackTrace();
			id = new Long(-1);
		}
		
		return id;
	}
	
	public String toString(){
		return "Person("+idPerson+","+firstname+","+lastname+","+birthdate+")";
	}
	
}
