package introsde.assignment.client;

import introsde.assignment.model.Measure;
import introsde.assignment.model.Person;
import introsde.assignment.soap.People;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class PeopleClient {
	public static void main(String[] args) throws Exception
	{
		URL url = new URL("http://localhost:6905/assignment/lifecoach?wsdl");
		QName qname = new QName("http://soap.assignment.introsde/","PeopleImplService");
		Service service = Service.create(url, qname);
		FileOutputStream fos = new FileOutputStream(new File("output.txt"), true);
		service.setHandlerResolver(new JaxWsHandlerResolver(fos));
		People people = service.getPort(People.class);
		//System.out.println(hello.getHelloWorldAsString("Stefano"));
		
		fos.write("\nREQUEST 1: readPersonList()".getBytes());
		List<Person> request1List = people.getPeople().getPeopleList();
		for(Person p: request1List)
		{
			fos.write(("\n"+p.toString()).getBytes());
			for(Measure m: p.getCurrentHealth()){
				fos.write(("\n\t"+m.toString()).getBytes());
			}
		}
		
		
		fos.write("\nREQUEST 2: readPerson(5)".getBytes());
		Person targetRequest2 = people.getPerson(new Long(5));
		fos.write(("\n"+targetRequest2.toString()).getBytes());
		for(Measure m: targetRequest2.getCurrentHealth()){
				fos.write(("\n\t"+m.toString()).getBytes());
		}
		
		fos.write("\nREQUEST 3: updatePerson(5)".getBytes());
		Person personToUpdate = targetRequest2;
		personToUpdate.setFirstname("Antonio");
		personToUpdate.setLastname("Rossi");
		Person targetRequest3 = people.updatePerson(personToUpdate);
		fos.write(("\n"+targetRequest3.toString()).getBytes());
		for(Measure m: targetRequest3.getCurrentHealth()){
				fos.write(("\n\t"+m.toString()).getBytes());
		}
		
		
		fos.write("\nREQUEST 4: createPerson".getBytes());
		Person personToCreate = new Person();
		personToCreate.setBirthdate(new Date());
		personToCreate.setFirstname("Test");
		personToCreate.setIdPerson(new Long(0));
		personToCreate.setLastname("VIVA");
		Person targetRequest4 = people.createPerson(personToCreate);
		fos.write(("\n"+targetRequest4.toString()).getBytes());
		
		fos.write("\nREQUEST 5: deletePerson(5)".getBytes());
		people.deletePerson(new Long(5));
		
		fos.write("\nREQUEST 1: readPersonList()".getBytes());
		List<Person> request51List = people.getPeople().getPeopleList();
		for(Person p: request51List)
		{
			fos.write(("\n"+p.toString()).getBytes());
		}
		
		fos.write("\nREQUEST 6: readPersonHistory(1, weight)".getBytes());
		List<Measure> readPersonHistory6 = people.showHistory(new Long(1), "weight").getHistoryMeasure();
		for(Measure m: readPersonHistory6)
		{
			fos.write(("\n"+m.toString()).getBytes());
		}
		
		fos.write("\nREQUEST 7: readPersonMeasurement(1, weight, 13)".getBytes());
		List<Measure> readPersonHistory7 = people.readHistoryById(new Long(1), "weight", new Long(13)).getHistoryMeasure();
		for(Measure m: readPersonHistory7)
		{
			fos.write(("\n"+m.toString()).getBytes());
		}
		
		fos.write("\nREQUEST 8: savePersonMeasurement(1, Measure m)".getBytes());
		Measure test = readPersonHistory7.get(0);
		test.setMeasureValue("100");
		test.setIdMeasure(new Long(0));
		people.addMeasure(new Long(1), test);
		
		
		fos.write("\nREQUEST 9: readMeasureTypes() ".getBytes());
		List<String> misure = people.showMeasureTypes().getTypes();
		for(String s: misure){
			fos.write(("\n"+s).getBytes());
		}
		
		
		fos.write("\nEXTRA 3: readPersonMeasureByDates(Long id, String measureType, Date before, Date after) ".getBytes());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Measure> misurel = people.filterHistoryByDatesAndMeasureType(new Long(1), "weight", sdf.parse("2014-01-01"), sdf.parse("2014-12-01")).getHistoryMeasure();
		for(Measure m: misurel){
			fos.write(("\n"+m.toString()).getBytes());
		}
		
		fos.write("\nEXTRA 4: readPersonListByMeasurement(String measureType, String maxValue, String minValue)".getBytes());
		List<Person> personL = people.filterPeopleListByMeasure("weight", new Long(86), new Long(75)).getPeopleList();
		for(Person p: personL){
			fos.write(("\n"+p.toString()).getBytes());
		}
		
		
		
		
		//p.toString();
		
		/*Person person = new Person();
		person.setCurrentHealth(new ArrayList<Measure>());
		person.setHealthHistory(new ArrayList<Measure>());
		person.setFirstname("Jony");
		person.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse("1963-01-01"));
		person.setIdPerson(new Long(0));
		person.setLastname("Ive");
		
		System.out.println(people.createPerson(person));*/
	}
}
