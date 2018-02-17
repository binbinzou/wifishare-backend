package com.shanghai.wifishare.usermgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shanghai.wifishare.usermgmt.domain.UserDevice;
import com.shanghai.wifishare.usermgmt.repository.UserDeviceRepository;
import com.shanghai.wifishare.usermgmt.service.UserDeviceService;
import com.shanghai.wifishare.usermgmt.utils.UserMgmtConvertr;
import com.wifishared.common.data.otd.user.UserDeviceRsp;
import com.wifishared.common.framework.contant.CommonResultCodeConstant;
import com.wifishared.common.framework.contant.CommonResultMessageConstant;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

@Service
public class UserDeviceServiceImpl implements  UserDeviceService {

	@Autowired
	private UserDeviceRepository userDeviceRepository;
	
	@Override
	public GeneralContentResult<List<UserDeviceRsp>> queryDevices(String userId) {
		GeneralContentResult<List<UserDeviceRsp>> result = new GeneralContentResult<List<UserDeviceRsp>>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		List<UserDeviceRsp> deviceRsps = new ArrayList<UserDeviceRsp>();
		List<UserDevice> userDevices = userDeviceRepository.findByUserId(userId);
		for(UserDevice device : userDevices) {
			deviceRsps.add(UserMgmtConvertr.userDevice2UserDeviceRsp(device));
		}
		result.setContent(deviceRsps);
		return result;
	}

}
