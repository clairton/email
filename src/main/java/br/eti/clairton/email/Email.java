package br.eti.clairton.email;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.persistence.AttributeConverter;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import br.eti.clairton.email.Email.Pattern;

@Pattern
public class Email implements CharSequence, Serializable {
	private static final long serialVersionUID = 1L;

	private final String address;

	public Email(final String address) {
		this.address = address;
	}

	@Override
	public int length() {
		return address.length();
	}

	@Override
	public char charAt(int index) {
		return address.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return address.subSequence(start, end);
	}

	@Override
	public String toString() {
		return address;
	}

	@Override
	public int hashCode() {
		return address.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return address.equals(obj);
	}

	@javax.persistence.Converter(autoApply = true)
	public static class Converter implements AttributeConverter<Email, String> {

		@Override
		public String convertToDatabaseColumn(Email attribute) {
			return attribute.toString();
		}

		@Override
		public Email convertToEntityAttribute(String dbData) {
			return new Email(dbData);
		}

	}

	@Target(TYPE)
	@Retention(RUNTIME)
	@Documented
	@Constraint(validatedBy = { PatternValidate.class })
	public static @interface Pattern {

		String message() default "{br.eti.clairton.email.Email.Pattern.message}";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
	}

	public static class PatternValidate implements ConstraintValidator<Pattern, Email> {
		// regex from http://emailregex.com/
		private final static String regex = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

		@Override
		public void initialize(Pattern annotation) {

		}

		@Override
		public boolean isValid(Email value, ConstraintValidatorContext context) {
			return value.toString().matches(regex);
		}

	}
}
