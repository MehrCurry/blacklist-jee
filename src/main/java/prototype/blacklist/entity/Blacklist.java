/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

@XmlRootElement
@NamedQuery(name = "hurz", query = "SELECT bl FROM Blacklist bl WHERE bl.name = :name")
@Entity
public class Blacklist extends AbstractEntity {
    @Size(max = 30)
    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "blacklist_id")
    private List<GenericEntry> genericEentries=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "blacklist_id")
    private List<IbanEntry> ibanEentries=new ArrayList<>();

    public Blacklist() {
    }

    public Blacklist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    
    // Default visibility for unit tests
    // Do not use outside of tests
    List<GenericEntry> getGenericEentries() {
        return Collections.unmodifiableList(genericEentries);
    }

    List<IbanEntry> getIbanEentries() {
        return Collections.unmodifiableList(ibanEentries);
    }
    
    public Blacklist addEntry(String value) {
        // This is a bit ugly since GenericEntry is a bit ugly too
        // If we have more meaningful blacklist entry type we should use a factory
        IbanEntry entry=new IbanEntry(value);
        if (entry.isValid()) {
            ibanEentries.add(entry);
        } else {
            genericEentries.add(new GenericEntry(this,value));
        }
        return this;
    }

    public boolean isBlacklisted(String value) {
        return matches(genericEentries, value) || matches(ibanEentries, value);
    }
    
    private boolean matches(List<? extends BlacklistEntry> alist,String aValue) {
        // return !alist.stream().filter(e -> e.matches(aValue)).collect(Collectors.<BlacklistEntry>toList()).isEmpty();
        return !alist.stream().filter(new MatcherPredicate(aValue)).collect(Collectors.<BlacklistEntry>toList()).isEmpty();
        // return false;    
    }

    private boolean isAnIban(String value) {
        return new IBANCheckDigit().isValid(value);                
    }
    
    private static class MatcherPredicate implements Predicate<BlacklistEntry> {
        private String value;

        public MatcherPredicate(String value) {
            this.value = value;
        }
        
        @Override
        public boolean test(BlacklistEntry t) {
            return t.matches(value);
        }
    }
}
