package prototype.blacklist.normalize;

/**
 * Normalized IBAN has upper case letters and no whitespace left or right.
 * 
 * @author AN
 */
public class IBANNormalizer implements Normalizer {

    @Override
    public String normalize(String value) {
        return value.toUpperCase().trim();
    }
    
}
