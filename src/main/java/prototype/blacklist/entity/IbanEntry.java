/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import javax.persistence.Entity;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

/**
 *
 * @author guido
 */
@Entity
public class IbanEntry extends BlacklistEntry  {

    public IbanEntry(String value) {
        super(IbanEntry.normalize(value));
    }

    @Override
    public boolean matches(String other) {
        return value.equals(IbanEntry.normalize(other));
    }

    @Override
    public void normalize() {
        value=IbanEntry.normalize(value);
    }
    
    public static String normalize(String value) {
        return value.trim().toUpperCase();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && new IBANCheckDigit().isValid(value);
    }
    
    
}
