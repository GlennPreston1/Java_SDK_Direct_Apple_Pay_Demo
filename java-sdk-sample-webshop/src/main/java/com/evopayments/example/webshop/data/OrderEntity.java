package com.evopayments.example.webshop.data;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.owasp.encoder.Encode;

@Entity
public class OrderEntity {

	@Id
	private String id;

	private String product;

	private Integer amount;
	private String currency;

	private java.sql.Timestamp createdOn;

	private java.sql.Timestamp modifiedOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public java.sql.Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(java.sql.Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public String toString() {
		return Encode.forJava("OrderEntity [id=" + id + ", product=" + product + ", amount=" + amount + ", currency=" + currency
				+ ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + "]");
	}

}