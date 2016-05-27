package br.eti.clairton.email;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String login;

	@NotNull
	@Valid
	@ElementCollection(targetClass = Email.class)
	private Collection<Email> emails = new HashSet<Email>();

	@Deprecated
	public User() {
	}

	public User(String login, Email email) {
		super();
		this.login = login;
		this.emails.add(email);
	}

	public Collection<Email> getEmails() {
		return emails;
	}

	public String getLogin() {
		return login;
	}
}
