package com.tci.rest.webservices.restfullwebservices.repository;

import com.tci.rest.webservices.restfullwebservices.beans.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

}
