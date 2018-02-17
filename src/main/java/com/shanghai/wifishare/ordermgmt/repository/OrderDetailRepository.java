package com.shanghai.wifishare.ordermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shanghai.wifishare.ordermgmt.domain.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

	List<OrderDetail> findByOrderId(String orderMergeId);

	OrderDetail findByOrderIdAndOrderDetailType(String orderMergerId, short oRDER_DETAIL_TYPE_END);


}
