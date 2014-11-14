package prototype.blacklist.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Guido.Zockoll
 */
public class BlacklistEntryTest {

  @Test
  public void an_empty_instance_must_not_be_valid() {
    BlacklistEntry cut = new BlacklistEntry() {

      @Override
      public boolean matches(String other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      @Override
      protected String normalize(String aValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }
    };

    assertThat(cut.isValid()).isFalse();
    assertThat(cut.getErrors().size()).isGreaterThan(0);
  }

  @Test
  public void an_instance_with_value_is_valid() {
    BlacklistEntry cut = new BlacklistEntry("bar") {

      @Override
      public boolean matches(String other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      @Override
      protected String normalize(String aValue) {
        return aValue;
      }
    };

    assertThat(cut.isValid()).isTrue();
    assertThat(cut.getErrors().size()).isEqualTo(0);
  }

  @Test
  public void an_instance_with_a_too_long_value_must_be_invalid() {
    BlacklistEntry cut = new BlacklistEntry("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa") {

      @Override
      public boolean matches(String other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      @Override
      protected String normalize(String aValue) {
        return aValue;
      }
    };

    assertThat(cut.isValid()).isFalse();
    assertThat(cut.getErrors().size()).isGreaterThan(0);
  }

}
