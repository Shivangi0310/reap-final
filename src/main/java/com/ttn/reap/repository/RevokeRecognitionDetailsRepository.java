package com.ttn.reap.repository;

import com.ttn.reap.entity.RevokeRecognitionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevokeRecognitionDetailsRepository extends JpaRepository<RevokeRecognitionDetails,Integer> {
    
}
