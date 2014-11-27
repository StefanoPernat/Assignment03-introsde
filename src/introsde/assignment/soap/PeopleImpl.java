package introsde.assignment.soap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import introsde.assignment.dao.LifeCoachDao;
import introsde.assignment.model.Measure;
import introsde.assignment.model.Person;
import introsde.assignment.wrapper.HistoryListWrapper;
import introsde.assignment.wrapper.PeopleListWrapper;
import introsde.assignment.wrapper.TypesWrapper;

import javax.jws.WebService;
import javax.persistence.EntityTransaction;

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
		else {
			System.out.println("--> "+target.toString());
			for(Measure m: target.getCurrentHealth())
				System.out.println(m.toString());
		}
		return target;
	}

	@Override
	public Person updatePerson(Person target) {
		System.out.println("--> REQUESTED: updatePerson(p)");
		System.out.println("--> "+target.toString());
		return Person.updatePerson(target);
	}

	@Override
	public Person createPerson(Person target) {
		System.out.println("--> REQUESTED: createPerson("+target.toString()+")");
		System.out.println("--> "+target.toString());
		System.out.println(target.hasCurrentHealth());
		/*for(Measure m: target.currentHealth){
			System.out.println("\t--> "+m.toString());
		}*/
		return Person.createPerson(target);
	}

	@Override
	public HistoryListWrapper showHistory(Long id, String measureType) {
		System.out.println("--> REQUESTED: showHistory("+id.longValue()+","+measureType+")");
		HistoryListWrapper historywrapper = new HistoryListWrapper();
		historywrapper.setHistoryMeasure(Measure.getHistoryByName(id, measureType));
		if(historywrapper.getHistoryMeasure().size() > 0){
			System.out.println("--> Found some history");
		}
		else{
			System.out.println("--> No history");
		}
		return historywrapper;
		//return new HistoryListWrapper();
	}

	@Override
	public HistoryListWrapper readHistoryById(Long id, String measureType, Long mid) {
		System.out.println("--> REQUESTED: readHistoryById("+id.longValue()+","+measureType+","+mid.longValue()+")");
		ArrayList<Measure> measureListed = new ArrayList<Measure>();
		measureListed.add(Measure.getHistoryMeasure(id, measureType, mid));
		HistoryListWrapper historyWrapper = new HistoryListWrapper();
		historyWrapper.setHistoryMeasure(measureListed);
		if(historyWrapper.getHistoryMeasure().size() > 0){
			System.out.println("Found that measure");
		}
		else{
			System.out.println("No measure...");
		}
		return historyWrapper;
	}

	@Override
	public Long addMeasure(Long id, Measure measure) {
		System.out.println("--> REQUESTED: addMeasure("+id.longValue()+","+measure.toString()+")");
		Long result = Measure.addMeasure(id, measure);
		Person resultPerson = Person.getOne(id);
		resultPerson.refreshMeasure();
		return result;
	}

	@Override
	public TypesWrapper showMeasureTypes() {
		TypesWrapper measureTypesW = new TypesWrapper();
		measureTypesW.setTypes(Measure.getTypes());
		return measureTypesW;
	}

	@Override
	public HistoryListWrapper filterHistoryByDatesAndMeasureType(Long id, String measureType, Date after, Date before) {
		System.out.println("--> REQUESTED: filterHistoryByDate("+id.longValue()+","+measureType+","+after+","+before+")");
		ArrayList<Measure> measureListed = new ArrayList<Measure>();
		measureListed.addAll(Measure.getFilterByDatesHistory(id, measureType, after, before));
		HistoryListWrapper historyWrapper = new HistoryListWrapper();
		historyWrapper.setHistoryMeasure(measureListed);
		if(historyWrapper.getHistoryMeasure().size() > 0){
			System.out.println("--> Found some measure");
		}
		else{
			System.out.println("--> No measure...");
		}
		return historyWrapper;
		
	}

	@Override
	public PeopleListWrapper filterPeopleListByMeasure(String measureType, Long max, Long min) {
		System.out.println("--> REQUESTED: filterPeopleListByMeasure("+measureType+","+max+","+min+")");
		List<Person> result = new ArrayList<Person>();
		for(Long id: Measure.getFilteredIdByMeasure(measureType, min, max)){
			result.add(Person.getOne(id));
		}
		PeopleListWrapper resultW = new PeopleListWrapper();
		resultW.setPeopleList(result);
		return resultW;
	}

	@Override
	public String deletePerson(Long id) {
		System.out.println("REQUESTED: deletePerson("+id.longValue()+")");
		String result = "";
		Person target = Person.getOne(id);
		if(target != null)
		{
			EntityTransaction tx = LifeCoachDao.instance.getEntityManager().getTransaction();
			tx.begin();
			target = LifeCoachDao.instance.getEntityManager().merge(target);
			LifeCoachDao.instance.getEntityManager().remove(target);
			tx.commit();
			result = "Person with id:"+id.longValue()+" deleted";
			return result;
			
		}
		else{
			result = "Person with id: "+id.longValue()+" not found!";
			return result;
		}
			
	}
}
