package com.ecrf.services;

import com.ecrf.entity.AuditEntity;
import com.ecrf.entity.RecordMasterData;
import com.ecrf.repository.AuditRepository;
import com.ecrf.repository.EcrfRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExportExcelService {

    private static final Logger logger = LogManager.getLogger(ExportExcelService.class);
    EcrfRepository ecrfRepository;

    AuditRepository auditRepository;

    @Autowired
    public ExportExcelService(EcrfRepository ecrfRepository, AuditRepository auditRepository) {
        this.ecrfRepository = ecrfRepository;
        this.auditRepository = auditRepository;
    }

    public List<RecordMasterData> exportExcel(LocalDateTime parsedStartDate, LocalDateTime parsedEndDate,String siteId,String subjectId){
        logger.info("ExportExcelService Method - exportExcel");

        return  ecrfRepository.findRecordMaterDataByDynamicCriteria(parsedStartDate,parsedEndDate,siteId,subjectId);

    }

    public List<AuditEntity> exportAuditExcel(LocalDateTime parsedStartDate, LocalDateTime parsedEndDate,String siteId,String subjectId){
        logger.info("ExportExcelService Method - exportAuditExcel()");
        return  auditRepository.findAuditDataByDynamicCriteria(parsedStartDate,parsedEndDate,siteId,subjectId);

    }

}