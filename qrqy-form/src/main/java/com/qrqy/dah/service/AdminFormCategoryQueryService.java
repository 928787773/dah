package com.qrqy.dah.service;

import com.qrqy.mysql.entity.FormInfoEntity;
import com.qrqy.mysql.entity.FormLevelRuleEntity;
import com.qrqy.mysql.entity.FormRecordEntity;
import com.qrqy.mysql.repository.FormCategoryRepository;
import com.qrqy.mysql.repository.FormInfoRepository;
import com.qrqy.mysql.repository.FormLevelRuleRepository;
import com.qrqy.mysql.repository.FormRecordRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.util.stream.DoubleStream;

import com.qrqy.mysql.entity.FormCategoryEntity;
import com.qrqy.mysql.manager.FormCategoryManager;
import com.qrqy.dah.qo.FormCategoryQueryQO;
import com.qrqy.dah.dto.FormCategoryListDTO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : admin-form-category-query
 *
 * @author : QRQY
 * @date : 2021-06-16 17:23
 */
@Service
@Slf4j
@Validated
@Api(value = "问卷方向列表", tags = {"管理端", "调查问卷", "这里放筛选标签"})
public class AdminFormCategoryQueryService implements IBaseService<FormCategoryQueryQO> {
    @Autowired
    private FormCategoryManager manager;

    @Autowired
    private FormCategoryRepository repository;



    @Autowired
    private FormInfoRepository formInfoRepository;

    @Autowired
    private FormRecordRepository formRecordRepository;

    @Autowired
    private FormLevelRuleRepository formLevelRuleRepository;




    @Override
    public ICommonResult execute(@Valid FormCategoryQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        Specification query = new BaseMysqlQuery(qo).orderBy("createdAt", Sort.Direction.DESC);

        Page<FormCategoryEntity> page = manager.query(query, qo.getPageable(), curUser);
        //1 取出问卷
        List<Integer> formCategoryIdList = page.getContent().stream().map(FormCategoryEntity::getId).collect(Collectors.toList());
        BaseMysqlQuery queryFormInfo = new BaseMysqlQuery()
                .append("formCategoryIdIn",formCategoryIdList);

        List<FormInfoEntity> formInfoEntityList = formInfoRepository.findAll(queryFormInfo);

        //2 获取满意度标准
        List<Integer> formInfoIdList = formInfoEntityList.stream().map(FormInfoEntity::getId).collect(Collectors.toList());
        BaseMysqlQuery queryFormLevelRule = new BaseMysqlQuery()
                .append("formInfoIdIn",formInfoIdList)
                .append("status","1");
        List<FormLevelRuleEntity> formLevelRuleEntityList = formLevelRuleRepository.findAll(queryFormLevelRule);

        //3 取出问卷的所有答题记录
        BaseMysqlQuery queryFormRecord = new BaseMysqlQuery()
                .append("formInfoIdIn",formInfoIdList)
                .append("status","1");

        List<FormRecordEntity> formRecordEntityList = formRecordRepository.findAll(queryFormRecord);

        //4 根据问卷id给问卷记录分组
        Map<Integer, List<FormRecordEntity>> formRecordMap= formRecordEntityList.stream().collect(Collectors.groupingBy(FormRecordEntity::getFormInfoId));

        //5 根据问卷id给问卷满意分数数据分组
        Map<Integer, List<FormLevelRuleEntity>> formLevelRuleMap= formLevelRuleEntityList.stream().collect(Collectors.groupingBy(FormLevelRuleEntity::getFormInfoId));

        //5 根据问卷方向id给问卷分组
        Map<Integer, List<FormInfoEntity>> formInfoMap= formInfoEntityList.stream().collect(Collectors.groupingBy(FormInfoEntity::getFormCategoryId));



        List<FormCategoryListDTO> content = page.getContent().stream().map(t -> {
            FormCategoryListDTO dto = new FormCategoryListDTO();
            BeanUtils.copyProperties(t, dto);

            //权重满意度:(单个问卷的权重/该方向下所有问卷权重的和)*((问卷的答题记录中得分>=form_level_rule对应的问卷满意分数的个数)/问卷的答题记录总个数)
            //每个问卷权重满意度相加成100变成百分比,保留小数点后两位
            long formInfoNumber = 0L;
            long formRecordNumber = 0L;
            BigDecimal satisfactionNum = new BigDecimal(0);
            if(formInfoMap.containsKey(dto.getId())){
                formInfoNumber = formInfoMap.get(dto.getId()).size();
                List<BigDecimal> itemSatisfactionNumList = new ArrayList<>();
                List<Long> formRecordnumberList = new ArrayList<>();
                BigDecimal totalWeight = new BigDecimal(formInfoMap.get(dto.getId()).stream().mapToInt(FormInfoEntity::getWeight).sum());
                formInfoMap.get(dto.getId()).forEach(it->{
                    //满意分数
                    BigDecimal formLevelRuleScore = formLevelRuleMap.get(it.getId()).get(0).getStartScore();
                    if(formRecordMap.containsKey(it.getId())){
                        //总答题数
                        long itemFormRecordnumber = formRecordMap.get(it.getId()).size();
                        formRecordnumberList.add(itemFormRecordnumber);
                        if(itemFormRecordnumber>0 && totalWeight.compareTo(BigDecimal.valueOf(0)) > 0){
                            //达到满意分数的问卷记录个数
                            long levelFormRecordnumber = formRecordMap.get(it.getId()).stream().filter(l->l.getTotalScore().compareTo(formLevelRuleScore) >= 0).count();
                            //计算满意度
                            BigDecimal formInfoSatisfactionNum = new BigDecimal(levelFormRecordnumber).divide(new BigDecimal(itemFormRecordnumber),4,BigDecimal.ROUND_HALF_UP);
                            BigDecimal weight = new BigDecimal(it.getWeight()).divide(totalWeight,4,BigDecimal.ROUND_HALF_UP);
                            BigDecimal itemSatisfactionNum = weight.multiply(formInfoSatisfactionNum).setScale(4,BigDecimal.ROUND_HALF_UP);
                            itemSatisfactionNumList.add(itemSatisfactionNum);
                        }

                    }
                });
                satisfactionNum = BigDecimal.valueOf(itemSatisfactionNumList.stream().flatMapToDouble(s -> DoubleStream.of(s.doubleValue())).sum()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
                formRecordNumber = (long) formRecordnumberList.stream().flatMapToDouble(s -> DoubleStream.of(s.doubleValue())).sum();
            }

            dto.setFormInfoNumber(formInfoNumber);
            dto.setFormRecordnumber(formRecordNumber);
            dto.setSatisfactionNum(satisfactionNum);

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
    public void validate(FormCategoryQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
