/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.istack.NotNull;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import lombok.Data;

/**
 *
 * @author Rabe
 */
@ValidBlacklistEntry
@Entity
@Table(name = "BLACKLISTENTRY")
@XmlRootElement
@Data
@NamedQueries({
    @NamedQuery(name = "BlacklistEntry.findAll", query = "SELECT ble FROM BlacklistEntry ble"),
    @NamedQuery(name = "BlacklistEntry.findBlacklistEntryId", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.id = :blacklistEntryId"),
    @NamedQuery(name = "BlacklistEntry.findByType", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.type = :type"),
    @NamedQuery(name = "BlacklistEntry.findByValue", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.value = :value"),
    @NamedQuery(name = "BlacklistEntry.findByTypeAndValue", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.type = :type AND ble.value = :value")})
public class BlacklistEntry extends AbstractEntity {
    @Inject
    private Validator validator;
    
    @Size(max = 30)
    @NotNull
    @Column(name = "TYPE")
    private String type;
    
    @Size(max = 40)
    @NotNull
    @Column(name = "VALUE")
    private String value;
    
    @PrePersist
    public boolean isValid() {
    	return validator.isValid(this);
    }
}
