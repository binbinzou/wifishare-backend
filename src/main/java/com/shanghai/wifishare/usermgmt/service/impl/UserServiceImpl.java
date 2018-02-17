package com.shanghai.wifishare.usermgmt.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.shanghai.wifishare.usermgmt.constants.UserMgmtConstant;
import com.shanghai.wifishare.usermgmt.domain.User;
import com.shanghai.wifishare.usermgmt.domain.UserDevice;
import com.shanghai.wifishare.usermgmt.repository.UserDeviceRepository;
import com.shanghai.wifishare.usermgmt.repository.UserRepository;
import com.shanghai.wifishare.usermgmt.service.UserService;
import com.shanghai.wifishare.usermgmt.utils.UserMgmtConvertr;
import com.wifishared.common.data.dto.user.LoginReqBody;
import com.wifishared.common.data.otd.user.UserRspMsg;
import com.wifishared.common.framework.contant.CommonConstant;
import com.wifishared.common.framework.contant.CommonResultCodeConstant;
import com.wifishared.common.framework.contant.CommonResultMessageConstant;
import com.wifishared.common.framework.jwt.JwtManager;
import com.wifishared.common.framework.redis.RedisStringManager;
import com.wifishared.common.framework.resultobj.GeneralContentResult;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private RedisStringManager redisManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDeviceRepository userDeviceRepository;
	
	@Override
	public GeneralContentResult<String> login(LoginReqBody reqbody) {
		log.debug("-----------------login begining-------------------");
		log.debug("login message is {}",reqbody);
		GeneralContentResult<String> result = new GeneralContentResult<String>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.LOGIN_SUCCESS);
		//初始化相关对象
		String userId = "";
		String deviceId = "";
		UserDevice userDevice = null;
		User user = null;
		
		boolean hasPhoneNumber = false;
		boolean hasDevice = false;
		//判断出相关的是否有用户和设备
		//取出用户
		user = userRepository.findByPhoneNumber(reqbody.getPhoneNumber());
		if(user!=null) {
			hasPhoneNumber = true;
			userId = user.getId();
			//取出设备
			userDevice = userDeviceRepository.findByUserIdAndDeviceId(userId,reqbody.getDeviceUuid());
			if(userDevice!=null) {
				deviceId = userDevice.getId();
				hasDevice = true;
			}
		}

		String token = null;
		/**
		 * 查询phoneNumber是否在数据库用户表中存在 存在phoneNumber,判断该phoneNumber下面是否有设备号
		 * 存在设备号,则直接根据用户信息生成token。 不存在设备号,则创建设备数据到数据库中,并根据用户信息生成token。
		 * 不存在phoneNumber,创建用户数据,并创建设备数据。再生成token信息。
		 */
		if (hasPhoneNumber) {
			// 存在用户
			if (hasDevice) {
				log.debug("phoneNumber and device exist -------------------");
				// 存在设备->生成token->存到redis中设置过期时间
				log.debug("---userId:{}-----deviceId:{}----",userId,deviceId);
				reqbody.setUserId(userId);
				reqbody.setDeviceId(deviceId);
				token = JwtManager.createJWT("1", "wifishare", JSONObject.toJSONString(reqbody));
				String key = userId + CommonConstant.TOKEN_SPILTE_DEVICEID_USERID + deviceId;
				redisManager.set(key, token, UserMgmtConstant.TOKEN_EXPIRETIME);
			} else {
				log.debug("phoneNumber exist and device not exist-------------------");
				// 不存在设备->创建设备数据->生成token->存到redis中设置过期时间
				UserDevice saveDevice = UserMgmtConvertr.loginReqBody2UserDetail(reqbody, userId);
				saveDevice = userDeviceRepository.save(saveDevice);
				if(!StringUtils.isEmpty(saveDevice.getId())) {
					deviceId = saveDevice.getId();
					log.debug("---userId:{}-----deviceId:{}----",userId,deviceId);
					reqbody.setUserId(userId);
					reqbody.setDeviceId(deviceId);
					token = JwtManager.createJWT("1", "wifishare", JSONObject.toJSONString(reqbody));
					String key = userId + CommonConstant.TOKEN_SPILTE_DEVICEID_USERID + deviceId;
					redisManager.set(key, token, UserMgmtConstant.TOKEN_EXPIRETIME);
				}
				
			}
		} else {
			log.debug("phoneNumber and device not exist-------------------");
			// 不存在用户->创建用户数据->创建设备数据->生成token->存到redis中设置过期时间
			User saveUser = UserMgmtConvertr.loginReqBody2User(reqbody);
			saveUser = userRepository.save(saveUser);
			if(!StringUtils.isEmpty(saveUser.getId())) {
				userId = saveUser.getId();
				UserDevice saveDevice = UserMgmtConvertr.loginReqBody2UserDetail(reqbody, userId);
				saveDevice = userDeviceRepository.save(saveDevice);
				if(!StringUtils.isEmpty(saveDevice.getId())) {
					deviceId = saveDevice.getId();
					log.debug("---userId:{}-----deviceId:{}----",userId,deviceId);
					reqbody.setUserId(userId);
					reqbody.setDeviceId(deviceId);
					token = JwtManager.createJWT("1", "wifishare", JSONObject.toJSONString(reqbody));
					String key = userId + CommonConstant.TOKEN_SPILTE_DEVICEID_USERID + deviceId;
					redisManager.set(key, token, UserMgmtConstant.TOKEN_EXPIRETIME);
				}
			}
		}
		if(StringUtils.isEmpty(token)) {
			result.setCode(CommonResultCodeConstant.LOGIN_ERROR);
			result.setMessage(CommonResultMessageConstant.LOGIN_FAIL);
		}
		result.setContent(token);
		log.debug("-----------------login ending-------------------");
		return result;
	}

	@Override
	public GeneralContentResult<UserRspMsg> queryUsersMsg(String authorization) {
		log.debug("------queryUsersMsg starting:authorization:{}-----------",authorization);
		GeneralContentResult<UserRspMsg> result = new GeneralContentResult<UserRspMsg>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		UserRspMsg userRspMsg = null;
        LoginReqBody reqBody = JwtManager.parseToken(authorization);
        String userId = reqBody.getUserId();
        String deviceId = reqBody.getDeviceId();
        User user =  userRepository.findOne(userId);
        UserDevice userDevice = userDeviceRepository.findOne(deviceId);
        log.debug("---queryUsersMsg user:{},userdevice:{}-----",user,userDevice);
        userRspMsg = UserMgmtConvertr.userAndUserDevice2UserRspMsg(user,userDevice);
        result.setContent(userRspMsg);
		log.debug("-----------------queryUsersMsg ending-------------------");
		return result;
	}

	@Override
	public GeneralContentResult<List<UserRspMsg>> queryUsers(List<String> userIds) {
		GeneralContentResult<List<UserRspMsg>> result = new GeneralContentResult<>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		
		List<User> users = userRepository.findByIdIn(userIds);
		List<UserRspMsg> userRspMsgs = users.stream().map(x -> UserMgmtConvertr.user2UserRspMsg(x)).collect(Collectors.toList());
		result.setContent(userRspMsgs);
		return result;
	}

	@Override
	public GeneralContentResult<String> tokenCheck(String authorization) {
		GeneralContentResult<String> result = new GeneralContentResult<>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			result.setContent(null);
            return result;
        }
        String token = authorization.substring(7);
        try {
        	//取出token中相应的信息
            Claims claims = JwtManager.parseJWT(token);
            String subject = claims.getSubject();
            LoginReqBody reqBody = JSONObject.parseObject(subject, LoginReqBody.class);
            String key = reqBody.getUserId() + CommonConstant.TOKEN_SPILTE_DEVICEID_USERID + reqBody.getDeviceId();
            if(!redisManager.exists(key)) {
            	result.setContent(null);
                return result;
            }
        } catch (final SignatureException e) {
        	e.printStackTrace();
        	result.setContent(null);
            return result;
        } catch (Exception e) {
        	e.printStackTrace();
        	result.setContent(null);
            return result;
		}
        result.setContent("success");
        return result;
	}

}
