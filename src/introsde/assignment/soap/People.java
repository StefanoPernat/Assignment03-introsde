package introsde.assignment.soap;

import introsde.assignment.model.Measure;
import introsde.assignment.model.Person;
import introsde.assignment.wrapper.HistoryListWrapper;
import introsde.assignment.wrapper.PeopleListWrapper;
import introsde.assignment.wrapper.TypesWrapper;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
public interface People {
	@WebMethod(operationName="readPeopleList")
	@WebResult(name="people")
	public PeopleListWrapper getPeople();
	
	@WebMethod(operationName="readPerson")
	@WebResult(name="person")
	public Person getPerson(@WebParam(name="personId") Long id);
	
	@WebMethod(operationName="updatePerson")
	@WebResult(name="personId")
	public Long updatePerson(@WebParam(name="person") Person target);
	
	@WebMethod(operationName="createPerson")
	@WebResult(name="personId")
	public Long createPerson(@WebParam(name="person") Person target);
	
	@WebMethod(operationName="readPersonHistory")
	@WebResult(name="healthProfile-history")
	public HistoryListWrapper showHistory(@WebParam(name="personId") Long id, @WebParam(name="measureType") String measureType);
	
	@WebMethod(operationName="readPersonMeasurement")
	@WebResult(name="healthProfile-history")
	public HistoryListWrapper readHistoryById(@WebParam(name="personId") Long id, @WebParam(name="measureType") String measureType, @WebParam(name="mid") Long mid);
	
	@WebMethod(operationName="savePersonMeasurement")
	@WebResult(name="mid")
	public Long addMeasure(@WebParam(name="personId") Long id, @WebParam(name="measure") Measure measure);
	
	@WebMethod(operationName="readMeasureTypes")
	@WebResult(name="measureTypes")
	public TypesWrapper showMeasureTypes();
}
