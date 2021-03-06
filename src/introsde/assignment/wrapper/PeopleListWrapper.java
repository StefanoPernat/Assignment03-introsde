package introsde.assignment.wrapper;

import introsde.assignment.model.Person;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="people")
public class PeopleListWrapper {
	@XmlElement(name="person")
	private ArrayList<Person> peopleList;

	public void setPeopleList(List<Person> peopleList) {
		
		this.peopleList = new ArrayList<Person>();
		this.peopleList.addAll(peopleList);
		
		/*System.out.println("================wrapper=====================");
		for(Person p: peopleList){
			System.out.println("---> "+p.toString());
			for(Measure m: p.getCurrentHealth()){
				System.out.println("\t--> "+m.toString());
			}
		}
		System.out.println("================wrapper=====================");*/
	}
	
	public ArrayList<Person> getPeopleList()
	{
		return this.peopleList;
	}
	
	
}
