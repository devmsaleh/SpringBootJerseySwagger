package com.his.webservice.dto;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.his.constants.ErrorCodeEnum;
import com.his.dao.ErrorCodeRepository;

public class ServiceResponse {
    protected ErrorCodeEnum errorCode;
    protected String errorCodeDesc;
    protected int errorCodeValue = ErrorCodeEnum.SYSTEM_ERROR_CODE.intValue();
    protected Map<String, Object> response;
    protected ErrorCodeRepository errorCodeRepository;
    protected String lang;
    private static final Logger logger = LoggerFactory.getLogger(ServiceResponse.class);

    public ServiceResponse(ErrorCodeRepository errorCodeRepository, String lang) {
        super();
        this.errorCodeRepository = errorCodeRepository;
        this.lang = lang;
    }

    public ServiceResponse(ErrorCodeEnum errorCode, ErrorCodeRepository errorCodeRepository, String lang) {
        super();
        this.errorCode = errorCode;
        response = new HashMap<String, Object>();
        this.errorCodeRepository = errorCodeRepository;
        this.lang = lang;
    }

    public ServiceResponse(ErrorCodeEnum errorCode, String responseKey, Object responseData, ErrorCodeRepository errorCodeRepository, String lang) {
        super();
        this.errorCode = errorCode;
        response = new HashMap<String, Object>();
        responseKey = responseKey == null ? "data" : responseKey;
        response.put(responseKey, responseData);
        this.errorCodeRepository = errorCodeRepository;
        this.lang = lang;
    }

    public ServiceResponse(ErrorCodeEnum errorCode, Map<String, Object> response, ErrorCodeRepository errorCodeRepository, String lang) {
        super();
        this.errorCode = errorCode;
        this.response = response;
        this.errorCodeRepository = errorCodeRepository;
        this.lang = lang;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCodeValue() {
        errorCodeValue = errorCode != null ? errorCode.intValue() : errorCodeValue;
        return errorCodeValue;
    }

    public void setErrorCodeValue(int errorCodeValue) {
        this.errorCodeValue = errorCodeValue;
    }

    public String getErrorCodeDesc() {
        if (errorCodeRepository != null) {
            com.his.entities.ErrorCode err = errorCodeRepository.findOne(new Long(getErrorCodeValue()));
            if (err == null)
                return errorCode + "";
            errorCodeDesc = this.lang == null || this.lang.equals("ar") ? err.getErrorNameArabic() : err.getErrorNameEnglish();
        }
        if (errorCodeDesc == null)
            return errorCode + "";
        return errorCodeDesc;
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    public void setResponse(Map<String, Object> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        // Object to JSON in String
        try {
            String jsonInString = mapper.writeValueAsString(this);
            return jsonInString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("Exception in toString webservice: ", e);
            return super.toString();
        }

    }
}
