package vn.vdc.nab.alice.voucher.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vn.vdc.nab.alice.util.StringUtils;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {
	private static final String PATTERN = "([0-9 +]+)";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value)) {
			return true;
		}

		boolean isValid = value.matches(PATTERN);

		// if contains "+", must lead with "+"
		if (value.contains("+")) {
			return isValid && value.startsWith("+");
		}

		return isValid;
	}

}
