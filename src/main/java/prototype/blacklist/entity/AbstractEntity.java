/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.entity;

import javax.persistence.*;
import javax.validation.*;
import java.io.Serializable;
import java.util.Set;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    protected static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;
    
    @Version
    protected Long version;

    public AbstractEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public Set<ConstraintViolation<AbstractEntity>> getErrors() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AbstractEntity>> errors = validator.validate(this);
        return errors;
    }

    @Transient
    public boolean isValid() {
        return getErrors().size() == 0;
    }

    @PrePersist
    @PreUpdate
    public void validate() {
        Set<ConstraintViolation<AbstractEntity>> errors = getErrors();
        if (errors.size() > 0) {
            throw new ConstraintViolationException(errors);
        }
    }
}
