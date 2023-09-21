package com.kiot.cctvSystem.repository;

import com.kiot.cctvSystem.domain.Alarm;
import com.kiot.cctvSystem.domain.Device;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    /** 막대 그래프 데이터 **/
    @Query(value="SELECT MONTH(e.detected_time) AS month, COUNT(*) AS count" +
            " FROM Alarm e" +
            " WHERE YEAR(e.detected_time) = :year" +
            " GROUP BY MONTH(e.detected_time)" +
            " ORDER BY MIN(e.detected_time);", nativeQuery= true)
    List<Map<String, Integer>> getMonthlyAlarmCounts(@Param("year") int year);

    @Query(value="SELECT DAY(e.detected_time) AS day, COUNT(*) AS count " +
                  " FROM Alarm e " +
                  " WHERE YEAR(e.detected_time) = :year AND MONTH(e.detected_time) = :month " +
                  " GROUP BY DAY(e.detected_time)" +
                  " ORDER BY MIN(e.detected_time);", nativeQuery= true)
    List<Map<String, Integer>> getDailyAlarmCounts(@Param("year") int year, @Param("month") int month);

    @Query(value="SELECT HOUR(e.detected_time) AS hour, COUNT(*) AS count " +
            " FROM Alarm e " +
            " WHERE YEAR(e.detected_time) = :year AND MONTH(e.detected_time) = :month AND DAY(e.detected_time) = :day " +
            " GROUP BY HOUR(e.detected_time)" +
            " ORDER BY MIN(e.detected_time);", nativeQuery= true)
    List<Map<String, Integer>> getPerHourAlarmCounts(@Param("year") int year, @Param("month") int month, @Param("day") int day);

/*
    // 위의 쿼리 3개를 통합으로 사용( CASE 별로 별칭 alias를 주지 못해서 사용 하지 않기로 함)
    @Query("SELECT" +
            "   CASE" +
            "       WHEN :day IS NOT NULL AND :day != 0 THEN HOUR(e.detectedTime)" +
            "       WHEN :month IS NOT NULL AND :month != 0 THEN DAY(e.detectedTime)" +
            "       ELSE MONTH(e.detectedTime)" +
            "   END AS timeValue," +
            "   COUNT(*) AS count" +
            " FROM Alarm e" +
            " WHERE YEAR(e.detectedTime) = :year" +
            "   AND (:month IS NULL OR :month = 0 OR MONTH(e.detectedTime) = :month)" +
            "   AND (:day IS NULL OR :day = 0 OR DAY(e.detectedTime) = :day)" +
            " GROUP BY timeValue" +
            " ORDER BY MIN(e.detectedTime)")
    List<Map<String, Integer>> getAlarmCounts(@Param("year") int year, @Param("month") Integer month, @Param("day") Integer day);


*/

    /** 테이블 데이터
     *  연도별, 월별, 일별 통계 통합하여 조회
     *
     * **/
    @Query(value="SELECT a" +
            " FROM Alarm a JOIN FETCH a.device d JOIN FETCH a.target t" +
            " WHERE YEAR(a.detectedTime) = :year" +
            " AND (:month IS NULL OR MONTH(a.detectedTime) = :month) " +
            " AND (:day IS NULL OR DAY(a.detectedTime) = :day) " +
            " AND (:hour IS NULL OR HOUR(a.detectedTime) = :hour) " +
            " ORDER BY a.detectedTime")
    List<Alarm> getAlarmList(@Param("year") int year, @Param("month") Integer month, @Param("day") Integer day, @Param("hour") Integer hour);

    @Query(value = "SELECT a.*" +
            " FROM Alarm a" +
            " JOIN Device d ON a.device_id = d.id" +
            " WHERE d.device_id = :deviceId" +
            " ORDER BY detected_time DESC" +
            " LIMIT 5"
            , nativeQuery= true)
    List<Alarm> mapAlarmList(@Param("deviceId") Long deviceId);
}
