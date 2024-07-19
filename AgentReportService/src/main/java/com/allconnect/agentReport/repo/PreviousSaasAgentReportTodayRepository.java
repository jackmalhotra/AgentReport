package com.allconnect.agentReport.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.allconnect.agentReport.model.SAASAgentReportModel;

public interface PreviousSaasAgentReportTodayRepository extends JpaRepository<SAASAgentReportModel, String>{
	
	@Query(value="SELECT\r\n" + 
			"c.agent_login as Agent_Login,\r\n" + 
			"c.sessions_run_count as Sessions_Run_Count,\r\n" + 
			"c.orders_count as Orders_Count,\r\n" + 
			"c.session_conversion as Session_Conversion,\r\n" + 
			"c.servedup_internet as Servedup_Internet,\r\n" + 
			"c.servedup_video as Servedup_Video,\r\n" + 
			"c.servedup_phone as Servedup_Phone,\r\n" + 
			"c.createddate as CreatedDate\r\n" + 
			"FROM allconnectutility.saas_agent_admin_report c\r\n" + 
			"WHERE (c.agent_login = ?1  AND DATE(c.createddate) = DATE(?2))", nativeQuery = true)
	SAASAgentReportModel getSaasReportToday(String agentLogin, String date);

}
