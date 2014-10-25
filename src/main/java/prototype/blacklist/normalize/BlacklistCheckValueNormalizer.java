package prototype.blacklist.normalize;

import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;

import prototype.blacklist.boundary.BlacklistCheck;
import prototype.blacklist.entity.BlacklistEntry;

/**
 * Normalize blacklist check parameter values depending on the type of the parameter.
 */
@Stateless
public class BlacklistCheckValueNormalizer {
    
    public void normalize(final BlacklistCheck check) {
    	Map<String, String> parametersToCheck = check.getParametersToCheck();
        for(Entry<String,String> checkEnty : parametersToCheck.entrySet()){
        	Normalizer normalizer = createNormalizerByName(checkEnty.getKey());
        	String value = normalizer.normalize(checkEnty.getValue());
        	parametersToCheck.put(checkEnty.getKey(), value);
        }
    }
    
    public Normalizer createNormalizerByName(final String name) {
        switch (name) {
            case "iban":
                    return new IBANNormalizer();
            default:
                    return new TrimNormalizer();
        }
    }
}
