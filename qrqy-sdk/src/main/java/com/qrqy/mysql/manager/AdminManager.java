package com.qrqy.mysql.manager;

import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.mysql.entity.AdminEntity;
import com.qrqy.mysql.entity.AdminSystemModularEntity;
import com.qrqy.mysql.entity.VerifyCodeEntity;
import com.qrqy.mysql.repository.AdminRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import com.qrqy.mysql.repository.VerifyCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : Luis
 * @date : 2021-06-10 13:57
 */
@Component
@Slf4j
public class AdminManager extends BaseMysqlManager<AdminRepository, AdminEntity> {
    @Autowired
    private AdminRepository repository;

    @Autowired
    private VerifyCodeRepository verifyCodeRepository;



    @Override
    protected AdminRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

    /**
     * 验证码登录
     * @param phonenum
     * @param vertifyCode
     * @return
     */
    public AdminEntity verifyCodeLogin(String phonenum, String vertifyCode) {
        Specification query = new BaseMysqlQuery().append("phonenum", phonenum);

        Optional<AdminEntity> beanOptional = repository.findOne(query);
        log.info("管理员 beanOptional{}",beanOptional);
        if (beanOptional.isPresent()) {
            AdminEntity bean = beanOptional.get();

//            List<VerifyCodeEntity> verifyCodeList = verifyCodeRepository.findAll(query,Sort.sort("createdAt").descending());
            List<VerifyCodeEntity> verifyCodeList = verifyCodeRepository.findAll();
            log.info("验证码 list{}",verifyCodeList);
            if (verifyCodeList.isEmpty()) {
                throw BizException.DATA_NOT_FOUND.withMessage("验证码错误");
            }else{
                VerifyCodeEntity beanVerifyCode = verifyCodeList.get(0);
                log.info("验证码 list0{}",beanVerifyCode);
                if(beanVerifyCode.getStatus().equals("1") && beanVerifyCode.getCode().equals(vertifyCode)){
                    beanVerifyCode.setStatus("0");
                    verifyCodeRepository.save(beanVerifyCode);
                }else{
                    throw BizException.DATA_NOT_FOUND.withMessage("验证码错误");
                }
            }
            return bean;
        } else {
            throw BizException.DATA_NOT_FOUND.withMessage("用户不存在");
        }
    }

    /**
     * 密码登录
     * @param phonenum
     * @param password
     * @return
     */
    public AdminEntity passwordLogin(String phonenum, String password) {
        Specification query = new BaseMysqlQuery().append("phonenum", phonenum);

        Optional<AdminEntity> beanOptional = repository.findOne(query);
        log.info("管理员 beanOptional{}",beanOptional);
        if (beanOptional.isPresent()) {
            AdminEntity bean = beanOptional.get();
            if (!bean.getStatus().equals("1")) {
                throw BizException.DATA_NOT_FOUND.withMessage("管理员已停用");
            }else{
                if (!password.equals(bean.getPassword())) {
                    throw BizException.DATA_NOT_FOUND.withMessage("密码错误");
                }
            }
            return bean;
        } else {
            throw BizException.DATA_NOT_FOUND.withMessage("用户不存在");
        }
    }






}
