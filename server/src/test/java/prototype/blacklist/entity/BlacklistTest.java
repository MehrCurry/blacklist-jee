/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlacklistTest {

  @Test
  public void testEmpty() {
    Blacklist cut = new Blacklist();
    assertThat(cut.isValid()).isFalse();
  }

  @Test
  public void testAddGenericEntry() {
    Blacklist cut = new Blacklist("JUnit");

    cut.addEntry("This is not an IBAN");
    assertThat(cut.getGenericEentries().size()).isEqualTo(1);
    assertThat(cut.getIbanEentries().size()).isEqualTo(0);

    assertThat(cut.isBlacklisted("Test")).isFalse();
    assertThat(cut.isBlacklisted("This is not an IBAN")).isTrue();
  }

  @Test
  public void testAddIbanEntry() {
    Blacklist cut = new Blacklist("JUnit");

    cut.addEntry("DE12500105170648489890");
    assertThat(cut.getGenericEentries().size()).isEqualTo(0);
    assertThat(cut.getIbanEentries().size()).isEqualTo(1);

    assertThat(cut.isBlacklisted("Test")).isFalse();
    assertThat(cut.isBlacklisted("DE12500105170648489890")).isTrue();
  }

}
