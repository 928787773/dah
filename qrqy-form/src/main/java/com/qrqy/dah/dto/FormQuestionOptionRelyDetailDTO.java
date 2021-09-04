package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.FormQuestionEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * route : admin-form-question-option-rely-get
 * @author : QRQY
 * @date : 2021-06-24 14:26
 */
@Data
public class FormQuestionOptionRelyDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 922177190015261072L;

	private FormQuestionListDTO parentFormQuestion;

	private List<Integer> parentFormQuestionOptionIdArray;

	private List<String> parentFormQuestionOptionCodeArray;


}
