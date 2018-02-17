package com.shanghai.wifishare.wifimgmt.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shanghai.wifishare.wifimgmt.service.HotSpotService;
import com.wifishared.common.data.dto.hotspot.HotSpotReqBody;
import com.wifishared.common.data.dto.hotspot.HotSpotReqParam;
import com.wifishared.common.data.otd.hotspot.HotSpotRspBody;
import com.wifishared.common.framework.resultobj.GeneralContentResult;
import com.wifishared.common.framework.resultobj.GeneralResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value="热点信息")
@RestController
@Slf4j
public class WifiMgmtController {

	@Autowired
	HotSpotService hotSpotService;

	@ApiOperation(value = "创建热点信息")
	@RequestMapping(value = "/auth/v1/hotspots", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralContentResult<String> createHotSpot(
			@RequestBody HotSpotReqBody hotSpotReqBody) {
		log.info("create hotspot body:{}",hotSpotReqBody.toString());
		GeneralContentResult<String> result = hotSpotService
				.createHotSpot(hotSpotReqBody);
		return result;
	}

	@ApiOperation(value = "查询热点信息")
	@RequestMapping(value = "/auth/v1/hotspots/{hotspotId}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralContentResult<HotSpotRspBody> findHotSpot(
			@PathVariable("hotspotId") String hotspotId) {
		GeneralContentResult<HotSpotRspBody> result = hotSpotService
				.findHotSpot(hotspotId);
		return result;
	}
	
	@ApiOperation(value = "根据id数组查询所有热点")
	@RequestMapping(value = "/auth/v1/hotspots/list", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralContentResult<List<HotSpotRspBody>> findHotSpots(
			@RequestBody Set<String> ids) {
		GeneralContentResult<List<HotSpotRspBody>> result = hotSpotService
				.findHotSpots(ids);
		return result;
	}
	
	@ApiOperation(value = "更新热点信息")
	@RequestMapping(value = "/auth/v1/hotspots/{hotspotId}", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResult updateHotSpot(
			@PathVariable("hotspotId") String hotspotId,
			@RequestBody HotSpotReqBody hotSpotReqBody) {
		GeneralResult result = hotSpotService
				.updateHotSpot(hotspotId,
						hotSpotReqBody);
		return result;
	}
	
	@ApiOperation(value = "使热点信息失效")
	@RequestMapping(value = "/auth/v1/hotspots/{hotspotId}/disabled", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResult disableHotSpot(
			@PathVariable("hotspotId") String hotspotId) {
		GeneralResult result = hotSpotService
				.disableHotSpot(hotspotId);
		return result;
	}
	
	@ApiOperation(value = "删除热点信息")
	@RequestMapping(value = "/auth/v1/hotspots/{hotspotId}", method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralResult deleteHotSpot(
			@PathVariable("hotspotId") String hotspotId) {
		GeneralResult result = hotSpotService
				.deleteHotSpot(hotspotId);
		return result;
	}
	
	@ApiOperation(value = "根据一些信息查询热点信息")
	@RequestMapping(value = "/noauth/v1/hotspots/location", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralContentResult<List<HotSpotRspBody>> findHotSpotBySomeAttr(
			@RequestBody HotSpotReqParam param) {
		GeneralContentResult<List<HotSpotRspBody>> result = hotSpotService
				.findHotSpotBySomeAttr(param);
		return result;
	}
	
	@ApiOperation(value = "查询自己分享的热点")
	@RequestMapping(value = "/auth/v1/hotspots/selfshare", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "Bearer ", required = true) })
	public GeneralContentResult<List<HotSpotRspBody>> findHotSpotShareBySelf(@RequestHeader("Authorization") String authorization) {
		GeneralContentResult<List<HotSpotRspBody>> result = hotSpotService
				.findHotSpotShareBySelf(authorization);
		return result;
	}
	
	
	
}
