package com.shanghai.wifishare.wifimgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shanghai.wifishare.wifimgmt.domain.HotspotConnect;


@Repository
public interface HotspotConnectRepository extends JpaRepository<HotspotConnect, String> {

	List<HotspotConnect> findByHotspotconfigIdIn(List<String> hotspotconfigIds);


}
