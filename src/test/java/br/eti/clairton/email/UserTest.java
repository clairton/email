package br.eti.clairton.email;

import static javax.persistence.Persistence.createEntityManagerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {

	private static EntityManager manager;

	@BeforeClass
	public static void init() {
		manager = createEntityManagerFactory("default").createEntityManager();
	}

	@Test
	public void testValid() {
		manager.getTransaction().begin();
		manager.persist(new User("clairton", new Email("clairton.rodrigo@gmail.com")));
		try {
			manager.flush();
			manager.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			manager.getTransaction().rollback();
		}
	}

	@Test
	public void testInvalid() {
		manager.getTransaction().begin();
		try {
			manager.persist(new User("clairton", new Email("clairton.rodrigo")));
			manager.flush();
			fail("Must Be Invalid");
		} catch (ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			assertEquals("emails[0]", violation.getPropertyPath().toString());
			manager.getTransaction().rollback();
		}
	}

}
