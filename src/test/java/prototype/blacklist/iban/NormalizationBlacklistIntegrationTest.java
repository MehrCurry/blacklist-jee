package prototype.blacklist.iban;

import prototype.blacklist.ApplicationURL;
import static com.jayway.restassured.RestAssured.expect;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import prototype.blacklist.boundary.Blacklist;

/**
 *
 * @author AN
 */
public class NormalizationBlacklistIntegrationTest {

    private static final String BLACKLIST_NAME_IBAN = "iban";
    
    private static final String BLACKLISTED_IBAN_WITHOUT_COUNTRY = "90208110080000001039531801";
    
    @Test
    public void expectIbanWithDifferentNotationWillBeNormalized() {
        Blacklist blacklist = new Blacklist(BLACKLIST_NAME_IBAN);
        
        // create the iban blacklist
        expect().
                statusCode(201).
                when().
                given().
                contentType(MediaType.APPLICATION_JSON).
                body(blacklist).
                put(ApplicationURL.LOCAL.getAppURL());

        // blacklist the iban with small letters
        String[] entries = new String[] {"de"+BLACKLISTED_IBAN_WITHOUT_COUNTRY};
        expect().
                statusCode(201).
                when().
                given().
                contentType(MediaType.APPLICATION_JSON).
                body(entries).
                post(ApplicationURL.LOCAL.getAppURL() + "/" + BLACKLIST_NAME_IBAN);

        // blacklist same iban with upper case letters
        entries = new String[] {"DE"+BLACKLISTED_IBAN_WITHOUT_COUNTRY};
        expect().
                statusCode(201).
                when().
                given().
                contentType(MediaType.APPLICATION_JSON).
                body(entries).
                post(ApplicationURL.LOCAL.getAppURL() + "/" + BLACKLIST_NAME_IBAN);
         
        //check that the iban with whitespace and different letter case is blacklisted
        expect().
                statusCode(200).
                when().
                given().
                contentType(MediaType.APPLICATION_JSON).
                get(ApplicationURL.LOCAL.getAppURL() + "/" + BLACKLIST_NAME_IBAN + "/" + "dE"+BLACKLISTED_IBAN_WITHOUT_COUNTRY );
    }

}
