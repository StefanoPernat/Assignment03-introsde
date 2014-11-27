package introsde.assignment.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="measureTypes")
@XmlAccessorType(XmlAccessType.FIELD)
public class TypesWrapper {

	@XmlElement(name="measureType")
	private List<String> types;

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> sometypes) {
		this.types = new ArrayList<String>();
		this.types.addAll(sometypes);
	}
	
	
}
