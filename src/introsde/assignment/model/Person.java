package introsde.assignment.model;

import introsde.assignment.adapter.DateAdapter;
import introsde.assignment.converter.DateConverter;
import introsde.assignment.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.ArrayList;
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
				query="SELECT p.idPerson FROM Person p WHERE p.firstname=:fname AND p.lastname=:lname"),
	@NamedQuery(name="Person.maxId", query="SELECT max(p.idPerson) FROM Person p")
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
	public List<Measure> currentHealth;
	
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
		ArrayList<Measure> currentValueInCurrentHealth = new ArrayList<Measure>();
		currentValueInCurrentHealth.addAll(this.currentHealth);
		this.currentHealth.clear();
		for(Measure m: currentValueInCurrentHealth){
			if(m.IsCurrent() == 1){
				this.currentHealth.add(m);
			}
		}
		return this.currentHealth;
		/*this.currentHealth.clear();
		this.currentHealth.addAll(LifeCoachDao.instance.getCurrentHealthMeasures(this.idPerson.intValue()));
		return this.currentHealth;*/
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
														  .setParameter("id", id.longValue()).getSingleResult();
			LifeCoachDao.instance.destroyEntityManager();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			res = null;
		}
		
		return res;
	}
	
	public static Long updatePerson(Person target)
	{
		Person fromDB = null;
		
		try{
			fromDB = LifeCoachDao.instance.getEntityManager().createNamedQuery("Person.getOne", Person.class)
					  										 .setParameter("id", target.getIdPerson().longValue()).getSingleResult();
			if(fromDB != null){
				Person updated = new Person();
				updated.setIdPerson(target.getIdPerson());
				updated.setFirstname(target.getFirstname());
				updated.setLastname(target.getLastname());
				updated.setBirthdate(target.getBirthdate());
				
				EntityTransaction tx = LifeCoachDao.instance.getEntityManager().getTransaction();
				tx.begin();
				updated = LifeCoachDao.instance.getEntityManager().merge(updated);
				tx.commit();
				LifeCoachDao.instance.destroyEntityManager();
				
				return updated.getIdPerson();
			}
			else{
				throw new Exception("id: "+target.getIdPerson()+" not found!");
			}
				
			/*Person updated = new Person();
			updated.setIdPerson(id);
			updated.setFirstname(target.getFirstname());
			updated.setLastname(target.getLastname());
			updated.setBirthdate(target.getBirthdate());
			
			EntityTransaction tx = LifeCoachDao.instance.getEntityManager().getTransaction();
			tx.begin();
			updated = LifeCoachDao.instance.getEntityManager().merge(updated);
			tx.commit();
			LifeCoachDao.instance.destroyEntityManager();*/
		}catch(Exception ex){
			ex.printStackTrace();
			return new Long(-1);
		}
	}
	
	private static Long getMaxId(){
		Long maxid = null;
		
		try{
			maxid = LifeCoachDao.instance.getEntityManager().createNamedQuery("Person.maxId",Long.class).getSingleResult();
			LifeCoachDao.instance.destroyEntityManager();
			return maxid;
		}catch(Exception ex){
			ex.printStackTrace();
			return new Long(-1);
		}
	}
	
	public static Long createPerson(Person p){
		EntityTransaction tx = LifeCoachDao.instance.getEntityManager().getTransaction();
		tx.begin();
		LifeCoachDao.instance.getEntityManager().persist(p);
		tx.commit();
		LifeCoachDao.instance.destroyEntityManager();
		
		return Person.getMaxId();
	}
	
	public boolean hasCurrentHealth()
	{
		return this.currentHealth == null;
	}
	
	public String toString(){
		return "Person("+idPerson+","+firstname+","+lastname+","+birthdate+")";
	}
	
}
