package com.shanghai.wifishare.usermgmt.service;

import java.util.List;

import com.wifishared.common.data.dto.user.LoginReqBody;
import com.wifishared.common.data.dto.user.UserDeviceReq;
import com.wifishared.common.data.otd.user.UserRspMsg;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

public interface UserService {

	GeneralContentResult<String> login(LoginReqBody reqbody);

	GeneralContentResult<UserRspMsg> queryUsersMsg(String authorization);

	GeneralContentResult<List<UserRspMsg>> queryUsers(List<String> userIds);

	GeneralContentResult<String> tokenCheck(String authorization);

}
