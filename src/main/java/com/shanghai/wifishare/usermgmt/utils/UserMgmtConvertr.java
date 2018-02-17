package com.shanghai.wifishare.usermgmt.utils;

import com.shanghai.wifishare.usermgmt.constants.UserMgmtConstant;
import com.shanghai.wifishare.usermgmt.domain.User;
import com.shanghai.wifishare.usermgmt.domain.UserDevice;
import com.wifishared.common.data.dto.user.LoginReqBody;
import com.wifishared.common.data.otd.user.UserDeviceRsp;
import com.wifishared.common.data.otd.user.UserRspMsg;

public class UserMgmtConvertr {

	public static User loginReqBody2User(LoginReqBody reqbody) {
		User user = new User();
		user.setPhoneNumber(reqbody.getPhoneNumber());
		user.setNickName(reqbody.getPhoneNumber());
		user.setStatus(UserMgmtConstant.USER_STATUS_ENABLED);
		return user;
	}
	

	public static UserDevice loginReqBody2UserDetail(LoginReqBody reqbody,String userId) {
		UserDevice userDevice = new UserDevice();
		userDevice.setUserId(userId);
		userDevice.setDeviceId(reqbody.getDeviceUuid());
		userDevice.setDeviceName(reqbody.getDeviceName());
		userDevice.setDeviceSystem(reqbody.getDeviceSystem());
		userDevice.setSystemVersion(reqbody.getSystemVersion());
		userDevice.setStatus(UserMgmtConstant.USER_STATUS_ENABLED);
		return userDevice;
	}
	
	public static UserRspMsg userAndUserDevice2UserRspMsg(User user,UserDevice userDevice) {
		UserRspMsg userRspMsg = new UserRspMsg();
		userRspMsg.setCertifiaction(String.valueOf(user.getCertification()));
		userRspMsg.setDeviceId(userDevice.getDeviceId());
		userRspMsg.setDeviceName(userDevice.getDeviceName());
		userRspMsg.setHeadImg(user.getHeadImg());
		userRspMsg.setName(user.getName());
		userRspMsg.setNickName(user.getNickName());
		userRspMsg.setPhoneNumber(user.getPhoneNumber());
		return userRspMsg;
	}
	
	public static UserDeviceRsp userDevice2UserDeviceRsp(UserDevice userDevice) {
		UserDeviceRsp deviceRsp = new UserDeviceRsp();
		deviceRsp.setDeviceId(userDevice.getDeviceId());
		deviceRsp.setId(userDevice.getId());
		deviceRsp.setDevideName(userDevice.getDeviceName());
		return deviceRsp;
	}


	public static UserRspMsg user2UserRspMsg(User user) {
		UserRspMsg userRspMsg = new UserRspMsg();
		userRspMsg.setUserId(user.getId());
		userRspMsg.setCertifiaction(String.valueOf(user.getCertification()));
		userRspMsg.setHeadImg(user.getHeadImg());
		userRspMsg.setName(user.getName());
		userRspMsg.setNickName(user.getNickName());
		//电话屏蔽中间四位
		userRspMsg.setPhoneNumber(user.getPhoneNumber().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
		return userRspMsg;
	}
	
}
