package com.shanghai.wifishare.usermgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shanghai.wifishare.usermgmt.service.UserService;
import com.wifishared.common.data.dto.user.LoginReqBody;
import com.wifishared.common.data.otd.user.UserRspMsg;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/auth/v1/users/self", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "Bearer ", required=true) })
	public GeneralContentResult<UserRspMsg> queryUsersMsg(@RequestHeader("Authorization") String authorization) {
		return userService.queryUsersMsg(authorization);
	}

	@RequestMapping(value = "/noauth/v1/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public GeneralContentResult<String> login(@RequestBody LoginReqBody reqbody) {
		return userService.login(reqbody);
	}
	
	@RequestMapping(value = "/auth/v1/users/self", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "Bearer ", required=true) })
	public GeneralContentResult<List<UserRspMsg>> queryUsers(@RequestBody List<String> userIds) {
		return userService.queryUsers(userIds);
	}

	@RequestMapping(value = "/noauth/vc/tokencheck", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "Bearer ", required = true) })
	public GeneralContentResult<String> checkTokenNoAuth(@RequestHeader("Authorization") String authorization) {
		return userService.tokenCheck(authorization);
	}

	
}
