/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import prototype.blacklist.entity.Blacklist;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response.Status;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Ignore;

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
        cut.setEm(em);
    
        assertThat(cut.isGranted("foo", "bar").getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @Ignore
    public void a_known_blacklist_with_an_matching_entry_should_result_in_a_forbidden_reponse() {
        EntityManager em=createEntityManagerMock(Arrays.asList(new Blacklist[]{
            new Blacklist("foo").addEntry("bar")
        }));
        cut.setEm(em);
        
        assertThat(cut.isGranted("foo", "bar").getStatus()).isEqualTo(Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @Ignore
    public void a_known_blacklist_with_an_non_matching_entry_should_result_in_a_empty_result() {
        EntityManager em=createEntityManagerMock(Arrays.asList(new Blacklist[]{
            new Blacklist("foo").addEntry("baz")
        }));
        cut.setEm(em);
        
        assertThat(cut.isGranted("foo", "bar").getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());
    }

}
