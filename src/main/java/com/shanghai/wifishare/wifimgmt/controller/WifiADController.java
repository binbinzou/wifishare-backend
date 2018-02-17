package com.shanghai.wifishare.wifimgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shanghai.wifishare.wifimgmt.service.HotSpotADService;
import com.wifishared.common.data.dto.hotspot.HotSpotADReqBody;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value="热点广告信息")
@RestController
@Slf4j
public class WifiADController {

	@Autowired
	HotSpotADService hotSpotADService;

	@ApiOperation(value = "创建热点信息")
	@RequestMapping(value = "/auth/v1/hotspots/{hotspotId}/ads", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralContentResult<String> createHotSpotAD(
			@RequestBody HotSpotADReqBody hotSpotADReqBody) {
		log.info("create hotspot body:{}",hotSpotADReqBody.toString());
		GeneralContentResult<String> result = hotSpotADService
				.createHotSpotAD(hotSpotADReqBody);
		return result;
	}

}
