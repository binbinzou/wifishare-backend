package com.shanghai.wifishare.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shanghai.wifishare.usermgmt.domain.UserDevice;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, String>{

	UserDevice findByUserIdAndDeviceId(String userId, String deviceUuid);

	List<UserDevice> findByUserId(String userId);

	List<UserDevice> findByIdIn(List<String> deviceIds);

}
