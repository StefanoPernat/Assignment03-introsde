package introsde.assignment.soap;

import introsde.assignment.model.Person;
import introsde.assignment.wrapper.PeopleListWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

@WebService(endpointInterface="introsde.assignment.soap.People")
public class PeopleImpl implements People {

	@Override
	public PeopleListWrapper getPeople() {
		System.out.println("--> REQUESTED: getPeople() ");
		PeopleListWrapper peopleWrapper = new PeopleListWrapper();
		peopleWrapper.setPeopleList(Person.getAll());
		/*ArrayList<Person> result = new ArrayList<Person>();
		result.addAll(Person.getAll());*/
		if(peopleWrapper.getPeopleList().size() > 0){
			System.out.println("--> Found some People");
		}
		else{
			System.out.println("--> No People Found");
		}
		return peopleWrapper;
	}
}
