/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guido
 */
@Entity
@XmlRootElement
public class GenericEntry extends BlacklistEntry {

    public GenericEntry() {
    }

  public GenericEntry(String value) {
    super(value);
    }

    @Override
    public boolean matches(String other) {
        return value.equals(other);
    }

    @Override
    protected String normalize(String aValue) {
        return aValue;
    }
}
