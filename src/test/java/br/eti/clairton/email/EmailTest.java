package br.eti.clairton.email;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.Validator;

import org.junit.Test;

public class EmailTest {
	private final Validator validator = buildDefaultValidatorFactory().getValidator();

	@Test
	public void testIsValid() {
		Email email = new Email("clairton.rodrigo@gmail.com");
		assertTrue(validator.validate(email).isEmpty());
	}

	@Test
	public void testIsInvalidWithoutDomain() {
		Email email = new Email("clairton.heinzen");
		assertFalse(validator.validate(email).isEmpty());
	}

	@Test
	public void testIsInvalidWithoutUser() {
		Email email = new Email("@maxicredito.coop");
		assertFalse(validator.validate(email).isEmpty());
	}

	@Test
	public void testIsInvalidWithoutDotInDomain() {
		Email email = new Email("clairton@maxicredito");
		assertFalse(validator.validate(email).isEmpty());
	}

	@Test
	public void testIsInvalidWithoutDotArroba() {
		Email email = new Email("clairtonmaxicredito.coop");
		assertFalse(validator.validate(email).isEmpty());
	}
}
