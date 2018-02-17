package com.shanghai.wifishare.usermgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.shanghai.wifishare.usermgmt.service.UserDeviceService;
import com.wifishared.common.data.otd.user.UserDeviceRsp;
import com.wifishared.common.framework.resultobj.GeneralContentResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserDeviceController {

	@Autowired
	UserDeviceService userDeviceService;

	@ApiOperation(value = "查询用户的设备")
	@RequestMapping(value = "/auth/v1/users/{userId}/devices", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralContentResult<List<UserDeviceRsp>> queryDevices(@PathVariable("userId") String userId) {
		return userDeviceService.queryDevices(userId);
	}


}
