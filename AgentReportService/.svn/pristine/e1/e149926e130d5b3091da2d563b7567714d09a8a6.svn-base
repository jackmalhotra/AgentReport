package com.allconnect.agentReport.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class AgentReportWTDModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1011214512L;
	@Id
	@Column(name = "EID")
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long EID;
	
	@Column(name="callsanswered")
	private Integer CallsAnswered;
	
	@Column(name = "orders")
	private Integer Orders;
	
	@Column(name = "coreproductssold")
	private Integer CoreProductsSold;
	
	@Column(name="basketsize")
	private float BasketSize;
	
	@Column(name = "coreconversion")
	private float CoreConversion;
	
	@Column(name = "weightedconversion")
	private float WeightedConversion;
	
	@Column(name = "merchandisingscore")
	private float MerchandisingScore;	
	
	@Column(name = "createddate")
	private String CreatedDate;
	

	@Override
	public String toString() {
		return "AgentReportModel [EID=" + EID + ", CallsAnswered=" + CallsAnswered + ", Orders=" + Orders
				+ ", CoreProductsSold=" + CoreProductsSold + ", BasketSize=" + BasketSize + ", CoreConversion="
				+ CoreConversion + ", WeightedConversion=" + WeightedConversion + ", MerchandisingScore="
				+ MerchandisingScore + ", CreatedDate=" + CreatedDate + "]";
	}

	public float getMerchandisingScore() {
		return MerchandisingScore;
	}

	public void setMerchandisingScore(float merchandisingScore) {
		MerchandisingScore = merchandisingScore;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public Long getEID() {
		return EID;
	}

	public void setEID(Long eID) {
		EID = eID;
	}

	public Integer getCallsAnswered() {
		return CallsAnswered;
	}

	public void setCallsAnswered(Integer callsAnswered) {
		CallsAnswered = callsAnswered;
	}

	public Integer getOrders() {
		return Orders;
	}

	public void setOrders(Integer orders) {
		Orders = orders;
	}

	public Integer getCoreProductsSold() {
		return CoreProductsSold;
	}

	public void setCoreProductsSold(Integer coreProductsSold) {
		CoreProductsSold = coreProductsSold;
	}

	public float getBasketSize() {
		return BasketSize;
	}

	public void setBasketSize(float basketSize) {
		BasketSize = basketSize;
	}

	public float getCoreConversion() {
		return CoreConversion;
	}

	public void setCoreConversion(float coreConversion) {
		CoreConversion = coreConversion;
	}

	public float getWeightedConversion() {
		return WeightedConversion;
	}

	public void setWeightedConversion(float weightedConversion) {
		WeightedConversion = weightedConversion;
	}
}