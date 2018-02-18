package com.shanghai.wifishare.wifimgmt.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the hotspot_connect database table.
 * 
 */
@Entity
@Table(name="HOTSPOT_CONNECT")
@NamedQuery(name="HotspotConnect.findAll", query="SELECT h FROM HotspotConnect h")
public class HotspotConnect implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name="CREATE_TIME",insertable=false,updatable=false)
	private Timestamp createTime;

	@Column(name="DEVICE_ID")
	private String deviceId;

	@Column(name="HOTSPOTCONFIG_ID")
	private String hotspotconfigId;

	@Column(name="USER_ID")
	private String userId;

	public HotspotConnect() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getHotspotconfigId() {
		return this.hotspotconfigId;
	}

	public void setHotspotconfigId(String hotspotconfigId) {
		this.hotspotconfigId = hotspotconfigId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}