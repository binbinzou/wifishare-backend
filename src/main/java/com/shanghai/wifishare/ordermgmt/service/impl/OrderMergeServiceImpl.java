package com.shanghai.wifishare.ordermgmt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shanghai.wifishare.ordermgmt.domain.OrderMerge;
import com.shanghai.wifishare.ordermgmt.repository.OrderMergeRepository;
import com.shanghai.wifishare.ordermgmt.service.OrderMergeService;
import com.shanghai.wifishare.ordermgmt.utils.OrderMergeConverter;
import com.wifishared.common.data.dto.user.LoginReqBody;
import com.wifishared.common.data.otd.order.OrdersConnectRspItem;
import com.wifishared.common.framework.contant.CommonResultCodeConstant;
import com.wifishared.common.framework.contant.CommonResultMessageConstant;
import com.wifishared.common.framework.jwt.JwtManager;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

@Service
public class OrderMergeServiceImpl implements OrderMergeService{

	@Autowired
	OrderMergeRepository orderMergeRepository;
	
	@Override
	public GeneralContentResult<OrdersConnectRspItem> queryNoMergenceOrders(String authorization) {
		GeneralContentResult<OrdersConnectRspItem> result = new GeneralContentResult<OrdersConnectRspItem>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		
		LoginReqBody reqBody = JwtManager.parseToken(authorization);
        String userId = reqBody.getUserId();
        String deviceId = reqBody.getDeviceId();
        List<OrderMerge> merges = orderMergeRepository.findByDeviceIdAndCreator(deviceId,userId);
        if(merges.size()==0) {
            result.setContent(null);
            return result;
        }
        OrdersConnectRspItem ordersRspItems = new OrdersConnectRspItem();
        for(OrderMerge merge : merges) {
        	ordersRspItems = OrderMergeConverter.orderMerge2OrdersRspItem(merge);
		}
        
        result.setContent(ordersRspItems);
		return result;
	}

}
