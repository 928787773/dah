package com.qrqy.dah.service;

import com.qrqy.dp.utils.PagingBean;
import com.qrqy.mysql.entity.*;
import com.qrqy.mysql.enumeration.CaseInfoVisitTypeEnum;
import com.qrqy.mysql.repository.FormInfoRepository;
import com.qrqy.mysql.repository.FormLevelRuleRepository;
import com.qrqy.mysql.repository.FormRecordRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.qrqy.mysql.manager.FormInfoManager;
import com.qrqy.dah.qo.FormInfoQueryQO;
import com.qrqy.dah.dto.FormInfoListDTO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : admin-form-info-query
 *
 * @author : QRQY
 * @date : 2021-06-21 10:34
 */
@Service
@Slf4j
@Validated
@Api(value = "问卷列表", tags = {"管理端", "问卷", "这里放筛选标签"})
public class AdminFormInfoQueryService implements IBaseService<FormInfoQueryQO> {
    @Autowired
    private FormInfoRepository repository;

    @Autowired
    private FormInfoManager manager;



    @Autowired
    private FormRecordRepository formRecordRepository;

    @Autowired
    private FormLevelRuleRepository formLevelRuleRepository;

    @Override
    public ICommonResult execute(@Valid FormInfoQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);


        Specification query = new BaseMysqlQuery(qo).orderBy("createdAt", Sort.Direction.DESC);

        Page<FormInfoEntity> page = manager.query(query, qo.getPageable(), curUser);
        //1 取出问卷的所有答题记录
        List<Integer> formInfoIdList = page.getContent().stream().map(FormInfoEntity::getId).collect(Collectors.toList());
        BaseMysqlQuery queryFormRecord = new BaseMysqlQuery()
                .append("formInfoIdIn",formInfoIdList)
                .append("status","1");

        List<FormRecordEntity> formRecordEntityList = formRecordRepository.findAll(queryFormRecord);

        //2 获取满意分数
        BaseMysqlQuery queryFormLevelRule = new BaseMysqlQuery()
                .append("formInfoIdIn",formInfoIdList)
                .append("status","1");
        List<FormLevelRuleEntity> formLevelRuleEntityList = formLevelRuleRepository.findAll(queryFormLevelRule);

        //3 根据问卷id给问卷记录分组
        Map<Integer, List<FormRecordEntity>> formRecordMap= formRecordEntityList.stream().collect(Collectors.groupingBy(FormRecordEntity::getFormInfoId));

        //4 根据问卷id给问卷满意分数数据分组
        Map<Integer, List<FormLevelRuleEntity>> formLevelRuleMap= formLevelRuleEntityList.stream().collect(Collectors.groupingBy(FormLevelRuleEntity::getFormInfoId));

        List<FormInfoListDTO> content = page.getContent().stream().map(t -> {
            FormInfoListDTO dto = new FormInfoListDTO();
            BeanUtils.copyProperties(t, dto);

            //满意度:((问卷的答题记录中得分>=form_level_rule对应的问卷满意分数的个数)/问卷的答题记录总个数)*100,百分比的,保留两位小数
            long formRecordnumber = 0L;
            BigDecimal satisfactionNum = new BigDecimal(0);
            if(formRecordMap.containsKey(t.getId())){
                formRecordnumber = formRecordMap.get(t.getId()).size();
                //满意分数
                BigDecimal formLevelRuleScore = formLevelRuleMap.get(t.getId()).get(0).getStartScore();
                //达到满意分数的问卷记录个数
                long levelFormRecordnumber = formRecordMap.get(t.getId()).stream().filter(l->l.getTotalScore().compareTo(formLevelRuleScore) >= 0).count();
                //计算满意度
                satisfactionNum = new BigDecimal(levelFormRecordnumber).divide(new BigDecimal(formRecordnumber),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            }
            dto.setSatisfactionNum(satisfactionNum);
            dto.setFormRecordnumber(formRecordnumber);
            return dto;
        }).collect(Collectors.toList());

        return new CommonPagingResult<>(content, page);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormInfoQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }

}
