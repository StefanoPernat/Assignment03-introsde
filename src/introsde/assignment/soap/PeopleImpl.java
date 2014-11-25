package introsde.assignment.soap;

import introsde.assignment.model.Person;
import introsde.assignment.wrapper.PeopleListWrapper;

import javax.jws.WebService;

@WebService(endpointInterface="introsde.assignment.soap.People", serviceName="PeopleImplService")
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

	@Override
	public Person getPerson(Long id) {
		System.out.println("--> REQUESTED: getPerson("+id+")");
		Person target = Person.getOne(id);
		if(target == null)
			System.out.println("--> No Person found with id: "+id);
		else System.out.println("--> "+target.toString());
		return target;
	}

	@Override
	public Long updatePerson(Person target) {
		// TODO Auto-generated method stub
		System.out.println("--> REQUESTED: updatePerson(p)");
		System.out.println("--> "+target.toString());
		return Person.updatePerson(target);
	}
}
