package com.qrqy.dah.service;

import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.FormQuestionOptionEntity;
import com.qrqy.mysql.entity.FormQuestionOptionRelyEntity;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import com.qrqy.mysql.manager.FormQuestionOptionManager;
import com.qrqy.mysql.repository.FormQuestionOptionRelyRepository;
import com.qrqy.mysql.repository.FormQuestionOptionRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.qrqy.mysql.entity.FormQuestionEntity;
import com.qrqy.mysql.manager.FormQuestionManager;
import com.qrqy.dah.qo.FormQuestionEditQO;

import java.util.List;
import java.util.Optional;

/**
 * route : admin-form-questin-save
 *
 * @author : QRQY
 * @date : 2021-08-17 10:14
 */

@Service
@Slf4j
@Validated
@Api(value = "保存问卷问题", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminFormQuestinSaveService implements IBaseService<FormQuestionEditQO> {
    @Autowired
    private FormQuestionManager manager;

    @Autowired
    private FormQuestionOptionManager formQuestionOptionManager;

    @Autowired
    private FormQuestionOptionRepository formQuestionOptionRepository;

     @Autowired
    private FormQuestionOptionRelyRepository formQuestionOptionRelyRepository;


    @Override
    public ICommonResult execute(@Valid FormQuestionEditQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        FormQuestionEntity entity;
        if (null == qo.getId()) {
            entity = new FormQuestionEntity();
        } else {
            entity = manager.get(qo.getId());

            //首先删除本题目历史关联信息
            Specification queryOptionrel = new BaseMysqlQuery()
                    .append("childFormQuestionId", entity.getId());
            List<FormQuestionOptionRelyEntity> formQuestionOptionRelyList= formQuestionOptionRelyRepository.findAll(queryOptionrel);
            if(formQuestionOptionRelyList.size()>0){
                formQuestionOptionRelyRepository.deleteAll(formQuestionOptionRelyList);
            }
        }
        BeanUtils.copyProperties(qo, entity, "status");
        entity.setStatus("1");
        entity.setLevel(0);
        manager.save(entity, curUser);
        //保存选项
        qo.getFormQuestionOptionList().forEach(t->{
            FormQuestionOptionEntity formQuestionOptionEntity;
            Integer id = null;
            Specification query = new BaseMysqlQuery().append("code", t.getCode()).append("formQuestionId", entity.getId());
            Optional formQuestionOption= formQuestionOptionRepository.findOne(query);
            if (formQuestionOption.isPresent()) {
                formQuestionOptionEntity = (FormQuestionOptionEntity) formQuestionOption.get();
                id = formQuestionOptionEntity.getId();
            }else{
                formQuestionOptionEntity = new FormQuestionOptionEntity();
            }
            BeanUtils.copyProperties(t, formQuestionOptionEntity, "status");
            formQuestionOptionEntity.setId(id);
            formQuestionOptionEntity.setStatus("1");
            formQuestionOptionEntity.setFormQuestionId(entity.getId());
            formQuestionOptionManager.save(formQuestionOptionEntity, curUser);
        });

//        //删除选项
//        if(qo.getDeleteOptionIdArray() != null){
//            qo.getDeleteOptionIdArray().forEach(it->{
//                FormQuestionOptionEntity formQuestionOption = formQuestionOptionManager.get(it);
//                if(formQuestionOption != null){
//                    formQuestionOptionManager.delete(it);
//                }
//            });
//        }
        //保存关联题目
        if(qo.getFormQuestionOptionRely() != null){
            if(qo.getFormQuestionOptionRely().getParentFormQuestionId() != null && qo.getFormQuestionOptionRely().getParentFormQuestionOptionCodeList() != null){
                //查出符合条件的选项列表
                Specification queryOptionList = new BaseMysqlQuery()
                        .append("codeIn", qo.getFormQuestionOptionRely().getParentFormQuestionOptionCodeList())
                        .append("formQuestionId", qo.getFormQuestionOptionRely().getParentFormQuestionId());
                List<FormQuestionOptionEntity> formQuestionOptionList= formQuestionOptionRepository.findAll(queryOptionList);
                formQuestionOptionList.forEach(formQuestionOption->{
                    FormQuestionOptionRelyEntity formQuestionOptionRelyEntity = new FormQuestionOptionRelyEntity();
                    formQuestionOptionRelyEntity.setChildFormQuestionId(entity.getId());
                    formQuestionOptionRelyEntity.setParentFormQuestionId(qo.getFormQuestionOptionRely().getParentFormQuestionId());
                    formQuestionOptionRelyEntity.setParentFormQuestionOptionId(formQuestionOption.getId());
                    formQuestionOptionRelyEntity.setStatus("1");
                    formQuestionOptionRelyRepository.save(formQuestionOptionRelyEntity);
                });

                entity.setLevel(1);
                manager.save(entity, curUser);
            }
        }

        return new CommonObjectResult<>(entity);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormQuestionEditQO qo, IBaseUser curUser) {
        if (!qo.getType().equals(FormQuestionTypeEnum.FILLIN)) {

            if(qo.getFormQuestionOptionList().isEmpty()){
                throw new BizException(5801, "选项不能为空");
            }

        }
    }


}
