package com.shanghai.wifishare.ordermgmt.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shanghai.wifishare.ordermgmt.constants.OrderMgmtConstant;
import com.shanghai.wifishare.ordermgmt.domain.OrderDetail;
import com.shanghai.wifishare.ordermgmt.domain.OrderDetailHistory;
import com.shanghai.wifishare.ordermgmt.domain.OrderMerge;
import com.shanghai.wifishare.ordermgmt.domain.OrderMergeHistory;
import com.shanghai.wifishare.ordermgmt.repository.OrderDetailHistoryRepository;
import com.shanghai.wifishare.ordermgmt.repository.OrderDetailRepository;
import com.shanghai.wifishare.ordermgmt.repository.OrderMergeHistoryRepository;
import com.shanghai.wifishare.ordermgmt.repository.OrderMergeRepository;
import com.shanghai.wifishare.ordermgmt.service.OrderDetailService;
import com.shanghai.wifishare.ordermgmt.utils.OrderDetailConverter;
import com.shanghai.wifishare.ordermgmt.utils.OrderMergeConverter;
import com.shanghai.wifishare.ordermgmt.utils.OrderMergeDataCalculate;
import com.shanghai.wifishare.usermgmt.service.UserDeviceService;
import com.shanghai.wifishare.wifimgmt.service.HotSpotService;
import com.wifishared.common.data.dto.orderdetail.OrderDetailReqBody;
import com.wifishared.common.data.dto.user.LoginReqBody;
import com.wifishared.common.data.otd.hotspot.HotSpotRspBody;
import com.wifishared.common.data.otd.order.OrdersConnectRspItem;
import com.wifishared.common.data.otd.user.UserDeviceRsp;
import com.wifishared.common.framework.contant.CommonResultCodeConstant;
import com.wifishared.common.framework.contant.CommonResultMessageConstant;
import com.wifishared.common.framework.jwt.JwtManager;
import com.wifishared.common.framework.mq.Producer;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	OrderMergeRepository orderMergeRepository;
	
	@Autowired
	OrderDetailHistoryRepository orderDetailHistoryRepository;
	
	@Autowired
	OrderMergeHistoryRepository orderMergeHistoryRepository;
	
	@Autowired  
    Producer producer; 
	
	@Autowired
	HotSpotService hotSpotService;
	
	@Autowired
	UserDeviceService userDeviceService;

	
	@Override
	public GeneralContentResult<String> createOrderDetail(String authorization,OrderDetailReqBody orderDetailReqBody) {
		LoginReqBody reqBody = JwtManager.parseToken(authorization);
        String userId = reqBody.getUserId();
        String deviceId = reqBody.getDeviceId();
		GeneralContentResult<String> result = new  GeneralContentResult<String>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date time = new Date();
		String dateStr = sdf.format(time);  
		Timestamp timestamp = Timestamp.valueOf(dateStr);
		if(OrderMgmtConstant.ORDER_DETAIL_TYPE_BEGIN==Short.parseShort(orderDetailReqBody.getType())) {
			//存储细节订单
			//存储merge订单
			OrderMerge orderMerge =  OrderMergeConverter.hotSpotReqBody2OrderMerge(orderDetailReqBody);
			orderMerge.setBeginTime(timestamp);
			orderMerge.setCreator(userId);
			orderMerge.setDeviceId(deviceId);
			orderMerge.setStatus(OrderMgmtConstant.ORDER_MERGE_BEGINING);
			orderMerge = orderMergeRepository.save(orderMerge);
			OrderDetail orderDetail = OrderDetailConverter.hotSpotReqBody2Hotspotconfig(orderDetailReqBody);
			orderDetail.setCreator(userId);
			orderDetail.setDeviceId(deviceId);
			orderDetail.setOrderId(orderMerge.getId());
			orderDetail = orderDetailRepository.save(orderDetail);
			if(StringUtils.isEmpty(orderMerge.getId())||StringUtils.isEmpty(orderDetail.getId())) {
				//没插入数据。回滚
				throw new RuntimeException();
			}
			result.setContent(orderMerge.getId());
		}else if(OrderMgmtConstant.ORDER_DETAIL_TYPE_END==Short.parseShort(orderDetailReqBody.getType())) {
			//新增或者更新结束订单
			OrderDetail orderDetailTmp = orderDetailRepository.findByOrderIdAndOrderDetailType(orderDetailReqBody.getOrderMergerId(),OrderMgmtConstant.ORDER_DETAIL_TYPE_END);
			
			if(orderDetailTmp==null) {
				//新增
				OrderDetail orderDetail = OrderDetailConverter.hotSpotReqBody2Hotspotconfig(orderDetailReqBody);
				orderDetail.setCreator(userId);
				orderDetail.setDeviceId(deviceId);
				orderDetail.setOrderId(orderDetailReqBody.getOrderMergerId());
				orderDetail = orderDetailRepository.save(orderDetail);
				if(StringUtils.isEmpty(orderDetail.getId())) {
					//没插入数据。回滚
					throw new RuntimeException();
				}
			}else {
				//更新
				OrderDetail orderDetail = OrderDetailConverter.hotSpotReqBody2Hotspotconfig(orderDetailReqBody);
				orderDetail.setId(orderDetailTmp.getId());
				orderDetail.setOrderId(orderDetailTmp.getOrderId());
				orderDetail.setCreator(orderDetailTmp.getCreator());
				orderDetail.setDeviceId(orderDetailTmp.getDeviceId());
				orderDetail = orderDetailRepository.save(orderDetail);
				if(StringUtils.isEmpty(orderDetail.getId())) {
					//没插入数据。回滚
					throw new RuntimeException();
				}
			}
			result.setContent(orderDetailReqBody.getOrderMergerId());
			/*//发送mq并计算订单。
			
			if(StringUtils.isEmpty(orderDetail.getId())) {
				//没插入数据。回滚
				throw new RuntimeException();
			}else {
				Destination destination = new ActiveMQQueue(OrderMgmtConstant.ORDER_MERGE_QUEUE); 
				producer.sendMessage(destination, orderDetailReqBody.getOrderMergerId());  
			}*/
			
		}
		return result;
	}

	@Override
	public GeneralContentResult<OrdersConnectRspItem> createOrderDetailForResult(String authorization,
			OrderDetailReqBody orderDetailReqBody) {
		LoginReqBody reqBody = JwtManager.parseToken(authorization);
        String userId = reqBody.getUserId();
        String deviceId = reqBody.getDeviceId();
		GeneralContentResult<OrdersConnectRspItem> result = new  GeneralContentResult<OrdersConnectRspItem>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date time = new Date();
		String dateStr = sdf.format(time);  
		Timestamp timestamp = Timestamp.valueOf(dateStr);
		//新增或者更新结束订单
		OrderDetail orderDetailTmp = orderDetailRepository.findByOrderIdAndOrderDetailType(orderDetailReqBody.getOrderMergerId(),OrderMgmtConstant.ORDER_DETAIL_TYPE_END);
		OrderDetail orderDetail = null;
		if(orderDetailTmp==null) {
			//新增
			orderDetail = OrderDetailConverter.hotSpotReqBody2Hotspotconfig(orderDetailReqBody);
			orderDetail.setCreator(userId);
			orderDetail.setDeviceId(deviceId);
			orderDetail.setOrderId(orderDetailReqBody.getOrderMergerId());
			orderDetail.setUpdateTime(timestamp);
			orderDetail = orderDetailRepository.save(orderDetail);
			if(StringUtils.isEmpty(orderDetail.getId())) {
				//没插入数据。回滚
				throw new RuntimeException();
			}
		}else {
			//更新
			orderDetail = OrderDetailConverter.hotSpotReqBody2Hotspotconfig(orderDetailReqBody);
			orderDetail.setId(orderDetailTmp.getId());
			orderDetail.setOrderId(orderDetailTmp.getOrderId());
			orderDetail.setCreator(orderDetailTmp.getCreator());
			orderDetail.setDeviceId(orderDetailTmp.getDeviceId());
			orderDetail.setUpdateTime(timestamp);
			orderDetail = orderDetailRepository.save(orderDetail);
			if(StringUtils.isEmpty(orderDetail.getId())) {
				//没插入数据。回滚
				throw new RuntimeException();
			}
		}
		/**
		 * 1、拿出订单细节表的两条数据，以及自身的订单数据 2、计算订单数据 3、将三条数据移到相应的历史表中。
		 */
		OrderDetail begin = null;
		OrderDetail end = null;
		HotSpotRspBody rspBody = null;
		String orderMergeId = orderDetail.getOrderId();
		List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderMergeId);
		for (OrderDetail detail : orderDetails) {
			if (OrderMgmtConstant.ORDER_DETAIL_TYPE_BEGIN == detail.getOrderDetailType()) {
				begin = detail;
			}
		}
		end = orderDetail;
		OrderMerge orderMerge = new OrderMerge();
		if(begin!=null&&end!=null) {
			//拿出自身的order数据
			orderMerge = orderMergeRepository.findByIdAndStatus(orderMergeId,OrderMgmtConstant.ORDER_MERGE_BEGINING);
			if(orderMerge!=null) {
				//计算订单数据
				GeneralContentResult<HotSpotRspBody> resultTmp = hotSpotService.findHotSpot(orderMerge.getHotspotconfigId());
				rspBody = resultTmp.getContent();
				if(rspBody!=null) {
					orderMerge = OrderMergeDataCalculate.calculate(begin, end, orderMerge,rspBody);
					//复制到相应的历史表中
					orderMergeRepository.delete(orderMerge.getId());
					orderDetailRepository.delete(begin.getId());
					orderDetailRepository.delete(end.getId());
					OrderDetailHistory beginHis = OrderDetailConverter.orderDetail2History(begin);
					OrderDetailHistory endHis = OrderDetailConverter.orderDetail2History(end);
					OrderMergeHistory mergeHis = OrderMergeConverter.orderMerge2History(orderMerge);
					orderDetailHistoryRepository.save(beginHis);
					orderDetailHistoryRepository.save(endHis);
					orderMergeHistoryRepository.save(mergeHis);
				}else {
					log.warn("merge order fail,can not find hotspot,orderId:{}",orderMergeId);
				}
			}else {
				log.warn("merge order fail,can not find order,orderId:{}",orderMergeId);
			}
		}else {
			log.warn("merge order fail,can not find order detail,orderId:{}",orderMergeId);
		}
		
		List<OrderMergeHistory> mergeHistories = orderMergeHistoryRepository.findByCreator(userId);
		GeneralContentResult<List<UserDeviceRsp>> resultUserDevice = userDeviceService.queryDevices(userId);
		List<UserDeviceRsp> rsps = resultUserDevice.getContent();
		Map<String, UserDeviceRsp> map = new HashMap<String, UserDeviceRsp>();
		if (rsps != null) {
			for (UserDeviceRsp rsp : rsps) {
				map.put(rsp.getId(), rsp);
			}
		}
		
		UserDeviceRsp deviceRsp = map.get(deviceId);
		OrdersConnectRspItem ordersRspItem = OrderMergeConverter.mergeAndDeviceRsp2OrdersRspItem(orderMerge, deviceRsp);
		rspBody.setPassword("");
		ordersRspItem.setHotSpotRspBody(rspBody);
		result.setContent(ordersRspItem);
		
		return result;
	}

}
