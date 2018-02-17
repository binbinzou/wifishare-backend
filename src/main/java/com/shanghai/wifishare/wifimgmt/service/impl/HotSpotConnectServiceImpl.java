package com.shanghai.wifishare.wifimgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shanghai.wifishare.wifimgmt.domain.HotspotConnect;
import com.shanghai.wifishare.wifimgmt.repository.HotspotConnectRepository;
import com.shanghai.wifishare.wifimgmt.service.HotSpotConnectService;
import com.wifishared.common.data.otd.hotspot.HotSpotConnectRsp;
import com.wifishared.common.framework.contant.CommonResultCodeConstant;
import com.wifishared.common.framework.contant.CommonResultMessageConstant;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

@Service
public class HotSpotConnectServiceImpl implements HotSpotConnectService {

	@Autowired
	HotspotConnectRepository hotspotConnectRepository;
	
	@Override
	public GeneralContentResult<List<HotSpotConnectRsp>> findHotspotConnectsByHotSpots(List<String> ids) {
		GeneralContentResult<List<HotSpotConnectRsp>> result = new GeneralContentResult<>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		//找到连接的设备数和连接次数,应该放到下面匹配完成的里面
		List<HotSpotConnectRsp> connectRsps = new ArrayList<>(); 
		List<HotspotConnect> connects = hotspotConnectRepository.findByHotspotconfigIdIn(ids);
		connects.forEach(x -> {
			HotSpotConnectRsp connectRsp = new HotSpotConnectRsp();
			BeanUtils.copyProperties(x, connectRsp);
			connectRsps.add(connectRsp);
		});
		result.setContent(connectRsps);
		return result;
	}

}
