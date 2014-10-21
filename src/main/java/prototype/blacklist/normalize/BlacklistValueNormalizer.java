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
        final String name = entry.getName();
        final Normalizer normalizer = createNormalizerByName(name);
        entry.setValue(normalizer.normalize(entry.getValue()));
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
