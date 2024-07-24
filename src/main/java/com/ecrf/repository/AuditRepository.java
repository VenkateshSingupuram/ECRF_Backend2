package com.ecrf.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ecrf.entity.AuditEntity;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {

    List<AuditEntity> findByModifiedOnBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value="SELECT * FROM audit_data data "+
            "WHERE (cast(:startDate as date) IS NULL OR data.modified_on>=:startDate)"+
            "AND  (cast(:endDate as date) IS NULL OR data.modified_on<=:endDate)"+
            "AND  (:siteId IS NULL OR data.site_Id=:siteId)"+
            "AND  (:subjectId IS NULL OR data.subject_Id=:subjectId)",
            nativeQuery = true)
    List<AuditEntity> findAuditDataByDynamicCriteria(@Param("startDate") LocalDateTime startDate,
                                                                @Param("endDate") LocalDateTime endDate,
                                                                @Param("siteId") String siteId,
                                                                @Param("subjectId") String subjectId);

}