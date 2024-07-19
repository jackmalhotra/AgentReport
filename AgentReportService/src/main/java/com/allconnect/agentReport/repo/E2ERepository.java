package com.allconnect.agentReport.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.allconnect.agentReport.model.TokenResponse;

public interface E2ERepository extends JpaRepository<TokenResponse,String>{
	
	@Query(value = "select top 1 * from allconnectutility.token_response order by expiresutc desc", nativeQuery = true)
	 TokenResponse findLastData();
}