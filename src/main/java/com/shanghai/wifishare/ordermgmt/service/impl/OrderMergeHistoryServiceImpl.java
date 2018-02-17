package com.shanghai.wifishare.ordermgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.shanghai.wifishare.ordermgmt.domain.OrderComment;
import com.shanghai.wifishare.ordermgmt.domain.OrderMergeHistory;
import com.shanghai.wifishare.ordermgmt.repository.OrderCommentRepository;
import com.shanghai.wifishare.ordermgmt.repository.OrderMergeHistoryRepository;
import com.shanghai.wifishare.ordermgmt.service.OrderMergeHistoryService;
import com.shanghai.wifishare.ordermgmt.utils.OrderMergeConverter;
import com.shanghai.wifishare.usermgmt.service.UserDeviceService;
import com.shanghai.wifishare.usermgmt.service.UserService;
import com.shanghai.wifishare.wifimgmt.service.HotSpotService;
import com.wifishared.common.data.dto.user.LoginReqBody;
import com.wifishared.common.data.dto.user.UserDeviceReq;
import com.wifishared.common.data.otd.hotspot.HotSpotRspBody;
import com.wifishared.common.data.otd.order.OrdersConnectRspItem;
import com.wifishared.common.data.otd.order.OrdersShareRspItem;
import com.wifishared.common.data.otd.user.UserDeviceRsp;
import com.wifishared.common.data.otd.user.UserRspMsg;
import com.wifishared.common.framework.contant.CommonResultCodeConstant;
import com.wifishared.common.framework.contant.CommonResultMessageConstant;
import com.wifishared.common.framework.jwt.JwtManager;
import com.wifishared.common.framework.page.PageInfo;
import com.wifishared.common.framework.resultobj.GeneralContentResult;
import com.wifishared.common.framework.resultobj.GeneralPagingResult;

@Service
public class OrderMergeHistoryServiceImpl implements OrderMergeHistoryService {

	@Autowired
	UserDeviceService userDeviceService;
	
	@Autowired
	OrderMergeHistoryRepository orderMergeHistoryRepository;
	
	@Autowired
	OrderCommentRepository orderCommentRepository;
	
	@Autowired
	HotSpotService hotSpotService;
	
	@Autowired
	UserService userService;
	
	@Override
	public GeneralPagingResult<List<OrdersConnectRspItem>> queryConnectOrders(String authorization, String page, String size) {
		GeneralPagingResult<List<OrdersConnectRspItem>> result = new GeneralPagingResult<List<OrdersConnectRspItem>>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		
		List<OrdersConnectRspItem> rspItems = new ArrayList<OrdersConnectRspItem>();
		LoginReqBody reqBody = JwtManager.parseToken(authorization);
		String userId = reqBody.getUserId();
		
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.DESC, "createTime"));
		Sort sort = new Sort(orders); 
		Pageable request = new PageRequest(Integer.parseInt(page) - 1,
				Integer.parseInt(size),sort);
		Page<OrderMergeHistory> pg = orderMergeHistoryRepository.findByCreator(userId,request);
		
		PageInfo pageInfo = new PageInfo();
		if (pg != null && pg.getContent().size() > 0) {
			
			pageInfo.setPage(pg.getNumber() + 1);
			pageInfo.setCount(Integer.parseInt(size));
			pageInfo.setTotalcount((int) pg.getTotalElements());
			pageInfo.setTotalpage(pg.getTotalPages());
			
			List<OrderMergeHistory> mergeHistories = pg.getContent();
			List<String> orderMergeIds = mergeHistories.stream().map(OrderMergeHistory::getId).collect(Collectors.toList());
			Set<String> hotspotIds = mergeHistories.stream().map(OrderMergeHistory::getHotspotconfigId).collect(Collectors.toSet());

			
			//获取用户的所有设备信息
			GeneralContentResult<List<UserDeviceRsp>> resultUserDevice = userDeviceService.queryDevices(userId);
			List<UserDeviceRsp> rsps = resultUserDevice.getContent();
			Map<String, UserDeviceRsp> map = new HashMap<String, UserDeviceRsp>();
			if (rsps != null) {
				for (UserDeviceRsp rsp : rsps) {
					map.put(rsp.getId(), rsp);
				}
			}
			
			//获取用户的所有wifi信息
			GeneralContentResult<List<HotSpotRspBody>>  resultHotspots = hotSpotService.findHotSpots(hotspotIds);
			List<HotSpotRspBody> rspHotspots = resultHotspots.getContent();
			Map<String, HotSpotRspBody> hotSpotMap = new HashMap<String, HotSpotRspBody>();
			if (rspHotspots != null) {
				for (HotSpotRspBody rsp : rspHotspots) {
					hotSpotMap.put(rsp.getId(), rsp);
				}
			}
			
			//获取所有订单的评价信息
			List<OrderComment> comments = orderCommentRepository.findByOrderHistoryIdIn(orderMergeIds);
			List<String> isCommented = comments.stream().map(OrderComment::getOrderHistoryId).collect(Collectors.toList());
			
			
			for(OrderMergeHistory mergeHistory : mergeHistories) {
				OrdersConnectRspItem ordersRspItem = new OrdersConnectRspItem();
				String deviceId = mergeHistory.getDeviceId();
				UserDeviceRsp deviceRsp = map.get(deviceId);
				HotSpotRspBody hotSpotRspBody = hotSpotMap.get(mergeHistory.getHotspotconfigId());
				ordersRspItem = OrderMergeConverter.mergeHistoryAndDeviceRsp2OrdersConnectRspItem(mergeHistory, deviceRsp,isCommented);
				ordersRspItem.setHotSpotRspBody(hotSpotRspBody);
				rspItems.add(ordersRspItem);
			}
		}
		result.setPageInfo(pageInfo);
		result.setContent(rspItems);
		return result;
	}
	
	@Override
	public GeneralPagingResult<List<OrdersShareRspItem>> queryShareOrders(String authorization,String page, String size) {
		GeneralPagingResult<List<OrdersShareRspItem>> result = new GeneralPagingResult<List<OrdersShareRspItem>>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		
		List<OrdersShareRspItem> rspItems = new ArrayList<>();
		PageInfo pageInfo = new PageInfo();
		
		GeneralContentResult<List<HotSpotRspBody>> resultTmp = hotSpotService.findHotSpotShareBySelf(authorization);
		if(resultTmp!=null&&resultTmp.getContent()!=null&&resultTmp.getContent().size()>0) {
			List<String> hotspotIds = resultTmp.getContent().stream().map(HotSpotRspBody::getId).collect(Collectors.toList());
			Map<String,HotSpotRspBody> hotspotMap = resultTmp.getContent().stream().collect(Collectors.toMap(HotSpotRspBody::getId, x -> x));
			List<Order> orders = new ArrayList<>();
			orders.add(new Order(Direction.DESC, "createTime"));
			Sort sort = new Sort(orders); 
			Pageable request = new PageRequest(Integer.parseInt(page) - 1,
					Integer.parseInt(size),sort);
			Page<OrderMergeHistory> pg = orderMergeHistoryRepository.findByHotspotconfigIdIn(hotspotIds,request);
			
			if (pg != null && pg.getContent().size() > 0) {
				
				pageInfo.setPage(pg.getNumber() + 1);
				pageInfo.setCount(Integer.parseInt(size));
				pageInfo.setTotalcount((int) pg.getTotalElements());
				pageInfo.setTotalpage(pg.getTotalPages());
				
				List<OrderMergeHistory> mergeHistories = pg.getContent();
				List<String> creators = mergeHistories.stream().map(OrderMergeHistory::getCreator).collect(Collectors.toList());

				
				//获取用户的所有设备信息
				GeneralContentResult<List<UserRspMsg>> resultUser = userService.queryUsers(creators);
				List<UserRspMsg> rsps = resultUser.getContent();
				Map<String, UserRspMsg> map = new HashMap<String, UserRspMsg>();
				if (rsps != null) {
					for (UserRspMsg rsp : rsps) {
						map.put(rsp.getUserId(), rsp);
					}
				}
				
				
				for(OrderMergeHistory mergeHistory : mergeHistories) {
					OrdersShareRspItem ordersRspItem = new OrdersShareRspItem();
					String userId = mergeHistory.getCreator();
					UserRspMsg userRsp = map.get(userId);
					HotSpotRspBody hotSpotRspBody = hotspotMap.get(mergeHistory.getHotspotconfigId());
					ordersRspItem = OrderMergeConverter.mergeHistoryAndUserRspMsg2OrdersShareRspItem(mergeHistory, userRsp);
					ordersRspItem.setHotSpotRspBody(hotSpotRspBody);
					rspItems.add(ordersRspItem);
				}
			}
		}
		result.setPageInfo(pageInfo);
		result.setContent(rspItems);
		return result;
	}

	@Override
	public GeneralContentResult<OrdersConnectRspItem> queryOrderByOrderId(String authorization,String orderId) {
		GeneralContentResult<OrdersConnectRspItem> result = new GeneralContentResult<OrdersConnectRspItem>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		OrderMergeHistory mergeHistory = orderMergeHistoryRepository.findOne(orderId);
		LoginReqBody reqBody = JwtManager.parseToken(authorization);
		String userId = reqBody.getUserId();
		GeneralContentResult<List<UserDeviceRsp>> resultUserDevice = userDeviceService.queryDevices(userId);
		List<UserDeviceRsp> rsps = resultUserDevice.getContent();
		Map<String, UserDeviceRsp> map = new HashMap<String, UserDeviceRsp>();
		if (rsps != null) {
			for (UserDeviceRsp rsp : rsps) {
				map.put(rsp.getId(), rsp);
			}
		}
		String deviceId = mergeHistory.getDeviceId();
		UserDeviceRsp deviceRsp = map.get(deviceId);
		OrdersConnectRspItem ordersRspItem = OrderMergeConverter.mergeHistoryAndDeviceRsp2OrdersConnectRspItem(mergeHistory, deviceRsp,new ArrayList<String>());
		result.setContent(ordersRspItem);
		return result;
	}

	private UserDeviceReq userId2UserDeviceReq(String userId) {
		UserDeviceReq req = new UserDeviceReq();
		req.setUserId(userId);
		return req;
	}
	
}
