/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.*;
import org.mockito.Matchers;

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
    public void a_known_type_without_entries_should_result_in_an_no_content_reponse() {
        EntityManager em=createEntityManagerMock(Collections.EMPTY_LIST);
        cut.setEm(em);
    
        assertThat(cut.isGranted("foo", "bar").getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());

    }

    @Test
    public void a_known_type_with_an_matching_entries_should_result_in_a_forbidden_reponse() {
        EntityManager em=createEntityManagerMock(Arrays.asList(new String[]{"xyz","bar"}));
        cut.setEm(em);
        
        assertThat(cut.isGranted("foo", "bar").getStatus()).isEqualTo(Status.FORBIDDEN.getStatusCode());
    }

}
