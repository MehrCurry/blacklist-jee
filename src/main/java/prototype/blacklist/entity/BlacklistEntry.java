/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;


import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rabe
 */
@ValidBlacklistEntry
@Entity
@Table(name = "BLACKLISTENTRY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BlacklistEntry.findAll", query = "SELECT ble FROM BlacklistEntry ble"),
    @NamedQuery(name = "BlacklistEntry.findBlacklistEntryId", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.id = :blacklistEntryId"),
    @NamedQuery(name = "BlacklistEntry.findByName", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.name = :name"),
    @NamedQuery(name = "BlacklistEntry.findByValue", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.value = :value"),
    @NamedQuery(name = "BlacklistEntry.findByNameAndValue", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.name = :name AND ble.value = :value")})
public class BlacklistEntry extends AbstractEntity {    
    @Size(max = 30)
    @NotNull
    private String name;
    
    @Size(max = 40)
    @NotNull
    private String value;

    public BlacklistEntry() {
    }

    public BlacklistEntry(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    
    public boolean isValid() {
        return name != null && value != null;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
