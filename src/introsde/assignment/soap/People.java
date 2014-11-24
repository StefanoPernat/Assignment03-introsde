package introsde.assignment.soap;

import introsde.assignment.model.Person;
import introsde.assignment.wrapper.PeopleListWrapper;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
public interface People {
	@WebMethod(operationName="getPeopleList")
	@WebResult(name="people")
	public PeopleListWrapper getPeople();
}
