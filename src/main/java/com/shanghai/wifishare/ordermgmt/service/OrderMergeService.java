package com.shanghai.wifishare.ordermgmt.service;

import java.util.List;

import com.wifishared.common.data.otd.order.OrdersConnectRspItem;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

public interface OrderMergeService {

	GeneralContentResult<OrdersConnectRspItem> queryNoMergenceOrders(String authorization);

}
