package com.shanghai.wifishare.ordermgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shanghai.wifishare.ordermgmt.service.OrderCommentService;
import com.wifishared.common.data.dto.hotspot.HotSpotCommentReqBody;
import com.wifishared.common.data.otd.hotspot.HotSpotCommentRspBody;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="订单评价信息")
@RestController
public class OrderCommentController {

	@Autowired
	OrderCommentService orderCommentService;

	@ApiOperation(value = "查询wifi的评价")
	@RequestMapping(value = "/noauth/v1/ordercomments/hotspots", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralContentResult<List<HotSpotCommentRspBody>> queryHotspotComments(@RequestBody List<String> hotspotIds) {
		GeneralContentResult<List<HotSpotCommentRspBody>> result = orderCommentService
				.queryHotspotComments(hotspotIds);
		return result;
	}
	
	@ApiOperation(value = "创建订单的评价")
	@RequestMapping(value = "/auth/v1/ordercomments", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "Bearer ", required = true) })
	public GeneralContentResult<String> createHotspotComments(@RequestBody HotSpotCommentReqBody reqBody ) {
		GeneralContentResult<String> result = orderCommentService
				.createHotspotComments(reqBody);
		return result;
	}
	
	
}
