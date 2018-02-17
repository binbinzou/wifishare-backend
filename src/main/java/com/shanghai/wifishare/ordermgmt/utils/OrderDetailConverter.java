package com.shanghai.wifishare.ordermgmt.utils;

import java.math.BigInteger;

import com.shanghai.wifishare.ordermgmt.domain.OrderDetail;
import com.shanghai.wifishare.ordermgmt.domain.OrderDetailHistory;
import com.wifishared.common.data.dto.orderdetail.OrderDetailReqBody;

public class OrderDetailConverter {

	public static OrderDetail hotSpotReqBody2Hotspotconfig(OrderDetailReqBody reqBody){
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderDetailType(Short.parseShort(reqBody.getType()));
		orderDetail.setWifiData(BigInteger.valueOf(Long.parseLong(reqBody.getWifiData())));
		return orderDetail;
	}
	
	public static OrderDetailHistory orderDetail2History(OrderDetail orderDetail) {
		OrderDetailHistory history = new OrderDetailHistory();
		history.setUpdateTime(orderDetail.getUpdateTime());
		history.setCreator(orderDetail.getCreator());
		history.setDeviceId(orderDetail.getDeviceId());
		history.setOrderDetailType(orderDetail.getOrderDetailType());
		history.setOrderId(orderDetail.getOrderId());
		history.setWifiData(orderDetail.getWifiData());
		return history;
	}

}
