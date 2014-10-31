/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import javax.persistence.Entity;

/**
 *
 * @author guido
 */
@Entity
public class GenericEntry extends BlacklistEntry {

    public GenericEntry() {
    }

    public GenericEntry(Blacklist blacklist,String value) {
        super(blacklist,value);
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
