package prototype.blacklist.iban;

import static com.jayway.restassured.RestAssured.expect;
import org.junit.Test;
import prototype.blacklist.boundary.Blacklist;

/**
 *
 * @author AN
 */
public class NormalizationBlacklistIntegrationTest {

    private static final String LOCAL_APP_URL = "http://localhost:8080/blacklist-jee7/resources/blacklist";
    
    private static final String BLACKLIST_NAME_IBAN = "iban";
    
    private static final String BLACKLISTED_IBAN_WITHOUT_COUNTRY = "90208110080000001039531801";
    
    private static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

    @Test
    public void expectIbanWithDifferentNotationWillBeNormalized() {
        Blacklist blacklist = new Blacklist(BLACKLIST_NAME_IBAN);
        
        // create the iban blacklist
        expect().
                statusCode(201).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                body(blacklist).
                put(LOCAL_APP_URL);

        // blacklist the iban with small letters
        String[] entries = new String[] {"de"+BLACKLISTED_IBAN_WITHOUT_COUNTRY};
        expect().
                statusCode(201).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                body(entries).
                post(LOCAL_APP_URL + "/" + BLACKLIST_NAME_IBAN);

        // blacklist same iban with upper case letters
        entries = new String[] {"DE"+BLACKLISTED_IBAN_WITHOUT_COUNTRY};
        expect().
                statusCode(201).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                body(entries).
                post(LOCAL_APP_URL + "/" + BLACKLIST_NAME_IBAN);
         
        //check that the iban with whitespace and different letter case is blacklisted
        expect().
                statusCode(200).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                get(LOCAL_APP_URL + "/" + BLACKLIST_NAME_IBAN + "/" + "dE"+BLACKLISTED_IBAN_WITHOUT_COUNTRY );
    }

}
