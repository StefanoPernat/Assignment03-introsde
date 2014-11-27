package introsde.assignment.wrapper;

import java.util.ArrayList;
import java.util.List;

import introsde.assignment.model.Measure;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="healthProfile-history")
public class HistoryListWrapper {

	@XmlElement(name="measure")
	private ArrayList<Measure> historyMeasure;

	public ArrayList<Measure> getHistoryMeasure() {
		return historyMeasure;
	}

	public void setHistoryMeasure(List<Measure> historyMeasure) {
		this.historyMeasure = new ArrayList<Measure>();
		this.historyMeasure.addAll(historyMeasure);
	}
	
	
}
