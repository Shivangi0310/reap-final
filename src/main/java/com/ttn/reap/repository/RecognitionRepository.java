package com.ttn.reap.repository;

import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Recognition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecognitionRepository extends JpaRepository<Recognition,Integer> {
    List<Recognition> findAll();
    List<Recognition> findByActiveIsTrue();

    
    Optional<Recognition> findById(Integer id);
    Optional<List<Recognition>> getAllRecognitions(@Param("id") int id);
    Optional<List<Recognition>> fetchShared(@Param("id") int id);
    Optional<List<Recognition>> fetchReceived(@Param("id") int id);

    List<Recognition> findByDateOfRecognitionBetween(Date startDate, Date endDate);

}