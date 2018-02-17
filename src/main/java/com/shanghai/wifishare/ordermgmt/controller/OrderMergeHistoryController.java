package com.shanghai.wifishare.ordermgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shanghai.wifishare.ordermgmt.service.OrderMergeHistoryService;
import com.wifishared.common.data.otd.order.OrdersConnectRspItem;
import com.wifishared.common.data.otd.order.OrdersShareRspItem;
import com.wifishared.common.framework.resultobj.GeneralContentResult;
import com.wifishared.common.framework.resultobj.GeneralPagingResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="历史订单信息")
@RestController
public class OrderMergeHistoryController {

	@Autowired
	OrderMergeHistoryService orderMergeHistoryService;

	@ApiOperation(value = "查询用户连接的所有订单信息(分页)")
	@RequestMapping(value = "/auth/v1/orders/connect/page/{page}/size/{size}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "Bearer ", required = true),
		@ApiImplicitParam(paramType = "path", name = "page", dataType = "String", required = true, value = "第几页", defaultValue = "1"),
		@ApiImplicitParam(paramType = "path", name = "size", dataType = "String", required = true, value = "每页数量", defaultValue = "10")})
	public GeneralPagingResult<List<OrdersConnectRspItem>> queryConnectOrders(@RequestHeader("Authorization") String authorization,
			@PathVariable("page") String page, @PathVariable("size") String size) {
		return orderMergeHistoryService.queryConnectOrders(authorization,page,size);
	}
	
	@ApiOperation(value = "查询用户分享的所有订单信息(分页)")
	@RequestMapping(value = "/auth/v1/orders/share/page/{page}/size/{size}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "Bearer ", required = true),
		@ApiImplicitParam(paramType = "path", name = "page", dataType = "String", required = true, value = "第几页", defaultValue = "1"),
		@ApiImplicitParam(paramType = "path", name = "size", dataType = "String", required = true, value = "每页数量", defaultValue = "10")})
	public GeneralPagingResult<List<OrdersShareRspItem>> queryShareOrders(@RequestHeader("Authorization") String authorization,
			@PathVariable("page") String page, @PathVariable("size") String size) {
		return orderMergeHistoryService.queryShareOrders(authorization,page,size);
	}
	
	@ApiOperation(value = "查询用户订单信息")
	@RequestMapping(value = "/auth/v1/orders/{orderId}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "Bearer ", required = true) })
	public GeneralContentResult<OrdersConnectRspItem> queryOrderByOrderId(@RequestHeader("Authorization") String authorization,@PathVariable("orderId") String orderId) {
		return orderMergeHistoryService.queryOrderByOrderId(authorization,orderId);
	}

}
