package com.shanghai.wifishare.ordermgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shanghai.wifishare.ordermgmt.domain.OrderComment;
import com.shanghai.wifishare.ordermgmt.repository.OrderCommentRepository;
import com.shanghai.wifishare.ordermgmt.service.OrderCommentService;
import com.shanghai.wifishare.ordermgmt.utils.OrderCommentConvert;
import com.wifishared.common.data.dto.hotspot.HotSpotCommentReqBody;
import com.wifishared.common.data.otd.hotspot.HotSpotCommentRspBody;
import com.wifishared.common.framework.contant.CommonResultCodeConstant;
import com.wifishared.common.framework.contant.CommonResultMessageConstant;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderCommentServiceImpl implements OrderCommentService{

	@Autowired
	OrderCommentRepository orderCommentRepository;
	
	@Override
	public GeneralContentResult<List<HotSpotCommentRspBody>> queryHotspotComments(List<String> hotspotIds) {
		GeneralContentResult<List<HotSpotCommentRspBody>> result = new GeneralContentResult<List<HotSpotCommentRspBody>>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		
		List<OrderComment> comments = orderCommentRepository.findByHotspotconfigIdIn(hotspotIds);
		List<HotSpotCommentRspBody> hotSpotCommentRspBodies = new ArrayList<>();
		comments.forEach(x -> {
			hotSpotCommentRspBodies.add(OrderCommentConvert.orderComment2HotSpotCommentRspBody(x));
		});
		result.setContent(hotSpotCommentRspBodies);
		return result;
	}

	@Override
	public GeneralContentResult<String> createHotspotComments(HotSpotCommentReqBody reqBody) {
		GeneralContentResult<String> result = new GeneralContentResult<>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		OrderComment comment = OrderCommentConvert.hotSpotCommentReqBody2OrderComment(reqBody);
		comment = orderCommentRepository.save(comment);
		result.setContent(comment.getId());
		return result;
	}

}
