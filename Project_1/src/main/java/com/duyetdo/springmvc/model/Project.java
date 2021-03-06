package com.duyetdo.springmvc.model;
// Generated Mar 27, 2017 11:22:56 AM by Hibernate Tools 5.2.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * Project generated by hbm2java
 */
@Entity
@Table(name = "project", catalog = "Project_1", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Project implements java.io.Serializable {
	
	private Integer id;

	@Pattern(regexp="^[a-zA-Z0-9_.-]*$")
	@NotBlank
	private String projectId;

	@NotNull
	private Customer customer;

	@NotBlank
	private String name;

	@NumberFormat(style = Style.CURRENCY, pattern = "###,###,###,###,###.############")
	@NotNull
	private Double value;

	@NumberFormat(style = Style.CURRENCY, pattern = "###,###,###,###,###.############")
	@NotNull
	private Double giaVon;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@NotNull
	private Date date;

	@NotBlank
	private String currency;

	@NumberFormat(style = Style.CURRENCY, pattern = "###,###,###,###.############")
	@NotNull
	private Double rate;

	private Set<Fee> fees = new HashSet<Fee>();
	private Set<Result> results = new HashSet<Result>();

	public Project() {
	}

	public Project(String projectId) {
		this.projectId = projectId;
	}

	public Project(String projectId, Customer customer, String name, Double value, Double giaVon, Date date,
			String currency, Double rate, Set<Fee> fees, Set<Result> results) {
		this.projectId = projectId;
		this.customer = customer;
		this.name = name;
		this.value = value;
		this.giaVon = giaVon;
		this.date = date;
		this.currency = currency;
		this.rate = rate;
		this.fees = fees;
		this.results = results;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "project_ID", unique = true, nullable = false, length = 45)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_customer")
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Column(name = "name", unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "value", precision = 22, scale = 0)
	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Column(name = "gia_von", precision = 22, scale = 0)
	public Double getGiaVon() {
		return this.giaVon;
	}

	public void setGiaVon(Double giaVon) {
		this.giaVon = giaVon;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date", length = 10)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "currency", length = 45)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "rate", precision = 22, scale = 0)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Fee> getFees() {
		return this.fees;
	}

	public void setFees(Set<Fee> fees) {
		this.fees = fees;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Result> getResults() {
		return this.results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

}
