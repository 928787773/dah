package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-28 17:40
 */
@Data
public class FormQuestionIdQO extends PageableQO {
    @NotNull
    private Integer id;
}
