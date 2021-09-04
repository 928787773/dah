package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.FormQuestionOptionEntity;
import com.qrqy.mysql.enumeration.FormQuestionAttributeEnum;
import com.qrqy.mysql.enumeration.FormQuestionOptionSortWayEnum;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * route : admin-form-question-query
 * @author : QRQY
 * @date : 2021-06-21 11:07
 */
@Data
public class CurrentFormAnswerRecordJsonDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3419034360388264351L;


    @ApiModelProperty(value = "问题id", example = "")
    private Integer id;

    @ApiModelProperty(value = "问卷id", example = "")
    private Integer formInfoId;

    @ApiModelProperty(value = "问题内容", example = "")
    private String content;

    @ApiModelProperty(value = "是否必答", example = "")
    private Integer isForced;

    @ApiModelProperty(value = "问题图片", example = "")
    private String imgs;

    @ApiModelProperty(value = "问题类型", example = "")
    private FormQuestionTypeEnum type;

    @ApiModelProperty(value = "属性", example = "")
    private FormQuestionAttributeEnum attribute;

    @ApiModelProperty(value = "选项排列方式", example = "")
    private FormQuestionOptionSortWayEnum optionSortWay;

    @ApiModelProperty(value = "填空的value", example = "")
    private String value;

    @ApiModelProperty(value = "选中选项", example = "")
    private List<FormQuestionOptionEntity> currentFormQuestionOptionList;








}
