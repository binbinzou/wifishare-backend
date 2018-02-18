package com.shanghai.wifishare.wifimgmt.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the hotspot_ad database table.
 * 
 */
@Entity
@Table(name="HOTSPOT_AD")
@NamedQuery(name="HotspotAd.findAll", query="SELECT h FROM HotspotAd h")
public class HotspotAd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name="AD_PHOTOURL")
	private String adPhotourl;

	@Column(name="AD_TYPE")
	private short adType;

	@Lob
	@Column(name="AD_URL")
	private String adUrl;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="HOTSPOTCONFIG_ID")
	private String hotspotconfigId;

	@Column(name="STATUS")
	private short status;

	@Column(name="UPDATE_TIME")
	private Timestamp updateTime;

	public HotspotAd() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdPhotourl() {
		return this.adPhotourl;
	}

	public void setAdPhotourl(String adPhotourl) {
		this.adPhotourl = adPhotourl;
	}

	public short getAdType() {
		return this.adType;
	}

	public void setAdType(short adType) {
		this.adType = adType;
	}

	public String getAdUrl() {
		return this.adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getHotspotconfigId() {
		return this.hotspotconfigId;
	}

	public void setHotspotconfigId(String hotspotconfigId) {
		this.hotspotconfigId = hotspotconfigId;
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