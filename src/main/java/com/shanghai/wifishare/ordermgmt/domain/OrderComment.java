package com.shanghai.wifishare.ordermgmt.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the order_comment database table.
 * 
 */
@Entity
@Table(name="order_comment")
@NamedQuery(name="OrderComment.findAll", query="SELECT o FROM OrderComment o")
public class OrderComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name="CREATE_TIME",insertable=false,updatable=false)
	private Timestamp createTime;

	@Column(name="HOTSPOTCONFIG_ID")
	private String hotspotconfigId;

	@Column(name="ORDER_HISTORY_ID")
	private String orderHistoryId;

	@Column(name="SPEED_SCORE")
	private float speedScore;

	@Column(name="STABLE_SCORE")
	private float stableScore;

	@Column(name="STRENGTH_SCORE")
	private float strengthScore;

	public OrderComment() {
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

	public String getHotspotconfigId() {
		return this.hotspotconfigId;
	}

	public void setHotspotconfigId(String hotspotconfigId) {
		this.hotspotconfigId = hotspotconfigId;
	}

	public String getOrderHistoryId() {
		return this.orderHistoryId;
	}

	public void setOrderHistoryId(String orderHistoryId) {
		this.orderHistoryId = orderHistoryId;
	}

	public float getSpeedScore() {
		return speedScore;
	}

	public void setSpeedScore(float speedScore) {
		this.speedScore = speedScore;
	}

	public float getStableScore() {
		return stableScore;
	}

	public void setStableScore(float stableScore) {
		this.stableScore = stableScore;
	}

	public float getStrengthScore() {
		return strengthScore;
	}

	public void setStrengthScore(float strengthScore) {
		this.strengthScore = strengthScore;
	}



}