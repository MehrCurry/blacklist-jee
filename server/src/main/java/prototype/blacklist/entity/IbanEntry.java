/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author guido
 */
@Entity
@XmlRootElement
public class IbanEntry extends BlacklistEntry {

  public IbanEntry() {
  }

  public IbanEntry(String value) {
    super(value);
  }

  @Override
  public boolean matches(String other) {
    return value.equals(normalize(other));
  }

  @Override
  public String normalize(String value) {
    return value.replaceAll("\\W", "").toUpperCase();
  }

  @Override
  public boolean isValid() {
    return super.isValid() && new IBANCheckDigit().isValid(value);
  }
}
