package com.shanghai.wifishare.usermgmt.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the user_device database table.
 * 
 */
@Entity
@Table(name="USER_DEVICE")
@NamedQuery(name="UserDevice.findAll", query="SELECT u FROM UserDevice u")
public class UserDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name="CREATE_TIME",insertable=false,updatable=false)
	private Timestamp createTime;

	@Column(name="DEVICE_ID")
	private String deviceId;

	@Column(name="DEVICE_NAME")
	private String deviceName;

	@Column(name="DEVICE_SYSTEM")
	private String deviceSystem;

	@Column(name="SYSTEM_VERSION")
	private String systemVersion;

	@Column(name="UPDATE_TIME",insertable=false)
	private Timestamp updateTime;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="STATUS")
	private short status;
	
	public UserDevice() {
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
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

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceSystem() {
		return this.deviceSystem;
	}

	public void setDeviceSystem(String deviceSystem) {
		this.deviceSystem = deviceSystem;
	}

	public String getSystemVersion() {
		return this.systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}