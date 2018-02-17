package com.shanghai.wifishare.ordermgmt.service;

import java.util.List;

import com.wifishared.common.data.otd.order.OrdersConnectRspItem;
import com.wifishared.common.data.otd.order.OrdersShareRspItem;
import com.wifishared.common.framework.resultobj.GeneralContentResult;
import com.wifishared.common.framework.resultobj.GeneralPagingResult;

public interface OrderMergeHistoryService {


	GeneralContentResult<OrdersConnectRspItem> queryOrderByOrderId(String authorization,String orderId);

	GeneralPagingResult<List<OrdersConnectRspItem>> queryConnectOrders(String authorization, String page, String size);

	GeneralPagingResult<List<OrdersShareRspItem>> queryShareOrders(String authorization,String page, String size);

}
