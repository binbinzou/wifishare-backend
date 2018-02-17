package com.shanghai.wifishare.ordermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shanghai.wifishare.ordermgmt.domain.OrderMerge;

@Repository
public interface OrderMergeRepository extends JpaRepository<OrderMerge, String> {

	OrderMerge findByIdAndStatus(String orderMergeId, short oRDER_MERGE_BEGINING);

	List<OrderMerge> findByDeviceIdAndCreator(String deviceId, String userId);


}
