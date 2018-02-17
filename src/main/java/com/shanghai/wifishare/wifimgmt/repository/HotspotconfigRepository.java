package com.shanghai.wifishare.wifimgmt.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shanghai.wifishare.wifimgmt.domain.Hotspotconfig;


@Repository
public interface HotspotconfigRepository extends JpaRepository<Hotspotconfig, String> {

	@Query("UPDATE Hotspotconfig a SET a.status = ?1 where a.id = ?2")
	@Modifying
	int setStatusFor(short status, String id);

	@Query("SELECT hs FROM Hotspotconfig hs where hs.lat>=?1 AND hs.lat <=?2 AND hs.lng>=?3 AND hs.lng<=?4 And hs.status = ?5")
	List<Hotspotconfig> findByBssidAnsSsidAndLngAndLat( double minlat, double maxlat,
			double minlng, double maxlng,short status);

	List<Hotspotconfig> findByCreator(String userId);

	List<Hotspotconfig> findByIdIn(Set<String> ids);

}
