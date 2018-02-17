package com.shanghai.wifishare.wifimgmt.service;

import com.wifishared.common.data.dto.hotspot.HotSpotADReqBody;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

public interface HotSpotADService {

	GeneralContentResult<String> createHotSpotAD(HotSpotADReqBody hotSpotADReqBody);

}
