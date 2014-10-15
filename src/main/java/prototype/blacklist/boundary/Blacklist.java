package prototype.blacklist.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Blacklist {

	private String name;	
	
	@XmlElement(required=false)
	private List<String> listedElements;

	public Blacklist() {	
		this.listedElements = new ArrayList<String>();
	}
	
	public Blacklist(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;		
	}

	public List<String> getListedElements() {		
		return listedElements;
	}	
}
