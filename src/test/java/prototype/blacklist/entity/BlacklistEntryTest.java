package prototype.blacklist.entity;


import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Guido.Zockoll
 */
public class BlacklistEntryTest {
private static Validator validator;

   @BeforeClass
   public static void setUp() {
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      validator = factory.getValidator();
   }
   
    @Test
    public void an_empty_instance_must_not_be_valid() {
        BlacklistEntry cut=new BlacklistEntry();
        final Set<ConstraintViolation<BlacklistEntry>> violations = validator.validate(cut);
        assertThat(violations.size()).isGreaterThan(0)
    }

    @Test
    public void an_instance_with_name_and_value_is_valid() {
        BlacklistEntry cut=new BlacklistEntry("foo","bar");
        final Set<ConstraintViolation<BlacklistEntry>> violations = validator.validate(cut);
        assertThat(violations.size()).isEqualTo(0);
    }
}
