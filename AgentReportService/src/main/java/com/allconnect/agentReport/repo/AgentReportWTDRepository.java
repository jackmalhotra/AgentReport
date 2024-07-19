package com.allconnect.agentReport.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.allconnect.agentReport.model.AgentReportWTDModel;

public interface AgentReportWTDRepository extends JpaRepository<AgentReportWTDModel, Long>{
	
	@Query(value="SELECT\r\n" + 
			"c.eid as EID,\r\n" + 
			"c.callsanswered as CallsAnswered,\r\n" + 
			"c.orders as Orders,\r\n" + 
			"c.coreproductssold as CoreProductsSold,\r\n" + 
			"c.BasketSize as BasketSize,\r\n" + 
			"c.coreconversion as CoreConversion,\r\n" + 
			"c.weightedconversion as WeightedConversion,\r\n" + 
			"c.merchandisingscore as MerchandisingScore,\r\n" + 
			"c.createddate as CreatedDate\r\n" + 
			"FROM allconnectutility.agent_admin_report_wtd c\r\n" + 
			"WHERE c.eid = ?1", nativeQuery = true)
	AgentReportWTDModel getWtdData(String agentPhoneId);
	
}