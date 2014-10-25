package prototype.blacklist.boundary;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

public class BlacklistCheck {

	@XmlElement(required=true)
	private Map<String,String> parametersToCheck;
	
	public BlacklistCheck() {		
	}
	
	public Map<String, String> getParametersToCheck() {
		return parametersToCheck;
	}
}
