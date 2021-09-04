package com.qrqy.dah.dto;

import com.qrqy.dp.annocation.BucketType;
import com.qrqy.dp.annocation.QiniuKodo;
import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.enumeration.FormQuestionAttributeEnum;
import com.qrqy.mysql.enumeration.FormQuestionOptionSortWayEnum;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * route : admin-form-question-query
 * @author : QRQY
 * @date : 2021-06-21 11:07
 */
@Data
public class FormInfoQuestionQueryOptionListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3419034360388264351L;

    private Integer id;

    private String code;

    private Integer formQuestionId;

    private String content;

    private String detail;

    private String enDetail;

    private BigDecimal score;

    private String imgs;

    private String type;

    private String sort;

    private String note;


}
