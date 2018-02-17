package com.shanghai.wifishare.ordermgmt.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shanghai.wifishare.ordermgmt.domain.OrderMergeHistory;

@Repository
public interface OrderMergeHistoryRepository extends JpaRepository<OrderMergeHistory, String> {

	Page<OrderMergeHistory> findByCreator(String userId, Pageable request);

	Page<OrderMergeHistory> findByHotspotconfigIdIn(List<String> hotspotIds, Pageable request);

	List<OrderMergeHistory> findByCreator(String userId);
	
}
