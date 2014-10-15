package prototype.blacklist.boundary;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Blacklist {

	private String name;	
	
	@XmlElement(required=false)
	private Set<String> listedElements;

	public Blacklist() {	
		this.listedElements = new HashSet<String>();
	}
	
	public Blacklist(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;		
	}

	public Set<String> getListedElements() {		
		return listedElements;
	}	
}
