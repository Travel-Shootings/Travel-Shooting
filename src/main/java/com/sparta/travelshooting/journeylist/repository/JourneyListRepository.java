package com.sparta.travelshooting.journeylist.repository;

import com.sparta.travelshooting.journeylist.entity.JourneyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyListRepository extends JpaRepository <JourneyList, Long> {
    List<JourneyList> findAllByPostId(Long PostId);
}
