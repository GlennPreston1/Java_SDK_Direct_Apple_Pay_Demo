package com.evopayments.turnkey.example.webshop.data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderEntity {

	@Id
	private String id;

	private String product;

	private BigDecimal amount;
	// private String currency; // in this example it is fixed

	private String status;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderEntity [id=" + id + ", product=" + product + ", amount=" + amount + ", status=" + status
				+ ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + "]";
	}

}