/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.iban;

import org.junit.Before;
import org.junit.Test;
import prototype.blacklist.boundary.Blacklist;
import static com.jayway.restassured.RestAssured.*;

/**
 *
 * @author michael
 */
public class IBanBlackListIntegrationTest {

    private static final String LOCAL_APP_URL = "http://localhost:8080/blacklist-jee7/resources/blacklist";
    
    private static final String BLACKLIST_NAME_IBANS = "ibans";
    
    private static final String BLACKLISTED_IBAN = "AL90208110080000001039531801";
    
    private static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";
    
    
    public IBanBlackListIntegrationTest() {
    }

    @Before
    public void setUp() {
    }

    
    @Test
    public void expectIbanIsBlacklisted() {
        Blacklist blacklist = new Blacklist(BLACKLIST_NAME_IBANS);
        
        // create the iban blacklist
        expect().
                statusCode(201).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                body(blacklist).
                put(LOCAL_APP_URL);

        // blacklist an iban
        String[] entries = new String[] {BLACKLISTED_IBAN};
        expect().
                statusCode(201).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                body(entries).
                post(LOCAL_APP_URL + "/" + BLACKLIST_NAME_IBANS);

        // **********************************************************
        // so finally we check that the expected iban is blacklisted
        // **********************************************************
        expect().
                statusCode(200).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                get(LOCAL_APP_URL + "/" + BLACKLIST_NAME_IBANS + "/" + BLACKLISTED_IBAN);

    }
    
    @Test
    public void expectIbanIsNotBlacklisted() {
        Blacklist blacklist = new Blacklist(BLACKLIST_NAME_IBANS);
        
        // create the iban blacklist
        expect().
                statusCode(201).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                body(blacklist).
                put(LOCAL_APP_URL);

        // **********************************************************
        // so finally we check that the expected iban is blacklisted
        // **********************************************************
        expect().
                statusCode(204).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                get(LOCAL_APP_URL + "/" + BLACKLIST_NAME_IBANS + "/XXXXXXX");

    }
    
    @Test
    public void expectIllegalIbanIsDeclined() {
        Blacklist blacklist = new Blacklist(BLACKLIST_NAME_IBANS);
        
        // create the iban blacklist
        expect().
                statusCode(201).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                body(blacklist).
                put(LOCAL_APP_URL);

        // blacklist an iban
        String[] entries = new String[] {"lk_"};
        expect().
                statusCode(400).
                when().
                given().
                contentType(DEFAULT_CONTENT_TYPE).
                body(entries).
                post(LOCAL_APP_URL + "/" + BLACKLIST_NAME_IBANS);

    }
    
}
