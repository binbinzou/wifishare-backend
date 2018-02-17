package com.shanghai.wifishare.ordermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shanghai.wifishare.ordermgmt.domain.OrderComment;


@Repository
public interface OrderCommentRepository extends JpaRepository<OrderComment, String> {

	List<OrderComment> findByHotspotconfigIdIn(List<String> hotspotIds);

	List<OrderComment> findByOrderHistoryIdIn(List<String> orderMergeIds);



}
