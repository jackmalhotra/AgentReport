package com.allconnect.agentReport.rest.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.allconnect.agentReport.model.TokenResponse;
import com.allconnect.agentReport.repo.E2ERepository;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@RestController
@RequestMapping("rest")
public class E2EController {

	private static final Logger logger = LoggerFactory.getLogger(E2EController.class);
	private static final String OFFER_GENERATED_BY = "jackmalhotra56@gmail.com";
	private static final String REWARD_AMOUNT = "25";

	@Autowired
	private E2ERepository repo;

	@PostMapping("/sendE2ESpectrumEmailLink")
	public ResponseEntity<String> sendMail(@RequestParam("lineItemId") String lineItemId, @RequestParam("address") String address,
			@RequestParam("city") String city, @RequestParam("moving") String moving,
			@RequestParam("state") String state, @RequestParam("zip") String zip,
			@RequestParam("trackingId") String trackingId, @RequestParam("email") String email,
			@RequestParam("callbackNumber") String callbackNumber,
			@RequestParam("RecipientFirstName") String RecipientFirstName,
			@RequestParam("RecipientLastName") String RecipientLastName,
			@RequestParam("ProviderName") String ProviderName, @RequestParam("PromotionName") String PromotionName,
			@RequestParam("orderId") String orderId) throws IOException, JSONException {
		String link = "https://buy.spectrum.com/buyflow/buyflow-localization?v=ALLCON&";

		StringBuilder sb = new StringBuilder();
		sb = sb.append(link).append("a=").append(address).append("&addrCity=").append(city).append("&addrState=")
				.append(state).append("&z=").append(zip).append("&moving=").append(moving).append("&TransID=")
				.append(lineItemId).append("&offcmp=PSF%2CNTP&affpn=1-833-579-0038");

		logger.info("lineItemId=" + lineItemId);
		String updatedLink = sb.toString().replaceAll(" ", "%20");
		URL spUrl = new URL(updatedLink);
		logger.info("UpdatedE2ESpectrumUrl=" + spUrl);

		JSONObject json1 = new JSONObject();
		json1.put("RecipientEmail", email);
		json1.put("RecipientFirstName", RecipientFirstName);
		json1.put("RecipientLastName", RecipientLastName);
		json1.put("ProviderName", ProviderName);
		json1.put("PromotionName", PromotionName);
		json1.put("OfferGeneratedBy", OFFER_GENERATED_BY);
		json1.put("OrderID", orderId);
		json1.put("LinkURL", spUrl);
		json1.put("RewardAmount", REWARD_AMOUNT);
		String json = json1.toString();

		String token = null;

		TokenResponse lastData = repo.findLastData();
		logger.info("Last_Token_Record: " + lastData);
		if (lastData == null) {
			TokenResponse re = SpectrumE2EGetToken();
			logger.info("New_Token_Generated !!!");
			token = re.getToken();

		} else {
			logger.info("getExpiresUTC : " + lastData.getExpiresUTC());
			String dataBaseDate = lastData.getExpiresUTC();

			String currentUTCDate = getCurrentUtcTime(); // 2022-12-09T18:25:58.6037597
			logger.info("UTC Current Time is: " + getCurrentUtcTime());

			// If Expiry Database date is more that CurrentDate
			if (dataBaseDate.compareTo(currentUTCDate) > 0) {
				logger.info("Session not Expired Same Token will be used !");
				token = lastData.getToken();
			}
			// If CurrentDate is more than Expiry Database date
			else if (dataBaseDate.compareTo(currentUTCDate) < 0) {
				logger.info("Session Expired ! New Token Generated !!!");
				TokenResponse re = SpectrumE2EGetToken();
				token = re.getToken();
			}
		}

		String getcreateofferResponse = null;
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

		Request request = new Request.Builder().url("https://testapi.sbloyalty.com/v2/allconnect/createoffer/")
				.addHeader("content-type", "application/json").header("Authorization", "Bearer " + token)
				.method("POST", body).build();

		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();
		getcreateofferResponse = response.body().string();
		logger.info("Mail_Response: " + getcreateofferResponse);
		logger.info("Mail_Sent_successfully_for_email=" + email);

		return ResponseEntity.ok().body(getcreateofferResponse);
	}

	public TokenResponse SpectrumE2EGetToken() throws JSONException, IOException {
		TokenResponse tokenResponse = new TokenResponse();
		JSONObject json = new JSONObject();
		json.put("Username", "allconnect_testuser");
		json.put("Password", "Vu101^w5%8eZ*xS");
		String getTokenResponse = null;

		RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());
		Request request = new Request.Builder().url("https://testapi.sbloyalty.com/v2/allconnect/token")
				.addHeader("Content-Type", "application/json").method("POST", body).build();

		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();
		getTokenResponse = response.body().string();
		logger.info("Token Response: " + getTokenResponse);

		JSONObject xyz = new JSONObject(getTokenResponse);
		tokenResponse.setToken(xyz.getString("Token"));
		tokenResponse.setExpiresUTC(xyz.getString("ExpiresUTC"));
		repo.save(tokenResponse);

		return tokenResponse;
	}

	public static String getCurrentUtcTime() {
		Instant instant = Instant.now();
		String d1 = instant.toString();
		return d1;
	}

}
