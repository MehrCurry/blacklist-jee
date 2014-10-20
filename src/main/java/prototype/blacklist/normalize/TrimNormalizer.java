package prototype.blacklist.normalize;

/**
 *
 * @author AN
 */
public class TrimNormalizer implements Normalizer {

    @Override
    public String normalize(String value) {
        return value.trim();
    }
    
}
