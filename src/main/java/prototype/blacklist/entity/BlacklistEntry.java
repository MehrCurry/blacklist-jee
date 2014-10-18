/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rabe
 */
@Entity
@Table(name = "BLACKLISTENTRY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BlacklistEntry.findAll", query = "SELECT ble FROM BlacklistEntry ble"),
    @NamedQuery(name = "BlacklistEntry.findBlacklistEntryId", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.blacklistEntryId = :blacklistEntryId"),
    @NamedQuery(name = "BlacklistEntry.findByType", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.type = :type"),
    @NamedQuery(name = "BlacklistEntry.findByValue", query = "SELECT ble FROM BlacklistEntry ble WHERE ble.value = :value")})
public class BlacklistEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "BLACKLISTENTRY_ID")
    private Long blacklistEntryId;
    
    @Size(max = 30)
    @Column(name = "TYPE")
    private String type;
    
    @Size(max = 10)
    @Column(name = "VALUE")
    private String value;
    
    @Version
    private Long version;
    

    public BlacklistEntry() {
    }

    public Long getBlacklistEntryId() {
        return blacklistEntryId;
    }

    public void setBlacklistEntryId(Long blacklistEntryId) {
        this.blacklistEntryId = blacklistEntryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }




    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blacklistEntryId != null ? blacklistEntryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlacklistEntry)) {
            return false;
        }
        BlacklistEntry other = (BlacklistEntry) object;
        if ((this.blacklistEntryId == null && other.blacklistEntryId != null) || (this.blacklistEntryId != null && !this.blacklistEntryId.equals(other.blacklistEntryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prototype.blacklist.boundary.BlacklistEntry[ blacklistEntryId=" + blacklistEntryId + " ]";
    }
    
}
