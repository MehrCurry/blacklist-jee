/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

/**
 *
 * @author guido
 */
@Entity
@XmlRootElement
public class IbanEntry extends BlacklistEntry  {

    public IbanEntry() {
    }

    IbanEntry(String value) {
        super(null,value);
    }

    
    public IbanEntry(Blacklist blacklist,String value) {
        super(blacklist,value);
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
