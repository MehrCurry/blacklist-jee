/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import net.jcip.annotations.NotThreadSafe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import prototype.blacklist.categories.IntegrationTest;
import prototype.blacklist.categories.LongRunning;
import prototype.blacklist.control.BlacklistRepository;
import prototype.blacklist.entity.Blacklist;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@Category({IntegrationTest.class, LongRunning.class})
@NotThreadSafe
public class PermissionResourceTest {
  private PermissionResource cut;

  @Before
  public void setUp() {
    cut = new PermissionResource();
  }

  @After
  public void tearDown() {
  }

  @Test
  public void an_unknown_blacklist_should_give_a_bad_request_response() {
    HttpServletResponse resp = mock(HttpServletResponse.class);

    cut.setRepository(createRepositoryMock(Collections.EMPTY_LIST));

    cut.isGranted("foo", "bar", resp);
    verify(resp).setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  private BlacklistRepository createRepositoryMock(List results) {
    BlacklistRepository repository = mock(BlacklistRepository.class);
    when(repository.findByName(Matchers.anyString())).thenReturn(results);
    return repository;
  }

  @Test
  public void a_known_blacklist_with_an_matching_entry_should_result_in_a_forbidden_reponse() {
    HttpServletResponse resp = mock(HttpServletResponse.class);

    cut.setRepository(createRepositoryMock(Arrays.asList(new Blacklist("foo").addEntry("bar"))));

    cut.isGranted("foo", "bar", resp);
    verify(resp).setStatus(HttpServletResponse.SC_FORBIDDEN);
  }

  @Test
  public void a_known_blacklist_with_an_non_matching_entry_should_result_in_a_empty_result() {
    HttpServletResponse resp = mock(HttpServletResponse.class);

    cut.setRepository(createRepositoryMock(Arrays.asList(new Blacklist("foo").addEntry("baz"))));

    cut.isGranted("foo", "bar", resp);
    verify(resp).setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

}
