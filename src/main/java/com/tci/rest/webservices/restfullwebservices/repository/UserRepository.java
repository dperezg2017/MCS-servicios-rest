package com.tci.rest.webservices.restfullwebservices.repository;

import com.tci.rest.webservices.restfullwebservices.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}
