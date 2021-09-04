package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FormQuestionOptionRelyEditQO extends PageableQO {


    @ApiModelProperty(value = "父级题目id", example = "1")
    @NotNull
    private Integer parentFormQuestionId;

    @ApiModelProperty(value = "父级题目选项id数组", example = "1")
    @NotNull
    private List<String> parentFormQuestionOptionCodeList;




}
