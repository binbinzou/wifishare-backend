package com.shanghai.wifishare.wifimgmt.service;

import java.util.List;
import java.util.Set;

import com.wifishared.common.data.dto.hotspot.HotSpotReqBody;
import com.wifishared.common.data.dto.hotspot.HotSpotReqParam;
import com.wifishared.common.data.otd.hotspot.HotSpotRspBody;
import com.wifishared.common.framework.resultobj.GeneralContentResult;
import com.wifishared.common.framework.resultobj.GeneralResult;

public interface HotSpotService {

	GeneralContentResult<String> createHotSpot(HotSpotReqBody hotSpotReqBody);

	GeneralResult updateHotSpot(String hotspotId, HotSpotReqBody hotSpotReqBody);

	GeneralResult deleteHotSpot(String bookcaseId);

	GeneralContentResult<List<HotSpotRspBody>> findHotSpotBySomeAttr(HotSpotReqParam param);

	GeneralResult disableHotSpot(String hotspotId);

	GeneralContentResult<HotSpotRspBody> findHotSpot(String hotspotId);

	GeneralContentResult<List<HotSpotRspBody>> findHotSpotShareBySelf(String authorization);

	GeneralContentResult<List<HotSpotRspBody>> findHotSpots(Set<String> ids);

}
