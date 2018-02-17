package com.shanghai.wifishare.ordermgmt.service;

import java.util.List;

import com.wifishared.common.data.dto.hotspot.HotSpotCommentReqBody;
import com.wifishared.common.data.otd.hotspot.HotSpotCommentRspBody;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

public interface OrderCommentService {

	GeneralContentResult<List<HotSpotCommentRspBody>> queryHotspotComments(List<String> hotspotId);

	GeneralContentResult<String> createHotspotComments(HotSpotCommentReqBody reqBody);

}
