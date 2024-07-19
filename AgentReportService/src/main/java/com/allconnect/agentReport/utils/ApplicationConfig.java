/**
 * 
 */
package com.allconnect.agentReport.utils;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 
 *
 */
@Configuration
@ComponentScan(basePackages = "com.allconnect")
@EnableTransactionManagement
public class ApplicationConfig  {
	  
}