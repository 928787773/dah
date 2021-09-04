package com.qrqy.dah.dto;

import com.qrqy.dp.annocation.BucketType;
import com.qrqy.dp.annocation.QiniuKodo;
import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.FormQuestionEntity;
import com.qrqy.mysql.entity.FormQuestionOptionEntity;
import com.qrqy.mysql.enumeration.FormQuestionAttributeEnum;
import com.qrqy.mysql.enumeration.FormQuestionOptionSortWayEnum;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * route : admin-form-question-query
 * @author : QRQY
 * @date : 2021-06-21 11:07
 */
@Data
public class FormQuestionListDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3419034360388264351L;

    @ApiModelProperty(value = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "code", example = "")
    private String code;

    @ApiModelProperty(value = "题目内容", example = "")
    private String content;

    @ApiModelProperty(value = "排序", example = "")
    private Integer sort;

    @ApiModelProperty(value = "英文题目内容", example = "")
    private String note;

    @ApiModelProperty(value = "问卷id", example = "")
    private Integer formInfoId;

    @ApiModelProperty(value = "是否必答", example = "")
    private Integer isForced;

    @ApiModelProperty(value = "问题等级 0:一级;1:二级", example = "")
    private Integer level;

    @ApiModelProperty(value = "图片", example = "")
    @QiniuKodo(type = BucketType.PUBLIC)
    private String imgs;

    @ApiModelProperty(value = "题目类型", example = "")
    private FormQuestionTypeEnum type;

    @ApiModelProperty(value = "属性", example = "")
    private FormQuestionAttributeEnum attribute;

    @ApiModelProperty(value = "排序方式", example = "")
    private FormQuestionOptionSortWayEnum optionSortWay;

    @ApiModelProperty(value = "选项数组", example = "")
    private List<FormInfoQuestionQueryOptionListDTO> formQuestionOptionList;


    @ApiModelProperty(value = "关联题目", example = "")
    private FormQuestionOptionRelyDetailDTO formQuestionOptionRelyDetail;

}
