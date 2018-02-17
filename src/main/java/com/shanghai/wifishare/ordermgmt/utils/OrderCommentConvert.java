package com.shanghai.wifishare.ordermgmt.utils;

import org.springframework.beans.BeanUtils;

import com.shanghai.wifishare.ordermgmt.domain.OrderComment;
import com.wifishared.common.data.dto.hotspot.HotSpotCommentReqBody;
import com.wifishared.common.data.otd.hotspot.HotSpotCommentRspBody;

public class OrderCommentConvert {

	public static HotSpotCommentRspBody orderComment2HotSpotCommentRspBody(OrderComment orderComment) {
		HotSpotCommentRspBody body = new HotSpotCommentRspBody();
		BeanUtils.copyProperties(orderComment, body);
		return body;
	}
	
	public static OrderComment hotSpotCommentReqBody2OrderComment(HotSpotCommentReqBody body ) {
		OrderComment comment = new OrderComment();
		comment.setHotspotconfigId(body.getHotspotconfigId());
		comment.setOrderHistoryId(body.getOrderHistoryId());
		comment.setSpeedScore(body.getSpeedScore());
		comment.setStableScore(body.getStableScore());
		comment.setStrengthScore(body.getStrengthScore());
		return comment;
	}
	
}
