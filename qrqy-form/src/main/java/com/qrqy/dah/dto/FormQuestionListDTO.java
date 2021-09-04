package com.qrqy.dah.dto;

import com.qrqy.dah.qo.FormQuestionOptionEditQO;
import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.FormQuestionOptionEntity;
import com.qrqy.mysql.enumeration.FormQuestionAttributeEnum;
import com.qrqy.mysql.enumeration.FormQuestionOptionSortWayEnum;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnEnum;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * route : admin-form-question-query
 * @author : QRQY
 * @date : 2021-06-21 11:07
 */
@Data
public class FormQuestionListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3419034360388264351L;

    @ApiModelProperty(value = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "题目内容", example = "")
    private String content;

    @ApiModelProperty(value = "英文题目内容", example = "")
    private String note;

    @ApiModelProperty(value = "问卷id", example = "")
    private Integer formInfoId;

    @ApiModelProperty(value = "是否必答;0:否;1:是", example = "")
    private Integer isForced;

    @ApiModelProperty(value = "图片", example = "")
    private String imgs;

    @ApiModelProperty(value = "类型 SINGLECHOICE：单选 MULTIPLECHOICE：多选 FILLIN:填空 SCORE:打分", example = "")
    private FormQuestionTypeEnum type;

    @ApiModelProperty(value = "英文类型", example = "")
    private FormQuestionTypeEnEnum enType;

    @ApiModelProperty(value = "(类型是填空使用的字段)属性 NO:无属性 TEXT:长文本 DATE: 日期 PHONE:手机号 EMAIL:邮箱", example = "")
    private FormQuestionAttributeEnum attribute;

    @ApiModelProperty(value = "选项排列方式; ROW1:竖向排列 ROW2:两列 ROW3:三列 ROW4:四列 ROW5:五列 ROW6:六列 ROW7:七列 ROW8:八列 ROW9:九列 ROW10:十列", example = "")
    private FormQuestionOptionSortWayEnum optionSortWay;

    @ApiModelProperty(value = "选项组", example = "")
    private List<FormQuestionOptionListDTO> formQuestionOptionList;


}
