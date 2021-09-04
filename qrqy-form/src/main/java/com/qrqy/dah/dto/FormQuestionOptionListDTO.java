package com.qrqy.dah.dto;

import com.qrqy.dah.qo.FormQuestionOptionEditQO;
import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.FormQuestionEntity;
import com.qrqy.mysql.entity.FormQuestionOptionEntity;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * route : admin-form-question-query
 * @author : QRQY
 * @date : 2021-06-21 11:07
 */
@Data
public class FormQuestionOptionListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3419034360388264351L;

    @ApiModelProperty(value = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "code", example = "")
    private String code;

    @ApiModelProperty(value = "问题id", example = "")
    private Integer formQuestionId;



    @ApiModelProperty(value = "选项内容", example = "")
    private String content;



    @ApiModelProperty(value = "描述", example = "")
    private String detail;

    @ApiModelProperty(value = "英文描述", example = "")
    private String enDetail;

    @ApiModelProperty(value = "图片", example = "")
    private String imgs;

    @ApiModelProperty(value = "英文选项内容", example = "")
    private String note;

    @ApiModelProperty(value = "子级问题列表", example = "")
    private List<FormQuestionListDTO> itemFormQuestionList;








}
