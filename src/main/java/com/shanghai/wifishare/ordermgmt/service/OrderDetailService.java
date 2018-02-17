package com.shanghai.wifishare.ordermgmt.service;

import com.wifishared.common.data.dto.orderdetail.OrderDetailReqBody;
import com.wifishared.common.data.otd.order.OrdersConnectRspItem;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

public interface OrderDetailService {

	GeneralContentResult<String> createOrderDetail( String authorization,OrderDetailReqBody orderDetailReqBody);

	GeneralContentResult<OrdersConnectRspItem> createOrderDetailForResult(String authorization,
			OrderDetailReqBody orderDetailReqBody);

}
