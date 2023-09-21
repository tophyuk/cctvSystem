package com.kiot.cctvSystem.repository;

import com.kiot.cctvSystem.domain.Alarm;
import com.kiot.cctvSystem.domain.Target;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {

    /** 원형 그래프 데이터  **/
    @Transactional
    @Query(value="SELECT t.name as name, COUNT(a.count) as count" +
            " FROM Alarm a" +
            " JOIN Target t" +
            " ON a.target = t.id" +
            " WHERE YEAR(a.detectedTime) = :year" +
            " AND (:month IS NULL OR :month = 0 OR MONTH(a.detectedTime) = :month) " +
            " AND (:day IS NULL OR :day = 0 OR DAY(a.detectedTime) = :day) " +
            " GROUP BY t.name")
    List<Map<String, Object>> getTargetCounts(@Param("year") int year, @Param("month") Integer month, @Param("day") Integer day);


}
