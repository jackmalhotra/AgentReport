package com.allconnect.agentReport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SAASAgentReportMTDModel {

private static final long serialVersionUID = 1011214512L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	

	@Column(name = "agent_login")
	private String AgentLogin;

	@Column(name = "sessions_run_count")
	private Integer SessionsRunCount;

	@Column(name = "orders_count")
	private Integer OrdersCount;

	@Column(name = "session_conversion")
	private float SessionConversion;

	@Column(name = "servedup_internet")
	private Integer ServedupInternet;

	@Column(name = "servedup_video")
	private Integer ServedupVideo;

	@Column(name = "servedup_phone")
	private Integer ServedupPhone;

	@Column(name = "createddate")
	private String CreatedDate;

	public String getAgentLogin() {
		return AgentLogin;
	}

	public void setAgentLogin(String agentLogin) {
		AgentLogin = agentLogin;
	}

	public Integer getSessionsRunCount() {
		return SessionsRunCount;
	}

	public void setSessionsRunCount(Integer sessionsRunCount) {
		SessionsRunCount = sessionsRunCount;
	}

	public Integer getOrdersCount() {
		return OrdersCount;
	}

	public void setOrdersCount(Integer ordersCount) {
		OrdersCount = ordersCount;
	}

	public float getSessionConversion() {
		return SessionConversion;
	}

	public void setSessionConversion(float sessionConversion) {
		SessionConversion = sessionConversion;
	}

	public Integer getServedupInternet() {
		return ServedupInternet;
	}

	public void setServedupInternet(Integer servedupInternet) {
		ServedupInternet = servedupInternet;
	}

	public Integer getServedupVideo() {
		return ServedupVideo;
	}

	public void setServedupVideo(Integer servedupVideo) {
		ServedupVideo = servedupVideo;
	}

	public Integer getServedupPhone() {
		return ServedupPhone;
	}

	public void setServedupPhone(Integer servedupPhone) {
		ServedupPhone = servedupPhone;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	@Override
	public String toString() {
		return "SAASAgentReportMTDModel [AgentLogin=" + AgentLogin + ", SessionsRunCount=" + SessionsRunCount
				+ ", OrdersCount=" + OrdersCount + ", SessionConversion=" + SessionConversion + ", ServedupInternet="
				+ ServedupInternet + ", ServedupVideo=" + ServedupVideo + ", ServedupPhone=" + ServedupPhone
				+ ", CreatedDate=" + CreatedDate + "]";
	}

}
