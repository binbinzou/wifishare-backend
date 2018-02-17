package com.shanghai.wifishare.wifimgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shanghai.wifishare.wifimgmt.service.HotSpotConnectService;
import com.wifishared.common.data.otd.hotspot.HotSpotConnectRsp;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value="热点评价信息")
@RestController
@Slf4j
public class WifiConnectController {

	@Autowired
	HotSpotConnectService hotSpotConnectService;

	
	@ApiOperation(value = "根据一些信息查询热点信息")
	@RequestMapping(value = "/auth/v1/hotspots/connects", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralContentResult<List<HotSpotConnectRsp>> findHotspotConnectsByHotSpots(
			@RequestBody List<String> ids) {
		GeneralContentResult<List<HotSpotConnectRsp>> result = hotSpotConnectService
				.findHotspotConnectsByHotSpots(ids);
		return result;
	}
	
	
}
