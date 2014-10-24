/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author guido
 */
@Entity
public class Blacklist extends AbstractEntity {
    @Size(max = 30)
    @NotNull
    private String name;
    
    private List<BlacklistEntry> entries=new ArrayList<>();
    
    public void addEntry(String value) {
        entries.add(new GenericEntry(value));
    }
}
