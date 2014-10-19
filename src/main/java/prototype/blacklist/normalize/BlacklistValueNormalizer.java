package prototype.blacklist.normalize;

import javax.ejb.Stateless;
import prototype.blacklist.entity.BlacklistEntry;

/**
 * Normalize blacklist values depending on tpye of list.
 * 
 * @author AN
 */
@Stateless
public class BlacklistValueNormalizer {
    
    public void normalize(final BlacklistEntry entry) {
        final String type = entry.getType();
        final Normalizer normalizer = createNormalizerByType(type);
        entry.setValue(normalizer.normalize(entry.getValue()));
    }
    
    public Normalizer createNormalizerByType(final String type) {
        switch (type) {
            case "iban":
                    return new IBANNormalizer();
            default:
                    return new TrimNormalizer();
        }
    }
}
