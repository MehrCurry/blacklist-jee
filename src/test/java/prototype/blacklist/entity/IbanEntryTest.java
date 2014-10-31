/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class IbanEntryTest {
    
    @Test
    public void testNormalize() {
        IbanEntry entry=new IbanEntry("DE12500105170648489890");
        
        assertThat(entry.getValue()).isEqualTo("DE12500105170648489890");
        
        entry=new IbanEntry("   DE12500105170648489890");
        assertThat(entry.getValue()).isEqualTo("DE12500105170648489890");

        entry=new IbanEntry("DE12500105170648489890         ");
        assertThat(entry.getValue()).isEqualTo("DE12500105170648489890");

        entry=new IbanEntry("de12500105170648489890");
        assertThat(entry.getValue()).isEqualTo("DE12500105170648489890");

    }    
}
