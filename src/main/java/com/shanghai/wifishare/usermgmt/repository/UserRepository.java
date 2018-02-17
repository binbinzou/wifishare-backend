package com.shanghai.wifishare.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shanghai.wifishare.usermgmt.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByPhoneNumber(String phoneNumber);

	List<User> findByIdIn(List<String> userIds);

}
