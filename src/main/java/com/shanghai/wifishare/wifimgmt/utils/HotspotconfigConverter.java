package com.shanghai.wifishare.wifimgmt.utils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import com.shanghai.wifishare.wifimgmt.domain.Hotspotconfig;
import com.wifishared.common.data.dto.hotspot.HotSpotReqBody;
import com.wifishared.common.data.otd.hotspot.HotSpotRspBody;
import com.wifishared.common.framework.rsa.Base64;
import com.wifishared.common.framework.rsa.RSACoder;

public class HotspotconfigConverter {

	public static Hotspotconfig hotSpotReqBody2Hotspotconfig(HotSpotReqBody reqBody){
		Hotspotconfig hotspotconfig = new Hotspotconfig();
		hotspotconfig.setDescription(reqBody.getDescrition());
		hotspotconfig.setBssid(reqBody.getBssid());
		hotspotconfig.setSsid(reqBody.getSsid());
		hotspotconfig.setPassword(reqBody.getPassword());
		hotspotconfig.setCharingModule(Short.parseShort(reqBody.getCharingModule()));
		hotspotconfig.setCharingStandard(Short.parseShort(reqBody.getCharingStandard()));
		hotspotconfig.setHotspotType(Short.parseShort(reqBody.getHotSpotType()));
		hotspotconfig.setLat(Double.parseDouble(reqBody.getLat()));
		hotspotconfig.setLng(Double.parseDouble(reqBody.getLng()));
		return hotspotconfig;
	}

	public static HotSpotRspBody hotspotconfig2HotSpotRspBody(Hotspotconfig hotspotconfig,Map<String, Object> map) {
		HotSpotRspBody body = new HotSpotRspBody();
		body.setBssid(hotspotconfig.getBssid());
		body.setCharingModule(String.valueOf(hotspotconfig.getCharingModule()));
		body.setCharingStandard(String.valueOf(hotspotconfig.getCharingStandard()));
		body.setDescription(hotspotconfig.getDescription());
		body.setHotspotType(String.valueOf(hotspotconfig.getHotspotType()));
		body.setId(hotspotconfig.getId());
		body.setLat(String.valueOf(hotspotconfig.getLat()));
		body.setLng(String.valueOf(hotspotconfig.getLng()));
		//私钥解密过程  
        byte[] res = null;
		try {
			res = RSACoder.decrypt(RSACoder.loadPrivateKeyByStr(RSACoder.getPrivateKey()), Base64.decode(hotspotconfig.getPassword()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        String restr=new String(res);    
        
        //私钥加密过程  
        byte[] cipherData = null;
		try {
			cipherData = RSACoder.encrypt((RSAPrivateKey)map.get("private"),restr.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        String cipher=Base64.encode(cipherData);  
        
		body.setPassword(cipher);
		body.setSsid(hotspotconfig.getSsid());
		body.setPublicCert(Base64.encode(((RSAPublicKey)map.get("public")).getEncoded()));
		return body;
	}
	
	public static HotSpotRspBody hotspotconfig2HotSpotRspBodyNoPassword(Hotspotconfig hotspotconfig) {
		HotSpotRspBody body = new HotSpotRspBody();
		body.setBssid(hotspotconfig.getBssid());
		body.setCharingModule(String.valueOf(hotspotconfig.getCharingModule()));
		body.setCharingStandard(String.valueOf(hotspotconfig.getCharingStandard()));
		body.setDescription(hotspotconfig.getDescription());
		body.setHotspotType(String.valueOf(hotspotconfig.getHotspotType()));
		body.setId(hotspotconfig.getId());
		body.setLat(String.valueOf(hotspotconfig.getLat()));
		body.setLng(String.valueOf(hotspotconfig.getLng()));
		body.setPassword("");
		body.setSsid(hotspotconfig.getSsid());
		body.setPublicCert("");
		return body;
	}
	
	
	public static HotSpotRspBody hotspotconfig2HotSpotRspBody(Hotspotconfig hotspotconfig) {
		HotSpotRspBody body = new HotSpotRspBody();
		body.setBssid(hotspotconfig.getBssid());
		body.setCharingModule(String.valueOf(hotspotconfig.getCharingModule()));
		body.setCharingStandard(String.valueOf(hotspotconfig.getCharingStandard()));
		body.setDescription(hotspotconfig.getDescription());
		body.setHotspotType(String.valueOf(hotspotconfig.getHotspotType()));
		body.setId(hotspotconfig.getId());
		body.setLat(String.valueOf(hotspotconfig.getLat()));
		body.setLng(String.valueOf(hotspotconfig.getLng()));
		body.setPassword("");
		body.setSsid(hotspotconfig.getSsid());
		return body;
	}
	
}
