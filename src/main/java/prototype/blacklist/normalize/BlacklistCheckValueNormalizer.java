package prototype.blacklist.normalize;

import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.inject.Inject;

import prototype.blacklist.boundary.BlacklistCheck;
import prototype.blacklist.entity.BlacklistEntry;

/**
 * Normalize blacklist check parameter values depending on the type of the parameter.
 */
@Stateless
public class BlacklistCheckValueNormalizer {
    
	@Inject
	BlacklistValueNormalizer normalizer;
	
    public void normalize(final BlacklistCheck check) {
    	Map<String, String> parametersToCheck = check.getParametersToCheck();
        for(Entry<String,String> checkEnty : parametersToCheck.entrySet()){
        	Normalizer specificNormalzer = normalizer.createNormalizerByName(checkEnty.getKey());
        	String value = specificNormalzer.normalize(checkEnty.getValue());
        	parametersToCheck.put(checkEnty.getKey(), value);
        }
    }
}
