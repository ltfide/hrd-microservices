package com.hrd.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hrd.entity.PublicHolidayEntity;
import com.hrd.implement.PublicHolidayImplement;
import com.hrd.model.request.PublicHolidayModelRequest;
import com.hrd.model.response.PublicHolidayModelResponse;
import com.hrd.payload.ApiResponse;
import com.hrd.repository.PublicHolidayRepository;
import com.hrd.util.BasicUtils;
import com.hrd.util.GeneralConstants;
import com.hrd.util.ObjectActiveAndCreatedDate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Parameter;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicHolidayService implements PublicHolidayImplement {

    @Autowired
    private final PublicHolidayRepository publicHolidayRepository;

    @Autowired
    private final Validator validator;

    @Autowired
    private final EntityManager em;

    @Override
    public ResponseEntity<Map<String, Object>> createPublicHoliday(PublicHolidayModelRequest request) {
        try {
            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

            PublicHolidayEntity ph = om.convertValue(request, PublicHolidayEntity.class);

            ObjectActiveAndCreatedDate.registerObject(ph);

            Set<ConstraintViolation<PublicHolidayEntity>> violations = validator.validate(ph);

            if (!violations.isEmpty())
                return ResponseEntity.ok(new ApiResponse(GeneralConstants.VALIDATION_CODE, GeneralConstants.INVALID).setValidate(violations));

            ph.setHolidayDate(LocalDate.parse(request.getHolidayDate().toString(), DateTimeFormatter.ofPattern(GeneralConstants.Y_M_D)));

            PublicHolidayEntity phName = publicHolidayRepository.findByName(request.getHolidayName());


            if (phName != null)
                return ResponseEntity.ok(new ApiResponse(GeneralConstants.UNAUTHORIZED_CODE, GeneralConstants.UNAUTHORIZED).setMessage("public holiday name is already exists"));

            publicHolidayRepository.save(ph);

            log.info("Public Holiday Insert is Persist ? : {} ", ph);

            return ResponseEntity.ok(new ApiResponse(GeneralConstants.SUCCESS_CODE, GeneralConstants.SUCCESS).setMessage("public holiday successfully added"));

        }catch (Exception event){
            log.info(event.getMessage(), event);
            return ResponseEntity.ok(new ApiResponse(GeneralConstants.FAIL_CODE, GeneralConstants.FAILED).setMessage(event.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> updatePublicHoliday(Long id, PublicHolidayModelRequest request) {
        try {
            Optional<PublicHolidayEntity> ph = publicHolidayRepository.findById(id);

            if (ph.isEmpty()){
                return ResponseEntity.ok(new ApiResponse(GeneralConstants.NOT_FOUND_CODE, GeneralConstants.NOT_FOUND).setMessage("public holiday not found"));
            }

            if (request.getHolidayName() != null && !request.getHolidayName().equals(GeneralConstants.EMPTY_STRING))
                ph.get().setHolidayName(request.getHolidayName());


            if (request.getHolidayDate() != null && !request.getHolidayDate().toString().equals(GeneralConstants.EMPTY_STRING))
                ph.get().setHolidayDate(LocalDate.parse(request.getHolidayDate().toString(), DateTimeFormatter.ofPattern(GeneralConstants.Y_M_D)));


            publicHolidayRepository.save(ph.get());

            log.info("Public Holiday Update is Persist ? : {} ", ph);

            return ResponseEntity.ok(new ApiResponse(GeneralConstants.SUCCESS_CODE, GeneralConstants.SUCCESS).setMessage("public holiday successfully updated"));

        }catch (Exception event){
            log.info(event.getMessage(), event);
            return ResponseEntity.ok(new ApiResponse(GeneralConstants.FAIL_CODE, GeneralConstants.FAILED).setMessage(event.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllPublicHoliday(Object params) {
        try {
            ObjectMapper om = new ObjectMapper();

            Map<String, Object> reqParam = om.convertValue(params, new TypeReference<>(){});

            Integer limit = 5, offset = 0;
            boolean filterDate = false, filterName = false;

            if (reqParam.containsKey("limit") && reqParam.get("limit") != null)
                limit = Integer.parseInt(reqParam.get("limit").toString());

            if (reqParam.containsKey("offset") && reqParam.get("offset") != null)
                offset = Integer.parseInt(reqParam.get("offset").toString());

            StringBuilder sb = new StringBuilder();
            sb.append("select ph.id, ph.holiday_date, ph.holiday_name from public.public_holidays ph where true");

            if (reqParam.containsKey("date") && reqParam.get("date") != null){
                sb.append(" and ph.holiday_date = :phDate");
                filterDate = true;
            }

            if (reqParam.containsKey("name") && reqParam.get("name") != null){
                sb.append(" and ph.holiday_name ilike :phName");
                filterName = true;
            }

            sb.append(" and ph.status = 0 order by ph.id desc");

            Query query = em.createNativeQuery(sb.toString());

            if (filterDate)
                query.setParameter("phDate", reqParam.get("date").toString());

            if (filterName)
                query.setParameter("phName", "%"+reqParam.get("name").toString()+"%");

            if (!limit.equals(-99) || !offset.equals(-99)){
                query.setMaxResults(limit);
                query.setFirstResult(offset);
            }

            List<Object[]> resultQuery = query.getResultList();

            List<Map<String, Object>> data = BasicUtils.createListOfMapFromArrayWithPattern(resultQuery, "yyyy-MM-dd", "",  "id", "holiday_date", "holiday_name");

            Query queryCount = em.createNativeQuery(sb.toString().replaceFirst("select.* from", "select count (*) from").replaceFirst("order by .*", ""));

            for (Parameter<?> parameter : query.getParameters())
                queryCount.setParameter(parameter.getName(), query.getParameterValue(parameter.getName()));

            BigInteger count = (BigInteger) queryCount.getSingleResult();

            Map<String, Object> response = new HashMap<>();
            response.put("publicHolidays", data);

            Map<String, Object> metaData = new HashMap<>();

            metaData.put("totalPages", (int) Math.ceil(Double.parseDouble(count.toString()) / data.size()));
            metaData.put("total", count);
            metaData.put("page", data.size());

            response.put("meta", metaData);

            return ResponseEntity.ok(new ApiResponse(GeneralConstants.SUCCESS_CODE, GeneralConstants.SUCCESS).setEntity(response));

        }catch (Exception event){
            log.info(event.getMessage(), event);
            return ResponseEntity.ok(new ApiResponse(GeneralConstants.FAIL_CODE, GeneralConstants.FAILED).setMessage(event.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getPublicHolidayById(Long id) {
        try {
            Optional<PublicHolidayEntity> ph = publicHolidayRepository.findById(id);

            if (ph.isEmpty())
                return ResponseEntity.ok(new ApiResponse(GeneralConstants.NOT_FOUND_CODE, GeneralConstants.NOT_FOUND).setMessage("public holiday not found"));

            log.info("Public Holiday Update is Persist ? : {} ", ph.get());

            PublicHolidayModelResponse phResponse = PublicHolidayModelResponse.builder()
                    .id(ph.get().getId())
                    .holidayName(ph.get().getHolidayName())
                    .holidayDate(ph.get().getHolidayDate()).build();

            return ResponseEntity.ok(new ApiResponse(GeneralConstants.SUCCESS_CODE, GeneralConstants.SUCCESS).setEntity(phResponse));

        }catch (Exception event){
            log.info(event.getMessage(), event);
            return ResponseEntity.ok(new ApiResponse(GeneralConstants.FAIL_CODE, GeneralConstants.FAILED).setMessage(event.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> deletePublicHoliday(Long id) {
        try {
            Optional<PublicHolidayEntity> ph = publicHolidayRepository.findById(id);

            if (ph.isEmpty())
                return ResponseEntity.ok(new ApiResponse(GeneralConstants.NOT_FOUND_CODE, GeneralConstants.NOT_FOUND).setMessage("public holiday not found"));

            ObjectActiveAndCreatedDate.deleteObject(ph.get(), 1L);

            publicHolidayRepository.save(ph.get());

            return ResponseEntity.ok(new ApiResponse(GeneralConstants.SUCCESS_CODE, GeneralConstants.SUCCESS).setMessage("Public Holiday has been deleted"));

        }catch (Exception event){
            log.info(event.getMessage(), event);
            return ResponseEntity.ok(new ApiResponse(GeneralConstants.FAIL_CODE, GeneralConstants.FAILED).setMessage(event.getMessage()));
        }
    }
}
