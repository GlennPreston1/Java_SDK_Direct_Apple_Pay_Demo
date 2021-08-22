package com.evopayments.example.webshop.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String orderDescription;

	private Integer amount;
	private String currency;

	private java.sql.Timestamp createdOn;

	private java.sql.Timestamp modifiedOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
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
		return "Order [id=" + id + ", orderDescription=" + orderDescription + ", amount=" + amount + ", currency="
				+ currency + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + "]";
	}
	
}