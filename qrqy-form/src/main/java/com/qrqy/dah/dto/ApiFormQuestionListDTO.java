package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.FormQuestionOptionEntity;
import com.qrqy.mysql.enumeration.FormQuestionAttributeEnum;
import com.qrqy.mysql.enumeration.FormQuestionOptionSortWayEnum;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * route : admin-form-question-query
 * @author : QRQY
 * @date : 2021-06-21 11:07
 */
@Data
public class ApiFormQuestionListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3419034360388264351L;

    private Integer id;

    private String code;


    private String content;


    private Integer formInfoId;


    private Integer isForced;


    private String imgs;


    private FormQuestionTypeEnum type;


    private FormQuestionAttributeEnum attribute;


    private FormQuestionOptionSortWayEnum optionSortWay;


    private List<FormQuestionOptionEntity> formQuestionOptionList;


}
