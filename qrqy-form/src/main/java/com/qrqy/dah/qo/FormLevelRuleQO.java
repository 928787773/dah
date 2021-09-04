package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-17 16:34
 */
@Data
public class FormLevelRuleQO extends PageableQO {
    /**
     * 等级:DISSATISFACTION:不满意;PARTLYDISSATISFACTION:比较不满意;PARTLYSATISFACTION: 比较满意;SATISFACTION:满意;HIGHLYSATISFACTORY:非常满意
     * 评定等级,本次只用到 "满意" 等级
     */
    @ApiModelProperty(value = "等级", example = "HIGHLYSATISFACTORY")
    @NotBlank
    private String level;

    @ApiModelProperty(value = "满意分数", example = "60")
    @NotNull
    private BigDecimal startScore;
}
