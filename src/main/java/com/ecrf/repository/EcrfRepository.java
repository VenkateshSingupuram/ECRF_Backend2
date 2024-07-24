package com.ecrf.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecrf.entity.RecordMasterData;
import com.ecrf.filters.AuthFilter;
@Repository
public interface EcrfRepository extends JpaRepository<RecordMasterData, String> {

	Logger logger = LogManager.getLogger(EcrfRepository.class);		

String TableName = "ecrf_master_data";

@Query(value = "SELECT * FROM "+TableName+" WHERE section1->>?1 = ?2", nativeQuery = true)
Iterable<RecordMasterData> findEmployeeByNamee(String tt,String vv);




@Query(value = "SELECT count(*) AS exact_count FROM "+TableName, nativeQuery = true)
int getTotalRecordCount();

@Query(value = "SELECT * FROM "+TableName+" WHERE to_date (section14->>'createdOn', 'YYYY-MM-DD') \r\n"
		+ "    BETWEEN ?1 \r\n"
		+ "    AND     ?2 AND dispatched_to = ?3 AND siteid=?4", nativeQuery = true)
List<RecordMasterData> findRecordOnDate(LocalDateTime fromDate,LocalDateTime toDate, String roleName,String siteId);

/*
 * @Query(value = "SELECT * FROM "
 * +TableName+" WHERE createdate >= ?1 AND createdate < ?2 AND dispatched_to = ?3 AND siteid=?4"
 * , nativeQuery = true) List<RecordMasterData> findRecordOnDate(LocalDateTime
 * fromDate,LocalDateTime toDate, String roleName,String siteId);
 */


@Query(value = "SELECT * FROM "+TableName+" WHERE dispatched_to = ?1 AND siteid=?2", nativeQuery = true)
List<RecordMasterData> getRecentRecords(String roleName,String siteId);

@Query(value = "select last_value from ?1", nativeQuery = true)
public Integer getCurrentVal(String seqName);

@Query(value = "SELECT setval(?1, ?2, true)", nativeQuery = true)
public Integer incrementVal(String seqName,long l);

@Query(value = "SELECT COUNT(*) FROM information_schema.sequences WHERE sequence_schema='public' AND sequence_name=?1", nativeQuery = true)
public Integer checkIfSeqExists(String seqName);

@Query(value = "SELECT * FROM "+TableName+" WHERE dispatched_to IN ?1", nativeQuery = true)
List<RecordMasterData> getRecordIn(List<String> roleName);

@Modifying
@Query(value = "create sequence ?1 increment 1 start 1", nativeQuery = true)
public Integer createSeq(String seqName);

List<RecordMasterData> findByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);

@Query(value="SELECT * FROM ecrf_master_data data "+
					"WHERE (cast(:startDate as date)  IS NULL OR data.createdate>=:startDate)"+
					"AND  (cast(:endDate as date) IS NULL OR data.createdate<=:endDate )"+
					"AND  (:siteId IS NULL OR data.siteId=:siteId)"+
					"AND  (:subjectId IS NULL OR data.subjectId=:subjectId)",
		nativeQuery = true)
List<RecordMasterData> findRecordMaterDataByDynamicCriteria(@Param("startDate") LocalDateTime startDate,
															@Param("endDate") LocalDateTime endDate,
															@Param("siteId") String siteId,
															@Param("subjectId") String subjectId);



}