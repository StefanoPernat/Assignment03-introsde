package introsde.assignment.model;

import introsde.assignment.adapter.DateAdapter;
import introsde.assignment.converter.DateConverter;
import introsde.assignment.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(name="\"Measure\"")
@NamedQueries({
	@NamedQuery(name="Measure.getCurrentHealth", query="SELECT m FROM Measure m WHERE m.person.idPerson=:id AND m.isCurrent=1"),
	@NamedQuery(name="Measure.getHistoryHealth", query="SELECT m FROM Measure m WHERE m.person.idPerson=:id AND m.isCurrent=0"),
	@NamedQuery(name="Measure.getHistoryByName", query="SELECT m FROM Measure m WHERE m.person.idPerson=:id AND m.measureType=:type AND m.isCurrent=0"),
	@NamedQuery(name="Measure.getSelectedHistoryMeasure", query="SELECT m FROM Measure m WHERE m.person.idPerson=:id AND m.measureType=:type AND m.idMeasure=:mid AND m.isCurrent=0"),
	@NamedQuery(name="Measure.get", query="SELECT m FROM Measure m WHERE m.person.idPerson=:id AND m.measureType=:type AND m.isCurrent=1"),
	@NamedQuery(name="Measure.types", query="SELECT distinct(m.measureType) FROM Measure m")
})
@XmlRootElement(name="measure")
@XmlAccessorType(XmlAccessType.FIELD)
public class Measure implements Serializable {
	
	private static final long serialVersionUID = 8009509583852124557L;

	@Id
	@GeneratedValue(generator="sqlite_measure")
	@TableGenerator(name="sqlite_measure", table="\"sqlite_sequence\"", pkColumnName="name", valueColumnName="seq", pkColumnValue="Measure")
	@Column(name="\"idMeasure\"")
	@XmlElement(name="mid")
	private Long idMeasure;
	
	@Column(name="\"dateRegistered\"")
	@Convert(converter=DateConverter.class)
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dateRegistered;
	
	@Column(name="\"measureType\"")
	private String measureType;
	
	@Column(name="\"measureValue\"")
	private String measureValue;
	
	@Column(name="\"valueType\"")
	@XmlElement(name="measureValueType")
	private String valueType;
	
	@Column(name="\"isCurrent\"")
	@XmlTransient
	private int isCurrent; // 1 current Health, 0 History value
	
	@ManyToOne
	@JoinColumn(name="\"idPerson\"", referencedColumnName="\"idPerson\"")
	@XmlTransient
	private Person person;

	public Long getIdMeasure() {
		return idMeasure;
	}

	public Date getDateRegistered() {
		return dateRegistered;
	}

	public String getMeasureType() {
		return measureType;
	}

	public String getMeasureValue() {
		return measureValue;
	}

	public String getValueType() {
		return valueType;
	}

	public int IsCurrent() {
		return isCurrent;
	}

	public Person getPerson() {
		return person;
	}

	public void setIdMeasure(Long idMeasure) {
		this.idMeasure = idMeasure;
	}

	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public void setCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public String toString(){
		return "Measure("+idMeasure+","+dateRegistered+","+measureType+","+measureValue+","+valueType+","+isCurrent+")";
	}
	
	public static List<Measure> getHistoryByName(Long id, String measureType)
	{
		try{
			Person target = Person.getOne(id);
			if(target!=null){
				List<Measure> historyByName = LifeCoachDao.instance.getEntityManager().createNamedQuery("Measure.getHistoryByName", Measure.class)
						  .setParameter("id", id.longValue())
						  .setParameter("type", measureType).getResultList();
				LifeCoachDao.instance.destroyEntityManager();
				return historyByName;
			}
			else{
				return null;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public static Measure getHistoryMeasure(Long id, String measureType, Long mid){
		try{
			Person target = Person.getOne(id);
			if(target!=null){
				Measure historymeasure = LifeCoachDao.instance.getEntityManager().createNamedQuery("Measure.getSelectedHistoryMeasure",Measure.class)
																				 .setParameter("id",id.longValue())
																				 .setParameter("type", measureType)
																				 .setParameter("mid", mid.longValue()).getSingleResult();
				LifeCoachDao.instance.destroyEntityManager();
				return historymeasure;																 
			}
			else return null;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	private static Measure foundMeasure(Long id, String type){
		Measure founded = null;
		try{
			founded = LifeCoachDao.instance.getEntityManager().createNamedQuery("Measure.get", Measure.class)
													.setParameter("id", id)
													.setParameter("type", type).getSingleResult();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return founded;
	}
	
	public static Long addMeasure(Long id, Measure m){
		Measure measure = null;
		
		Person target = Person.getOne(id);
		if(target != null){
			System.out.println("--> found person with id:"+id);
			measure = foundMeasure(target.getIdPerson(), m.getMeasureType());
			if(measure != null){
				System.out.println("--> found measure....");
				measure.setCurrent(0);
				measure.setPerson(target);
				
				EntityTransaction tx = LifeCoachDao.instance.getEntityManager().getTransaction();
				tx.begin();
				LifeCoachDao.instance.getEntityManager().persist(measure);
				tx.commit();
				
				System.out.println("--> measure added");
			}
			else{
				measure = new Measure();
				measure.setIdMeasure(new Long(0));
				measure.setCurrent(1);
				measure.setDateRegistered(m.getDateRegistered());
				measure.setMeasureType(m.getMeasureType());
				measure.setMeasureValue(m.getMeasureValue());
				measure.setValueType(m.getValueType());
				measure.setPerson(target);
				
				System.out.println("--> measure created...");
				
				EntityTransaction tx = LifeCoachDao.instance.getEntityManager().getTransaction();
				tx.begin();
				LifeCoachDao.instance.getEntityManager().persist(measure);
				tx.commit();
				
				System.out.println("--> measure added...");
			}
			LifeCoachDao.instance.destroyEntityManager();
			return measure.getIdMeasure();
		}
		else return new Long(-1);
	}
	
	public static List<String> getTypes(){
		return LifeCoachDao.instance.getEntityManager().createNamedQuery("Measure.types", String.class).getResultList();
	}
	
}
