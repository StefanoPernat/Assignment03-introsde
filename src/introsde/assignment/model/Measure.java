package introsde.assignment.model;

import introsde.assignment.adapter.DateAdapter;
import introsde.assignment.converter.DateConverter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
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
	@NamedQuery(name="Measure.getHistoryHealth", query="SELECT m FROM Measure m WHERE m.person.idPerson=:id AND m.isCurrent=0")
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
	
}
