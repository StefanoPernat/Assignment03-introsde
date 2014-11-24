package introsde.assignment.endpoint;

import introsde.assignment.soap.PeopleImpl;

import javax.xml.ws.Endpoint;

public class PeoplePublisher {

	public static final String SERVER_URL = "http://localhost";
	public static final String PORT = "6905";
	public static final String BASE_URL = "/assignment/lifecoach";
	
	public static String getEndpointUrl(){
		return PeoplePublisher.SERVER_URL+":"+PeoplePublisher.PORT+PeoplePublisher.BASE_URL;
	}
	
	public static void main(String[] args) throws Exception{
		String endpoint = getEndpointUrl();
		System.out.println("--> starting LifeCoach at \""+endpoint+"\"");
		Endpoint.publish(endpoint, new PeopleImpl());
	}
}
