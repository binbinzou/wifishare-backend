package com.shanghai.wifishare.ordermgmt.service.impl;
import org.springframework.stereotype.Component;

/*
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.shanghai.wifishare.ordermgmt.constants.OrderMgmtConstant;
import com.shanghai.wifishare.ordermgmt.domain.OrderDetail;
import com.shanghai.wifishare.ordermgmt.domain.OrderDetailHistory;
import com.shanghai.wifishare.ordermgmt.domain.OrderMerge;
import com.shanghai.wifishare.ordermgmt.domain.OrderMergeHistory;
import com.shanghai.wifishare.ordermgmt.repository.OrderDetailHistoryRepository;
import com.shanghai.wifishare.ordermgmt.repository.OrderDetailRepository;
import com.shanghai.wifishare.ordermgmt.repository.OrderMergeHistoryRepository;
import com.shanghai.wifishare.ordermgmt.repository.OrderMergeRepository;
import com.shanghai.wifishare.ordermgmt.utils.OrderDetailConverter;
import com.shanghai.wifishare.ordermgmt.utils.OrderMergeConverter;
import com.shanghai.wifishare.ordermgmt.utils.OrderMergeDataCalculate;
import com.shanghai.wifishare.wifimgmt.service.HotSpotService;
import com.wifishared.common.data.otd.hotspot.HotSpotRspBody;
import com.wifishared.common.framework.resultobj.GeneralContentResult;
*/
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderConsumer {

	/*@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	OrderMergeRepository orderMergeRepository;
	
	@Autowired
	OrderDetailHistoryRepository orderDetailHistoryRepository;
	
	@Autowired
	OrderMergeHistoryRepository orderMergeHistoryRepository;
	
	@Autowired
	HotSpotService hotSpotService;
	
	// 使用JmsListener配置消费者监听的队列，其中orderMergeId是接收到的订单id
	@JmsListener(destination = "order.merge")
	@Transactional
	public void receiveQueue(String orderMergeId) {
		log.info("receive merge order,orderId:{}",orderMergeId);
		*//**
		 * 1、拿出订单细节表的两条数据，以及自身的订单数据 2、计算订单数据 3、将三条数据移到相应的历史表中。
		 *//*
		OrderDetail begin = null;
		OrderDetail end = null;
		List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderMergeId);
		for (OrderDetail detail : orderDetails) {
			if (OrderMgmtConstant.ORDER_DETAIL_TYPE_BEGIN == detail.getOrderDetailType()) {
				begin = detail;
			} else if (OrderMgmtConstant.ORDER_DETAIL_TYPE_END == detail.getOrderDetailType()) {
				end = detail;
			}
		}
		if(begin!=null&&end!=null) {
			//拿出自身的order数据
			OrderMerge orderMerge = orderMergeRepository.findByIdAndStatus(orderMergeId,OrderMgmtConstant.ORDER_MERGE_BEGINING);
			if(orderMerge!=null) {
				//计算订单数据
				GeneralContentResult<HotSpotRspBody> result = hotSpotService.findHotSpot(orderMerge.getHotspotconfigId());
				HotSpotRspBody rspBody = result.getContent();
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
	}
*/
}
