package com.shanghai.wifishare.wifimgmt.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shanghai.wifishare.ordermgmt.service.OrderCommentService;
import com.shanghai.wifishare.wifimgmt.constants.HotSpotConstant;
import com.shanghai.wifishare.wifimgmt.domain.HotspotConnect;
import com.shanghai.wifishare.wifimgmt.domain.Hotspotconfig;
import com.shanghai.wifishare.wifimgmt.repository.HotspotConnectRepository;
import com.shanghai.wifishare.wifimgmt.repository.HotspotconfigRepository;
import com.shanghai.wifishare.wifimgmt.service.HotSpotConnectService;
import com.shanghai.wifishare.wifimgmt.service.HotSpotService;
import com.shanghai.wifishare.wifimgmt.utils.HotspotconfigConverter;
import com.wifishared.common.data.dto.hotspot.HotSpotOtherMsg;
import com.wifishared.common.data.dto.hotspot.HotSpotReqBody;
import com.wifishared.common.data.dto.hotspot.HotSpotReqParam;
import com.wifishared.common.data.dto.hotspot.WifiReqParam;
import com.wifishared.common.data.otd.hotspot.HotSpotCommentRspBody;
import com.wifishared.common.data.otd.hotspot.HotSpotConnectRsp;
import com.wifishared.common.data.otd.hotspot.HotSpotRspBody;
import com.wifishared.common.framework.contant.CommonResultCodeConstant;
import com.wifishared.common.framework.contant.CommonResultMessageConstant;
import com.wifishared.common.framework.jwt.JwtManager;
import com.wifishared.common.framework.resultobj.GeneralContentResult;
import com.wifishared.common.framework.resultobj.GeneralResult;
import com.wifishared.common.framework.rsa.RSACoder;

@Service
public class HotSpotServiceImpl implements HotSpotService {

	@Autowired
	HotspotconfigRepository hotspotconfigRepository;
	@Autowired
	HotspotConnectRepository hotspotConnectRepository;
	@Autowired
	OrderCommentService orderCommentService;
	@Autowired
	HotSpotConnectService hotSpotConnectService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GeneralContentResult<String> createHotSpot(HotSpotReqBody hotSpotReqBody) {
		GeneralContentResult<String> result = new GeneralContentResult<String>();
		Hotspotconfig hotspotconfig = HotspotconfigConverter.hotSpotReqBody2Hotspotconfig(hotSpotReqBody);
		hotspotconfig.setCreator("xxx");
		hotspotconfig.setStatus(HotSpotConstant.HOTSPOT_CONFIG_ENABLE);
		Hotspotconfig hotspot = hotspotconfigRepository.save(hotspotconfig);
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setContent(hotspot.getId());
		result.setMessage("热点创建成功");
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GeneralResult updateHotSpot(String hotspotId, HotSpotReqBody hotSpotReqBody) {
		GeneralResult result = new GeneralResult();
		Hotspotconfig hotspotconfig = HotspotconfigConverter.hotSpotReqBody2Hotspotconfig(hotSpotReqBody);
		hotspotconfig.setId(hotspotId);
		hotspotconfig.setCreator("xxx");
		hotspotconfig.setStatus(HotSpotConstant.HOTSPOT_CONFIG_ENABLE);
		Hotspotconfig hotspot = hotspotconfigRepository.save(hotspotconfig);
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage("热点更新成功");
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GeneralResult disableHotSpot(String hotspotId) {
		int tmp = hotspotconfigRepository.setStatusFor(HotSpotConstant.HOTSPOT_CONFIG_DISABLE, hotspotId);
		GeneralResult result = new GeneralResult();
		if (tmp > 0) {
			result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
			result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		} else {
			result.setCode(CommonResultCodeConstant.WIFI_ERROR);
			result.setMessage(CommonResultMessageConstant.UNKNOW_ERROR);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GeneralResult deleteHotSpot(String hotspotId) {
		int tmp = hotspotconfigRepository.setStatusFor(HotSpotConstant.HOTSPOT_CONFIG_DELETE, hotspotId);
		GeneralResult result = new GeneralResult();
		if (tmp > 0) {
			result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
			result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		} else {
			result.setCode(CommonResultCodeConstant.WIFI_ERROR);
			result.setMessage(CommonResultMessageConstant.UNKNOW_ERROR);
		}
		return result;
	}

	@Override
	public GeneralContentResult<List<HotSpotRspBody>> findHotSpotBySomeAttr(HotSpotReqParam param) {
		DecimalFormat df = new DecimalFormat("######0.00");   
		
		GeneralContentResult<List<HotSpotRspBody>> result = new GeneralContentResult<List<HotSpotRspBody>>();
		List<HotSpotRspBody> rspBodies = new ArrayList<HotSpotRspBody>();
		double lng = Double.parseDouble(param.getLocationReqParam().getLng());
		double lat = Double.parseDouble(param.getLocationReqParam().getLat());
		double r = 6371;// 地球半径千米
		double dis = 0.5;// 0.5千米距离
		double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(lat * Math.PI / 180));
		dlng = dlng * 180 / Math.PI;// 角度转为弧度
		double dlat = dis / r;
		dlat = dlat * 180 / Math.PI;
		double minlat = lat - dlat;
		double maxlat = lat + dlat;
		double minlng = lng - dlng;
		double maxlng = lng + dlng;
		List<Hotspotconfig> hotspotconfigs = hotspotconfigRepository.findByBssidAnsSsidAndLngAndLat(minlat, maxlat, minlng, maxlng, HotSpotConstant.HOTSPOT_CONFIG_ENABLE);
		
		Map<String, Object> map = null;
		try {
			map = RSACoder.initKey();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Hotspotconfig hotspotconfig : hotspotconfigs) {
			for(WifiReqParam reqParamList :param.getWifiReqParam()) {
				if(hotspotconfig.getSsid().equals(reqParamList.getSsid())&&hotspotconfig.getBssid().equals(reqParamList.getBssid())) {
					HotSpotRspBody rspBody = new HotSpotRspBody();
					rspBody = HotspotconfigConverter.hotspotconfig2HotSpotRspBody(hotspotconfig,map);
					rspBodies.add(rspBody);
				}
			}
		}
		
		List<String> hotspotIds = new ArrayList<>();
		rspBodies.forEach(x -> {
			hotspotIds.add(x.getId());
		});
		
		//找到连接的设备数和连接次数,应该放到下面匹配完成的里面
		List<HotspotConnect> connects = hotspotConnectRepository.findByHotspotconfigIdIn(hotspotIds);
		Map<String,List<HotspotConnect>> hotspotConnectMap = new HashMap<>();
		connects.forEach(x -> {
			String hotSpotId = x.getHotspotconfigId();
			if(hotspotConnectMap.containsKey(hotSpotId)) {
				hotspotConnectMap.get(hotSpotId).add(x);
			}else {
				List<HotspotConnect> connectTmps = new ArrayList<>();
				connectTmps.add(x);
				hotspotConnectMap.put(hotSpotId, connectTmps);
			}
		});
		
		//找到相关的评价,应该放到下面匹配完成的里面
		GeneralContentResult<List<HotSpotCommentRspBody>> commentRspBodiesResult = orderCommentService.queryHotspotComments(hotspotIds);
		Map<String,List<HotSpotCommentRspBody>> hotspotCommentMap = new HashMap<>();
		if(commentRspBodiesResult!=null&&commentRspBodiesResult.getContent()!=null) {
			List<HotSpotCommentRspBody> bodiesTmps = commentRspBodiesResult.getContent();
			bodiesTmps.forEach(x -> {
				String hotSpotId = x.getHotspotconfigId();
				if(hotspotCommentMap.containsKey(hotSpotId)) {
					hotspotCommentMap.get(hotSpotId).add(x);
				}else {
					List<HotSpotCommentRspBody> commentTmps = new ArrayList<>();
					commentTmps.add(x);
					hotspotCommentMap.put(hotSpotId, commentTmps);
				}
			});
		}
		
		
		Map<String,HotSpotOtherMsg> hotSpotMsgMap = new HashMap<>();
		Set<String> hotSpotIdTmps = hotspotConnectMap.keySet();
		Iterator<String> iterator = hotSpotIdTmps.iterator();
		while(iterator.hasNext()) {
			HotSpotOtherMsg spotOtherMsg = new HotSpotOtherMsg();
			String hotSpotId = iterator.next();
			//计算设备连接数和连接次数
			List<HotspotConnect> connectTmps = hotspotConnectMap.get(hotSpotId);
			//设备的连接次数
			int deviceNum = connectTmps.stream().map(HotspotConnect::getDeviceId).collect(Collectors.toSet()).size();
			//总共的连接次数
			int connectNum = connectTmps.size();
			spotOtherMsg.setDeviceNum(deviceNum);
			spotOtherMsg.setConnectNum(connectNum);
			//计算评价
			List<HotSpotCommentRspBody> hotspotComments = hotspotCommentMap.get(hotSpotId);
			if(hotspotComments==null||hotspotComments.size()==0) {
				spotOtherMsg.setSpeedScore(0);
				spotOtherMsg.setStableScore(0);
				spotOtherMsg.setStrengthScore(0);
			}else {
				spotOtherMsg.setSpeedScore(hotspotComments.stream().mapToInt(HotSpotCommentRspBody::getSpeedScore).sum()/hotspotComments.size());
				spotOtherMsg.setStableScore(hotspotComments.stream().mapToInt(HotSpotCommentRspBody::getStableScore).sum()/hotspotComments.size());
				spotOtherMsg.setStrengthScore(hotspotComments.stream().mapToInt(HotSpotCommentRspBody::getStrengthScore).sum()/hotspotComments.size());
			}
			hotSpotMsgMap.put(hotSpotId, spotOtherMsg);
		}
		//组装数据
		rspBodies.stream().forEach(x -> {
			HotSpotOtherMsg msgTmp = hotSpotMsgMap.get(x.getId());
			x.setDeviceNum(msgTmp==null?0 : msgTmp.getDeviceNum());
			x.setConnectNum(msgTmp==null?0 : msgTmp.getConnectNum());
			x.setSpeedScore(msgTmp==null?"0" : df.format(msgTmp.getSpeedScore()));
			x.setStableScore(msgTmp==null?"0" : df.format(msgTmp.getStableScore()));
			x.setStrengthScore(msgTmp==null?"0" : df.format(msgTmp.getStrengthScore()));
		});
		
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		result.setContent(rspBodies);
		return result;
	}

	@Override
	public GeneralContentResult<HotSpotRspBody> findHotSpot(String hotspotId) {
		GeneralContentResult<HotSpotRspBody> result = new GeneralContentResult<HotSpotRspBody>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage("查询成功");
		Hotspotconfig hotspotconfig = hotspotconfigRepository.getOne(hotspotId);
		Map<String, Object> map = null;
		try {
			map = RSACoder.initKey();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(hotspotconfig!=null) {
			HotSpotRspBody rspBody = HotspotconfigConverter.hotspotconfig2HotSpotRspBody(hotspotconfig,map);
			result.setContent(rspBody);
		}else {
			result.setContent(null);
		}
		return result;
	}

	@Override
	public GeneralContentResult<List<HotSpotRspBody>> findHotSpotShareBySelf(String authorization) {
		DecimalFormat df = new DecimalFormat("######0.00");   
		GeneralContentResult<List<HotSpotRspBody>> result = new GeneralContentResult<>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage(CommonResultMessageConstant.OPERATE_SUCCESS);
		String userId  = JwtManager.parseToken(authorization).getUserId();
		List<Hotspotconfig> hotspotconfigs = hotspotconfigRepository.findByCreator(userId);
		List<HotSpotRspBody> hotspotRspBodies = hotspotconfigs.stream().map(x -> HotspotconfigConverter.hotspotconfig2HotSpotRspBody(x)).collect(Collectors.toList());
		result.setContent(hotspotRspBodies);
		
		if(result!=null&&result.getContent()!=null&&result.getContent().size()>0) {
			List<HotSpotRspBody> bodies = result.getContent();
			List<String> ids = bodies.stream().map(HotSpotRspBody::getId).collect(Collectors.toList());
			//找到连接的设备数和连接次数,应该放到下面匹配完成的里面
			 GeneralContentResult<List<HotSpotConnectRsp>> connectsResult = hotSpotConnectService.findHotspotConnectsByHotSpots(ids);
			Map<String,List<HotSpotConnectRsp>> hotspotConnectMap = new HashMap<>();
			if(connectsResult!=null&&connectsResult.getContent()!=null) {
				List<HotSpotConnectRsp> connects = connectsResult.getContent();
				connects.forEach(x -> {
					String hotSpotId = x.getHotspotconfigId();
					if(hotspotConnectMap.containsKey(hotSpotId)) {
						hotspotConnectMap.get(hotSpotId).add(x);
					}else {
						List<HotSpotConnectRsp> connectTmps = new ArrayList<>();
						connectTmps.add(x);
						hotspotConnectMap.put(hotSpotId, connectTmps);
					}
				});
			}
			
			//找到相关的评价,应该放到下面匹配完成的里面
			GeneralContentResult<List<HotSpotCommentRspBody>> commentRspBodiesResult = orderCommentService.queryHotspotComments(ids);
			Map<String,List<HotSpotCommentRspBody>> hotspotCommentMap = new HashMap<>();
			if(commentRspBodiesResult!=null&&commentRspBodiesResult.getContent()!=null) {
				List<HotSpotCommentRspBody> bodiesTmps = commentRspBodiesResult.getContent();
				bodiesTmps.forEach(x -> {
					String hotSpotId = x.getHotspotconfigId();
					if(hotspotCommentMap.containsKey(hotSpotId)) {
						hotspotCommentMap.get(hotSpotId).add(x);
					}else {
						List<HotSpotCommentRspBody> commentTmps = new ArrayList<>();
						commentTmps.add(x);
						hotspotCommentMap.put(hotSpotId, commentTmps);
					}
				});
			}
			
			Map<String,HotSpotOtherMsg> hotSpotMsgMap = new HashMap<>();
			Set<String> hotSpotIdTmps = hotspotConnectMap.keySet();
			Iterator<String> iterator = hotSpotIdTmps.iterator();
			while(iterator.hasNext()) {
				HotSpotOtherMsg spotOtherMsg = new HotSpotOtherMsg();
				String hotSpotId = iterator.next();
				//计算设备连接数和连接次数
				List<HotSpotConnectRsp> connectTmps = hotspotConnectMap.get(hotSpotId);
				int deviceNum = connectTmps.size();
				int connectNum = 0;
				for(HotSpotConnectRsp connect : connectTmps) {
					connectNum += connect.getConnectNum();
				}
				spotOtherMsg.setDeviceNum(deviceNum);
				spotOtherMsg.setConnectNum(connectNum);
				//计算评价
				List<HotSpotCommentRspBody> hotspotComments = hotspotCommentMap.get(hotSpotId);
				if(hotspotComments==null||hotspotComments.size()==0) {
					spotOtherMsg.setSpeedScore(0);
					spotOtherMsg.setStableScore(0);
					spotOtherMsg.setStrengthScore(0);
				}else {
					spotOtherMsg.setSpeedScore(hotspotComments.stream().mapToInt(HotSpotCommentRspBody::getSpeedScore).sum()/hotspotComments.size());
					spotOtherMsg.setStableScore(hotspotComments.stream().mapToInt(HotSpotCommentRspBody::getStableScore).sum()/hotspotComments.size());
					spotOtherMsg.setStrengthScore(hotspotComments.stream().mapToInt(HotSpotCommentRspBody::getStrengthScore).sum()/hotspotComments.size());
				}
				hotSpotMsgMap.put(hotSpotId, spotOtherMsg);
			}
			//组装数据
			bodies.stream().forEach(x -> {
				HotSpotOtherMsg msgTmp = hotSpotMsgMap.get(x.getId());
				x.setDeviceNum(msgTmp==null?0 : msgTmp.getDeviceNum());
				x.setConnectNum(msgTmp==null?0 : msgTmp.getConnectNum());
				x.setSpeedScore(msgTmp==null?"0" : df.format(msgTmp.getSpeedScore()));
				x.setStableScore(msgTmp==null?"0" : df.format(msgTmp.getStableScore()));
				x.setStrengthScore(msgTmp==null?"0" : df.format(msgTmp.getStrengthScore()));
			});
		}
		return result;
	}

	@Override
	public GeneralContentResult<List<HotSpotRspBody>> findHotSpots(Set<String> ids) {
		GeneralContentResult<List<HotSpotRspBody>> result = new GeneralContentResult<>();
		result.setCode(CommonResultCodeConstant.OPERATE_SUCCESS);
		result.setMessage("查询成功");
		List<Hotspotconfig> hotspotconfigs = hotspotconfigRepository.findByIdIn(ids);
		if(hotspotconfigs!=null&&hotspotconfigs.size()>0) {
			List<HotSpotRspBody> rspBodys = new ArrayList<>();
			hotspotconfigs.forEach(x -> {
				HotSpotRspBody body = HotspotconfigConverter.hotspotconfig2HotSpotRspBodyNoPassword(x);
				rspBodys.add(body);
			});
			result.setContent(rspBodys);
		}else {
			result.setContent(null);
		}
		return result;
	}

}
