package com.allconnect.agentReport.rest.controller;

import java.util.Calendar;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.allconnect.agentReport.model.AgentReportModel;
import com.allconnect.agentReport.repo.AgentReportRepository;
import com.allconnect.agentReport.model.AgentReportWTDModel;
import com.allconnect.agentReport.model.SAASAgentReportMTDModel;
import com.allconnect.agentReport.model.SAASAgentReportModel;
import com.allconnect.agentReport.model.SAASAgentReportWTDModel;
import com.allconnect.agentReport.repo.AgentReportWTDRepository;
import com.allconnect.agentReport.repo.PreviousAgentReportMTDRepository;
import com.allconnect.agentReport.repo.PreviousAgentReportTodayRepository;
import com.allconnect.agentReport.repo.PreviousAgentReportWTDRepository;
import com.allconnect.agentReport.repo.PreviousSaasAgentReportMTDRepository;
import com.allconnect.agentReport.repo.PreviousSaasAgentReportTodayRepository;
import com.allconnect.agentReport.repo.PreviousSaasAgentReportWTDRepository;
import com.allconnect.agentReport.repo.SAASAgentReportMTDRepository;
import com.allconnect.agentReport.repo.SAASAgentReportRepository;
import com.allconnect.agentReport.repo.SAASAgentReportWTDRepository;
import com.allconnect.agentReport.model.AgentReportTodayModel;
import com.allconnect.agentReport.repo.AgentReportTodayRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter; 
import java.text.SimpleDateFormat;


@RestController
@RequestMapping("rest")
public class AgentReportRestController{

	private static final Logger logger = LoggerFactory.getLogger(AgentReportRestController.class);
	
	@Autowired
	private AgentReportRepository agentReportRepository;
	
	@Autowired
	private AgentReportWTDRepository agentReportWTDRepository;
	
	@Autowired
	private AgentReportTodayRepository agentReportTodayRepository;
	
	@Autowired
	private SAASAgentReportRepository saasAgentReportRepository;
	
	@Autowired
	private SAASAgentReportWTDRepository saasAgentReportWTDRepository;
	
	@Autowired
	private SAASAgentReportMTDRepository saasAgentReportMTDRepository;
	
	@Autowired
	private PreviousAgentReportTodayRepository previousAgentReportDailyRepository;
	
	@Autowired
	private PreviousAgentReportMTDRepository previousAgentReportMTDRepository;
	
	@Autowired
	private PreviousAgentReportWTDRepository previousAgentReportWTDRepository;
	
	@Autowired
	private PreviousSaasAgentReportTodayRepository previousSaasAgentReportDailyRepository;
	
	@Autowired
	private PreviousSaasAgentReportWTDRepository previousSaasAgentReportWTDRepository;
	
	@Autowired
	private PreviousSaasAgentReportMTDRepository previousSaasAgentReportMTDRepository;
		

	Calendar cal=Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String callTime = sdf.format(cal.getTime());
	
	@GetMapping("getAgentReportData")
	public ResponseEntity<Object> getAgentReportData(@RequestParam("agentPhoneId") String agentPhoneId) {
		logger.info("getAgentReportData api call started");
		AgentReportModel armMtd = agentReportRepository.getData(agentPhoneId);
		AgentReportTodayModel armToday = agentReportTodayRepository.getTodayData(agentPhoneId);
		AgentReportWTDModel armWtd = agentReportWTDRepository.getWtdData(agentPhoneId);
		JSONObject jsobj = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String monthlyAgentReportObject = ow.writeValueAsString(armMtd);
			String weeklyAgentReportObject = ow.writeValueAsString(armWtd);
			String dailyAgentReportObject = ow.writeValueAsString(armToday);
			
			if(monthlyAgentReportObject == null || monthlyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportModel armMtdObj1 = new AgentReportModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("monthlyAgentReport", json);
			}else {
				jsobj.put("monthlyAgentReport", monthlyAgentReportObject);
			}
			
			if(weeklyAgentReportObject == null || weeklyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportWTDModel armMtdObj1 = new AgentReportWTDModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("weeklyAgentReport", json);
			}else {
				jsobj.put("weeklyAgentReport", weeklyAgentReportObject);
			}
			if(dailyAgentReportObject == null || dailyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportTodayModel armMtdObj1 = new AgentReportTodayModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("dailyAgentReport", json);
			}else {
				jsobj.put("dailyAgentReport", dailyAgentReportObject);
			}
			
			logger.info("AgentReportModelResponse="+jsobj);
			return ResponseEntity.ok().body(jsobj.toString());
		} 
		catch (JsonProcessingException e) {
			logger.error("Error_in_AgentReport="+e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error_in_AgentReport="+e.getMessage());
		}
		AgentReportModel armObj = new AgentReportModel();
		return ResponseEntity.ok().body(armObj.toString());

	}

	@GetMapping("getPreviousDailyAgentReportData")
	public ResponseEntity<Object> getPreviousDailyAgentReportData(@RequestParam("agentPhoneId") String agentPhoneId,@RequestParam("date") String date) {
		logger.info("getPreviousDaily AgentReportData api call started");
		AgentReportModel armMtd = agentReportRepository.getData(agentPhoneId);
		AgentReportTodayModel armToday = previousAgentReportDailyRepository.getTodayData(agentPhoneId,date);
		AgentReportWTDModel armWtd = agentReportWTDRepository.getWtdData(agentPhoneId);
		JSONObject jsobj = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String monthlyAgentReportObject = ow.writeValueAsString(armMtd);
			String weeklyAgentReportObject = ow.writeValueAsString(armWtd);
			String dailyAgentReportObject = ow.writeValueAsString(armToday);
			
			if(monthlyAgentReportObject == null || monthlyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportModel armMtdObj1 = new AgentReportModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("monthlyAgentReport", json);
			}else {
				jsobj.put("monthlyAgentReport", monthlyAgentReportObject);
			}
			
			if(weeklyAgentReportObject == null || weeklyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportWTDModel armMtdObj1 = new AgentReportWTDModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("weeklyAgentReport", json);
			}else {
				jsobj.put("weeklyAgentReport", weeklyAgentReportObject);
			}
			if(dailyAgentReportObject == null || dailyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportTodayModel armMtdObj1 = new AgentReportTodayModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("dailyAgentReport", json);
			}else {
				jsobj.put("dailyAgentReport", dailyAgentReportObject);
			}
			
			logger.info("AgentReportModelResponse="+jsobj);
			return ResponseEntity.ok().body(jsobj.toString());
		} 
		catch (JsonProcessingException e) {
			logger.error("Error_in_PreviousTodayAgentReport="+e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error_in_PreviousTodayAgentReport="+e.getMessage());
		}
		AgentReportTodayModel armObj = new AgentReportTodayModel();
		return ResponseEntity.ok().body(armObj.toString());
	}
	
	@GetMapping("getPreviousWeeklyAgentReportData")
	public ResponseEntity<Object> getPreviousWeeklyAgentReportData(@RequestParam("agentPhoneId") String agentPhoneId,@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate) {
		logger.info("getPreviousWeekly AgentReportData api call started");
		AgentReportModel armMtd = agentReportRepository.getData(agentPhoneId);
		AgentReportTodayModel armToday = agentReportTodayRepository.getTodayData(agentPhoneId);
		AgentReportWTDModel armWtd = previousAgentReportWTDRepository.getWtdData(agentPhoneId,fromDate,toDate);
		JSONObject jsobj = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String monthlyAgentReportObject = ow.writeValueAsString(armMtd);
			String weeklyAgentReportObject = ow.writeValueAsString(armWtd);
			String dailyAgentReportObject = ow.writeValueAsString(armToday);
			if(monthlyAgentReportObject == null || monthlyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportModel armMtdObj1 = new AgentReportModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("monthlyAgentReport", json);
			}else {
				jsobj.put("monthlyAgentReport", monthlyAgentReportObject);
			}
			
			if(weeklyAgentReportObject == null || weeklyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportWTDModel armMtdObj1 = new AgentReportWTDModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("weeklyAgentReport", json);
			}else {
				jsobj.put("weeklyAgentReport", weeklyAgentReportObject);
			}
			if(dailyAgentReportObject == null || dailyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportTodayModel armMtdObj1 = new AgentReportTodayModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("dailyAgentReport", json);
			}else {
				jsobj.put("dailyAgentReport", dailyAgentReportObject);
			}
			
			logger.info("AgentReportModelResponse="+jsobj);
			return ResponseEntity.ok().body(jsobj.toString());
		} 
		catch (JsonProcessingException e) {
			logger.error("Error_in_PreviousWeeklyAgentReport="+e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error_in_PreviousWeeklyAgentReport="+e.getMessage());
		}
		AgentReportWTDModel armObj = new AgentReportWTDModel();
		return ResponseEntity.ok().body(armObj.toString());
	}
	
	@GetMapping("getPreviousMonthlyAgentReportData")
	public ResponseEntity<Object> getPreviousMonthlyAgentReportData(@RequestParam("agentPhoneId") String agentPhoneId,@RequestParam("date") String date) {
		logger.info("getPreviousMonthly AgentReportData api call started");		
		AgentReportModel armMtd = previousAgentReportMTDRepository.getMtdData(agentPhoneId,date);
		AgentReportTodayModel armToday = agentReportTodayRepository.getTodayData(agentPhoneId);
		AgentReportWTDModel armWtd = agentReportWTDRepository.getWtdData(agentPhoneId);
		JSONObject jsobj = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String monthlyAgentReportObject = ow.writeValueAsString(armMtd);
			String weeklyAgentReportObject = ow.writeValueAsString(armWtd);
			String dailyAgentReportObject = ow.writeValueAsString(armToday);
			
			if(monthlyAgentReportObject == null || monthlyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportModel armMtdObj1 = new AgentReportModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("monthlyAgentReport", json);
			}else {
				jsobj.put("monthlyAgentReport", monthlyAgentReportObject);
			}
			
			if(weeklyAgentReportObject == null || weeklyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportWTDModel armMtdObj1 = new AgentReportWTDModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("weeklyAgentReport", json);
			}else {
				jsobj.put("weeklyAgentReport", weeklyAgentReportObject);
			}
			if(dailyAgentReportObject == null || dailyAgentReportObject.equalsIgnoreCase("null")) {
				AgentReportTodayModel armMtdObj1 = new AgentReportTodayModel();
				armMtdObj1.setEID(Long.valueOf(agentPhoneId));
				armMtdObj1.setOrders(0);
				armMtdObj1.setCallsAnswered(0);
				armMtdObj1.setMerchandisingScore(0);
				armMtdObj1.setCoreProductsSold(0);
				armMtdObj1.setBasketSize(0);
				armMtdObj1.setCoreConversion(0);
				armMtdObj1.setWeightedConversion(0);
				armMtdObj1.setCreatedDate(callTime);
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("dailyAgentReport", json);
			}else {
				jsobj.put("dailyAgentReport", dailyAgentReportObject);
			}
			
			logger.info("AgentReportModelResponse="+jsobj);
			System.out.println("AgentReportModelResponse="+jsobj);
			return ResponseEntity.ok().body(jsobj.toString());
		} 
		
		catch (JsonProcessingException e) {
			logger.error("Error_in_PreviousMonthlyAgentReport="+e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error_in_PreviousMonthlyAgentReport="+e.getMessage());
		}
		AgentReportModel armObj = new AgentReportModel();
		return ResponseEntity.ok().body(armObj.toString());
	}
	
	
	@GetMapping("getSaasAgentReportData")
	public ResponseEntity<Object> getSaasAgentReportData(@RequestParam("agentLogin") String agentLogin) {
		logger.info("getAgentReportData api call started");
		SAASAgentReportModel armToday = saasAgentReportRepository.getSaasReportToday(agentLogin);
		SAASAgentReportMTDModel armMtd = saasAgentReportMTDRepository.getMTDSaasReport(agentLogin);
		SAASAgentReportWTDModel armWtd = saasAgentReportWTDRepository.getWTDSaasReport(agentLogin);
		JSONObject jsobj = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String monthlySaasAgentReportObject = ow.writeValueAsString(armMtd);
			String weeklySaasAgentReportObject = ow.writeValueAsString(armWtd);
			String dailySaasAgentReportObject = ow.writeValueAsString(armToday);
			
			if(monthlySaasAgentReportObject == null || monthlySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportMTDModel armMtdObj1 = new SAASAgentReportMTDModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("monthlySaasAgentReport", json);
			}else {
				jsobj.put("monthlySaasAgentReport", monthlySaasAgentReportObject);
			}
			
			if(weeklySaasAgentReportObject == null || weeklySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportWTDModel armMtdObj1 = new SAASAgentReportWTDModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("weeklySaasAgentReport", json);
			}else {
				jsobj.put("weeklySaasAgentReport", weeklySaasAgentReportObject);
			}
			if(dailySaasAgentReportObject == null || dailySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportModel armMtdObj1 = new SAASAgentReportModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("dailySaasAgentReport", json);
			}else {
				jsobj.put("dailySaasAgentReport", dailySaasAgentReportObject);
			}
			
			
			logger.info("SaasAgentReportModelResponse="+jsobj);
			return ResponseEntity.ok().body(jsobj.toString());
		} 
		catch (JsonProcessingException e) {
			logger.error("Error_in_SaasAgentReport="+e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error_in_SaasAgentReport="+e.getMessage());
		}
		SAASAgentReportModel armObj = new SAASAgentReportModel();
		return ResponseEntity.ok().body(armObj.toString());

	}
	
	@GetMapping("getPreviousDailySaasAgentReportData")
	public ResponseEntity<Object> getPreviousDailySaasAgentReportData(@RequestParam("agentLogin") String agentLogin,@RequestParam("date") String date) {
		logger.info("getPreviousToday SaasAgentReportData api call started");
		SAASAgentReportModel armToday = previousSaasAgentReportDailyRepository.getSaasReportToday(agentLogin,date);
		SAASAgentReportMTDModel armMtd = saasAgentReportMTDRepository.getMTDSaasReport(agentLogin);
		SAASAgentReportWTDModel armWtd = saasAgentReportWTDRepository.getWTDSaasReport(agentLogin);
		JSONObject jsobj = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String monthlySaasAgentReportObject = ow.writeValueAsString(armMtd);
			String weeklySaasAgentReportObject = ow.writeValueAsString(armWtd);
			String dailySaasAgentReportObject = ow.writeValueAsString(armToday);
			if(monthlySaasAgentReportObject == null || monthlySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportMTDModel armMtdObj1 = new SAASAgentReportMTDModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("monthlySaasAgentReport", json);
			}else {
				jsobj.put("monthlySaasAgentReport", monthlySaasAgentReportObject);
			}
			
			if(weeklySaasAgentReportObject == null || weeklySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportWTDModel armMtdObj1 = new SAASAgentReportWTDModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("weeklySaasAgentReport", json);
			}else {
				jsobj.put("weeklySaasAgentReport", weeklySaasAgentReportObject);
			}
			if(dailySaasAgentReportObject == null || dailySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportModel armMtdObj1 = new SAASAgentReportModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("dailySaasAgentReport", json);
			}else {
				jsobj.put("dailySaasAgentReport", dailySaasAgentReportObject);
			}
			logger.info("SaasAgentReportModelResponse="+jsobj);
			return ResponseEntity.ok().body(jsobj.toString());
		}
		catch (JsonProcessingException e) {
			logger.error("Error_in_Previous_Today SaasAgentReport="+e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error_in_Previous_Today SaasAgentReport="+e.getMessage());
		}
		SAASAgentReportModel armObj = new SAASAgentReportModel();
		return ResponseEntity.ok().body(armObj.toString());

	}
	
	@GetMapping("getPreviousWeeklySaasAgentReportData")
	public ResponseEntity<Object> getPreviousWeeklySaasAgentReportData(@RequestParam("agentLogin") String agentLogin,@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate) {
		logger.info("getPreviousWeekly SaasAgentReportData api call started");
		SAASAgentReportModel armToday = saasAgentReportRepository.getSaasReportToday(agentLogin);
		SAASAgentReportMTDModel armMtd = saasAgentReportMTDRepository.getMTDSaasReport(agentLogin);
		SAASAgentReportWTDModel armWtd = previousSaasAgentReportWTDRepository.getWTDSaasReport(agentLogin,fromDate,toDate);
		JSONObject jsobj = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String monthlySaasAgentReportObject = ow.writeValueAsString(armMtd);
			String weeklySaasAgentReportObject = ow.writeValueAsString(armWtd);
			String dailySaasAgentReportObject = ow.writeValueAsString(armToday);
			if(monthlySaasAgentReportObject == null || monthlySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportMTDModel armMtdObj1 = new SAASAgentReportMTDModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("monthlySaasAgentReport", json);
			}else {
				jsobj.put("monthlySaasAgentReport", monthlySaasAgentReportObject);
			}
			
			if(weeklySaasAgentReportObject == null || weeklySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportWTDModel armMtdObj1 = new SAASAgentReportWTDModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("weeklySaasAgentReport", json);
			}else {
				jsobj.put("weeklySaasAgentReport", weeklySaasAgentReportObject);
			}
			if(dailySaasAgentReportObject == null || dailySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportModel armMtdObj1 = new SAASAgentReportModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("dailySaasAgentReport", json);
			}else {
				jsobj.put("dailySaasAgentReport", dailySaasAgentReportObject);
			}
			
			logger.info("SaasAgentReportModelResponse="+jsobj);
			return ResponseEntity.ok().body(jsobj.toString());
		} 
		catch (JsonProcessingException e) {
			logger.error("Error_in_Previous_Weekly SaasAgentReport="+e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error_in_Previous_Weekly SaasAgentReport="+e.getMessage());
		}
		SAASAgentReportWTDModel armObj = new SAASAgentReportWTDModel();
		return ResponseEntity.ok().body(armObj.toString());
	}
	
	@GetMapping("getPreviousMonthlySaasAgentReportData")
	public ResponseEntity<Object> getPreviousMonthlySaasAgentReportData(@RequestParam("agentLogin") String agentLogin,@RequestParam("date") String date) {
		logger.info("getPreviousMonthly SaasAgentReportData api call started");
		SAASAgentReportModel armToday = saasAgentReportRepository.getSaasReportToday(agentLogin);
		SAASAgentReportMTDModel armMtd = previousSaasAgentReportMTDRepository.getMTDSaasReport(agentLogin,date);
		SAASAgentReportWTDModel armWtd = saasAgentReportWTDRepository.getWTDSaasReport(agentLogin);
		JSONObject jsobj = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String monthlySaasAgentReportObject = ow.writeValueAsString(armMtd);
			String weeklySaasAgentReportObject = ow.writeValueAsString(armWtd);
			String dailySaasAgentReportObject = ow.writeValueAsString(armToday);
			
			if(monthlySaasAgentReportObject == null || monthlySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportMTDModel armMtdObj1 = new SAASAgentReportMTDModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("monthlySaasAgentReport", json);
			}else {
				jsobj.put("monthlySaasAgentReport", monthlySaasAgentReportObject);
			}
			
			if(weeklySaasAgentReportObject == null || weeklySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportWTDModel armMtdObj1 = new SAASAgentReportWTDModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("weeklySaasAgentReport", json);
			}else {
				jsobj.put("weeklySaasAgentReport", weeklySaasAgentReportObject);
			}
			if(dailySaasAgentReportObject == null || dailySaasAgentReportObject.equalsIgnoreCase("null")) {
				SAASAgentReportModel armMtdObj1 = new SAASAgentReportModel();
				armMtdObj1.setAgentLogin(agentLogin);
				armMtdObj1.setOrdersCount(0);
				armMtdObj1.setServedupInternet(0);
				armMtdObj1.setServedupPhone(0);
				armMtdObj1.setServedupVideo(0);
				armMtdObj1.setSessionConversion(0);
				armMtdObj1.setSessionsRunCount(0);
				armMtdObj1.setCreatedDate(callTime);
				
				String json = ow.writeValueAsString(armMtdObj1);
				jsobj.put("dailySaasAgentReport", json);
			}else {
				jsobj.put("dailySaasAgentReport", dailySaasAgentReportObject);
			}
			
			
			logger.info("SaasAgentReportModelResponse="+jsobj);
			return ResponseEntity.ok().body(jsobj.toString());
		}  
		catch (JsonProcessingException e) {
			logger.error("Error_in_Previous_Monthly SaasAgentReport="+e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error_in_Previous_Monthly SaasAgentReport="+e.getMessage());
		}
		SAASAgentReportMTDModel armObj = new SAASAgentReportMTDModel();
		return ResponseEntity.ok().body(armObj.toString());

	}
	
	@GetMapping("healthCheck")
	public ResponseEntity<Object> healthCheck() {
		logger.debug("AgentReportService Service Started");
		return ResponseEntity.ok().body("Success");
	}
	
	
	
}
