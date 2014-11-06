/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import prototype.blacklist.entity.Blacklist;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PermissionResourceTest {
    private PermissionResource cut;
    
    @Before
    public void setUp() {
        cut=new PermissionResource();
    }
    
    @After
    public void tearDown() {
    }

    private EntityManager createEntityManagerMock(List result) {
        Query query=Mockito.mock(Query.class);
        Mockito.when(query.getResultList()).thenReturn(result);
        EntityManager em=Mockito.mock(EntityManager.class);
        Mockito.when(em.createNamedQuery(Matchers.anyString())).thenReturn(query);
        return em;
    }

    @Test
    @Ignore
    public void an_unknown_blacklist_should_give_a_bad_request_response() {
        EntityManager em=createEntityManagerMock(Collections.EMPTY_LIST);

        // assertThat(cut.isGranted("foo", "bar").getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @Ignore
    public void a_known_blacklist_with_an_matching_entry_should_result_in_a_forbidden_reponse() {
        EntityManager em = createEntityManagerMock(Arrays.asList(new Blacklist("foo").addEntry("bar")));

        //assertThat(cut.isGranted("foo", "bar").getStatus()).isEqualTo(Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @Ignore
    public void a_known_blacklist_with_an_non_matching_entry_should_result_in_a_empty_result() {
        EntityManager em = createEntityManagerMock(Arrays.asList(new Blacklist("foo").addEntry("baz")));

        // assertThat(cut.isGranted("foo", "bar").getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());
    }

}
