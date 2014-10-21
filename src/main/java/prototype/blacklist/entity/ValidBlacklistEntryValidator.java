package prototype.blacklist.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

public class ValidBlacklistEntryValidator implements ConstraintValidator<ValidBlacklistEntry, BlacklistEntry> {

	@Override
	public void initialize(ValidBlacklistEntry constraintAnnotation) {
	}

	@Override
	public boolean isValid(BlacklistEntry entry,
			ConstraintValidatorContext context) {
		return entry.isValid() && createValidator(entry.getName()).isValid(entry.getValue());
	}

	private Validator<String> createValidator(String name) {
		switch (name) {
		case "iban":
			return new Validator<String>() {
				@Override
				public boolean isValid(String iban) {
					return new IBANCheckDigit().isValid(iban);
				}
			};
		default:
			return new Validator<String>() {
				@Override
				public boolean isValid(String object) {
					return true;
				}
			};
		}
	}
	
}
