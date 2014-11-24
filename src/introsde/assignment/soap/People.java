package introsde.assignment.soap;

import introsde.assignment.model.Person;
import introsde.assignment.wrapper.PeopleListWrapper;

import java.util.List;

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
}
