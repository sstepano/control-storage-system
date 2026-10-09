package org.code_studio.controller;

import java.math.BigDecimal;

import org.code_studio.database.ClientPricelist;
import org.code_studio.model.ClientPricelistModel;
import org.code_studio.model.ClientPricelistModelPageable;
import org.code_studio.model.ClientPricelistTotalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.clientPricelist}")
public class ClientPricelistController extends BaseController <ClientPricelist> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	ClientPricelistModel model;
	
	@Autowired
	ClientPricelistModelPageable modelPageable;
	
	@Autowired
	ClientPricelistTotalModel modelTotal;
	
	public ClientPricelistController (ClientPricelistModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByClientId/{clientId}")
	public ResponseEntity <Object> allByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> allByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientId(clientId, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@PostMapping("updateDiscountRateByClientId/{clientId}/{discountRate}")
	public ResponseEntity <Object> updateDiscountRateByClientId (@PathVariable Integer clientId, @PathVariable BigDecimal discountRate) {
		try {
			responseData = model.updateDiscountRateByClientId(clientId, discountRate);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@PostMapping("updateAllPricesByClientId/{clientId}/{discountRatePct}/{increaseDecreaseFlag}")
	public ResponseEntity <Object> massUpdateByClientId (@PathVariable Integer clientId, @PathVariable BigDecimal discountRatePct, @PathVariable Boolean increaseDecreaseFlag) {
		try {
			responseData = model.updateAllPricesByClientId(clientId, discountRatePct, increaseDecreaseFlag);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@PostMapping("deleteAllByClientId/{clientId}")
	public ResponseEntity <Object> deleteAllByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.deleteAllByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByItemId/{itemId}/{pageId}")
	public ResponseEntity <Object> allByItemId (@PathVariable Integer itemId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByItemId(itemId, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("totalByClientId/{clientId}")
	public ResponseEntity <Object> totalByClientId (@PathVariable Integer clientId) {
		try {
			responseData = modelTotal.findTotalByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByClientIdAndItemId/{clientId}/{itemId}")
	public ResponseEntity <Object> allByClientIdAndItemId (@PathVariable Integer clientId, @PathVariable Integer itemId) {
		try {
			responseData = model.findAllByClientIdAndItemId(clientId, itemId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
}
