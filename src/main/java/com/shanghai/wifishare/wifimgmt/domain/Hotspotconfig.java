package com.shanghai.wifishare.wifimgmt.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the hotspotconfig database table.
 * 
 */
@Entity
@NamedQuery(name="Hotspotconfig.findAll", query="SELECT h FROM Hotspotconfig h")
public class Hotspotconfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	@Column(name="BSSID")
	private String bssid;

	@Column(name="CHARING_MODULE")
	private short charingModule;

	@Column(name="CHARING_STANDARD")
	private short charingStandard;

	@Column(name="CREATE_TIME",insertable=false,updatable=false)
	private Timestamp createTime;

	@Column(name="CREATOR")
	private String creator;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="HOTSPOT_TYPE")
	private short hotspotType;

	@Column(name="LAT")
	private double lat;

	@Column(name="LNG")
	private double lng;

	@Column(name="PASSWORD")
	private String password;

	@Column(name="SSID")
	private String ssid;

	@Column(name="STATUS")
	private short status;

	@Column(name="UPDATE_TIME",insertable=false)
	private Timestamp updateTime;

	public Hotspotconfig() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBssid() {
		return this.bssid;
	}

	public void setBssid(String bssid) {
		this.bssid = bssid;
	}

	public short getCharingModule() {
		return this.charingModule;
	}

	public void setCharingModule(short charingModule) {
		this.charingModule = charingModule;
	}

	public short getCharingStandard() {
		return this.charingStandard;
	}

	public void setCharingStandard(short charingStandard) {
		this.charingStandard = charingStandard;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getHotspotType() {
		return this.hotspotType;
	}

	public void setHotspotType(short hotspotType) {
		this.hotspotType = hotspotType;
	}

	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return this.lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSsid() {
		return this.ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}