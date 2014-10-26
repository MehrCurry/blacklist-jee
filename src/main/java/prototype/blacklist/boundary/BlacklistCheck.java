package prototype.blacklist.boundary;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import prototype.blacklist.entity.BlacklistEntry;

public class BlacklistCheck {

	@XmlElement(required=true)
	private List<BlacklistEntry> parametersToCheck;
	
	public BlacklistCheck() {		
	}
	
	public List<BlacklistEntry> getParametersToCheck() {
		return parametersToCheck;
	}
}
