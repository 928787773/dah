package com.qrqy.dah.service;

import com.alibaba.fastjson.JSONObject;
import com.qrqy.dah.dto.CurrentFormAnswerRecordJsonDTO;
import com.qrqy.dah.qo.FormAnswerRecordEditQO;
import com.qrqy.dah.qo.FormRecordEditQO;
import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import com.qrqy.mysql.entity.FormAnswerRecordEntity;
import com.qrqy.mysql.entity.FormQuestionEntity;
import com.qrqy.mysql.entity.FormQuestionOptionEntity;
import com.qrqy.mysql.entity.FormRecordEntity;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import com.qrqy.mysql.manager.FormQuestionManager;
import com.qrqy.mysql.manager.FormRecordManager;
import com.qrqy.mysql.repository.FormAnswerRecordRepository;
import com.qrqy.mysql.repository.FormQuestionOptionRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 * route : admin-form-record-save
 *
 * @author : QRQY
 * @date : 2021-06-22 15:36
 */
@Service
@Slf4j
@EnableAsync
public class FormAnswerRecordImplSaveService{

    @Autowired
    private FormRecordManager manager;

    @Autowired
    private FormQuestionManager formQuestionManager;

    @Autowired
    private FormAnswerRecordRepository formAnswerRecordRepository;


    @Autowired
    private FormQuestionOptionRepository formQuestionOptionRepository;


    /**
     *异步保存详细答题记录
     * @param formAnswerRecordList
     * @param formRecord
     * @param curUser
     * @return
     */
    @Async
    public void saveFormAnswerRecord(List<FormAnswerRecordEditQO> formAnswerRecordList, FormRecordEntity formRecord, IBaseUser curUser){
        //处理
        List<BigDecimal> totalScoreList = new ArrayList<>();
        formAnswerRecordList.forEach(t->{
            FormQuestionEntity formQuestion = formQuestionManager.get(t.getFormQuestionId());
            if (formQuestion != null) {
                if(!formQuestion.getType().equals(FormQuestionTypeEnum.FILLIN)){
                    //多选情况有多个选项id
                    String[] formOptionIdStringArray= t.getFormOptionIds().split(",");
                    List<Integer> formOptionIdArray = new ArrayList<>();
                    for(int i = 0; i < formOptionIdStringArray.length; i++){
                        formOptionIdArray.add(Integer.parseInt(formOptionIdStringArray[i]));
                    }
                    //查看选项
                    Specification queryOption = new BaseMysqlQuery()
                            .append("status", "1")
                            .append("idIn", formOptionIdArray);

                    List<FormQuestionOptionEntity> formQuestionOptionList = formQuestionOptionRepository.findAll(queryOption);
                    BigDecimal score = new BigDecimal(formQuestionOptionList.stream().mapToInt(it->it.getScore().intValue()).sum());

                    //整理每道题的答题json
                    CurrentFormAnswerRecordJsonDTO currentFormAnswerRecordJsonDTO = new CurrentFormAnswerRecordJsonDTO();
                    BeanUtils.copyProperties(formQuestion, currentFormAnswerRecordJsonDTO, "status");
                    currentFormAnswerRecordJsonDTO.setCurrentFormQuestionOptionList(formQuestionOptionList);
                    currentFormAnswerRecordJsonDTO.setValue(t.getValue());
                    //转换格式
                    String json = JSONObject.toJSONString(currentFormAnswerRecordJsonDTO);

                    //开始插入数据
                    FormAnswerRecordEntity formAnswerRecordEntity = new FormAnswerRecordEntity();
                    formAnswerRecordEntity.setStatus("1");
                    formAnswerRecordEntity.setFormQuestionId(t.getFormQuestionId());
                    formAnswerRecordEntity.setFormOptionIds(t.getFormOptionIds());
                    formAnswerRecordEntity.setValue(t.getValue());
                    formAnswerRecordEntity.setFormRecordId(formRecord.getId());
                    formAnswerRecordEntity.setCurrentFormAnswerRecordJson(json);
                    formAnswerRecordEntity.setScore(score);
                    formAnswerRecordRepository.save(formAnswerRecordEntity);

                    totalScoreList.add(score);
                }
            }else{
                throw new BizException(6001, "提交失败");
            }

        });
        BigDecimal totalScore = BigDecimal.valueOf(totalScoreList.stream().flatMapToDouble(s -> DoubleStream.of(s.doubleValue())).sum());
        formRecord.setStatus("1");
        formRecord.setTotalScore(totalScore);
        manager.save(formRecord, curUser);
    }

    /**
     *
     * @param formAnswerRecordList
     * @param formRecord
     * @param curUser
     * @return
     */
//    @Async
//    public BigDecimal saveApiFormAnswerRecord(List<FormAnswerRecordEditQO> formAnswerRecordList, FormRecordEntity formRecord, IBaseUser curUser){
//
//        List<BigDecimal> totalScoreList = new ArrayList<>();
//        formAnswerRecordList.forEach(t->{
//            //处理
//            FormQuestionEntity formQuestion = formQuestionManager.get(t.getFormQuestionId());
//            if(formQuestion != null){
//                if(!formQuestion.getType().equals(FormQuestionTypeEnum.FILLIN)){
//                    //多选情况有多个选项id
//                    log.info("formQuestion {}:",formQuestion);
//                    String[] formOptionIdStringArray= t.getFormOptionIds().split(",");
//                    List<Integer> formOptionIdArray = new ArrayList<>();
//                    for(int i = 0; i < formOptionIdStringArray.length; i++){
//                        formOptionIdArray.add(Integer.parseInt(formOptionIdStringArray[i]));
//                    }
//                    //查看选项
//                    Specification queryOption = new BaseMysqlQuery()
//                            .append("status", "1")
//                            .append("idIn", formOptionIdArray);
//
//                    List<FormQuestionOptionEntity> formQuestionOptionList = formQuestionOptionRepository.findAll(queryOption);
//                    BigDecimal score = new BigDecimal(formQuestionOptionList.stream().mapToInt(it->it.getScore().intValue()).sum());
//
//                    //整理每道题的答题json
//                    CurrentFormAnswerRecordJsonDTO currentFormAnswerRecordJsonDTO = new CurrentFormAnswerRecordJsonDTO();
//                    BeanUtils.copyProperties(formQuestion, currentFormAnswerRecordJsonDTO, "status");
//                    currentFormAnswerRecordJsonDTO.setCurrentFormQuestionOptionList(formQuestionOptionList);
//                    currentFormAnswerRecordJsonDTO.setValue(t.getValue());
//                    //转换格式
//                    String json = JSONObject.toJSONString(currentFormAnswerRecordJsonDTO);
//
//                    //开始插入数据
//                    FormAnswerRecordEntity formAnswerRecordEntity = new FormAnswerRecordEntity();
//                    formAnswerRecordEntity.setStatus("1");
//                    formAnswerRecordEntity.setFormQuestionId(t.getFormQuestionId());
//                    formAnswerRecordEntity.setFormOptionIds(t.getFormOptionIds());
//                    formAnswerRecordEntity.setValue(t.getValue());
//                    formAnswerRecordEntity.setFormRecordId(formRecord.getId());
//                    formAnswerRecordEntity.setCurrentFormAnswerRecordJson(json);
//                    formAnswerRecordEntity.setScore(score);
//                    formAnswerRecordRepository.save(formAnswerRecordEntity);
//
//                    totalScoreList.add(score);
//                }
//            }else{
//                throw new BizValidationException("FormQuestion", "未找到问题");
//            }
//        });
//        BigDecimal totalScore = BigDecimal.valueOf(totalScoreList.stream().flatMapToDouble(s -> DoubleStream.of(s.doubleValue())).sum());
//
//        formRecord.setTotalScore(totalScore);
//        formRecord.setStatus("1");
//        return totalScore;
//    }



}
