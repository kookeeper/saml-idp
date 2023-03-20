package br.com.sbk.saml2.idp.entity.intsoa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CON_ID database table.
 * 
 */
@Entity
@Table(name = "CON_ID")
@NamedQuery(name = "ConId.findAll", query = "SELECT c FROM ConId c")
public class ConId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Id")
	private String id;
	/*
	 * @Column(name="AccessFailedCount") private int accessFailedCount;
	 * 
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @Column(name="DATATOKEN") private Date datatoken;
	 * 
	 * @Column(name="Discriminator") private String discriminator;
	 */
	@Column(name = "Email")
	private String email;
	/*
	 * @Column(name="EmailConfirmed") private int emailConfirmed;
	 * 
	 * @Column(name="HABILITADO") private int habilitado;
	 * 
	 * @Column(name="HABILITATOKEN") private int habilitatoken;
	 * 
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @Column(name="LASTLOGIN") private Date lastlogin;
	 * 
	 * @Column(name="LockoutEnabled") private int lockoutEnabled;
	 * 
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @Column(name="LockoutEndDateUtc") private Date lockoutEndDateUtc;
	 * 
	 * @Column(name="MASTERUSER") private int masteruser;
	 */
	@Column(name = "Name")
	private String name;

	@Column(name = "PasswordHash")
	private String passwordHash;
	/*
	 * @Column(name="PhoneNumber") private String phoneNumber;
	 * 
	 * @Column(name="PhoneNumberConfirmed") private int phoneNumberConfirmed;
	 * 
	 * @Column(name="SecurityStamp") private String securityStamp;
	 * 
	 * @Column(name="TwoFactorEnabled") private int twoFactorEnabled;
	 * 
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @Column(name="UltimaAtualizacaoSenha") private Date ultimaAtualizacaoSenha;
	 * 
	 * @Column(name="ULTIMOTOKEN") private String ultimotoken;
	 */
	@Column(name = "UserName")
	private String userName;

	/*
	 * @Column(name="VersaoLgpd") private int versaoLgpd;
	 */
	public ConId() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}