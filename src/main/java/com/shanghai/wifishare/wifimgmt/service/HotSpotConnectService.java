package com.shanghai.wifishare.wifimgmt.service;

import java.util.List;

import com.wifishared.common.data.otd.hotspot.HotSpotConnectRsp;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

public interface HotSpotConnectService {

	GeneralContentResult<List<HotSpotConnectRsp>> findHotspotConnectsByHotSpots(List<String> ids);

}
