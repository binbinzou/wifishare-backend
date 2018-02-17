package com.shanghai.wifishare.ordermgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shanghai.wifishare.ordermgmt.service.OrderMergeService;
import com.wifishared.common.data.otd.order.OrdersConnectRspItem;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="主订单信息")
@RestController
public class OrderMergeController {

	@Autowired
	OrderMergeService orderMergeService;

	@ApiOperation(value = "查询用户未结算的订单")
	@RequestMapping(value = "/auth/v1/orders/nomergence", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "Bearer ", required = true) })
	public GeneralContentResult<OrdersConnectRspItem> queryNoMergenceOrders(@RequestHeader("Authorization") String authorization) {
		return orderMergeService.queryNoMergenceOrders(authorization);
	}

}
