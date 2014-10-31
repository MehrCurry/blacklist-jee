/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;


import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BlacklistEntry.findAll", query = "SELECT ble FROM BlacklistEntry ble"),
    @NamedQuery(name = "BlacklistEntry.findByValue", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.value = :value")
})
public abstract class BlacklistEntry extends AbstractEntity {        
    @Size(max = 40)
    @NotNull
    protected String value;
    
    public BlacklistEntry() {
    }

    public BlacklistEntry(Blacklist blacklist,String value) {
        this.value = normalize(value);
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
        
    public abstract boolean matches(String other);
    
    protected abstract String normalize(String aValue);
}
