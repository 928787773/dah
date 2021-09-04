package com.qrqy.dah.service;

import com.qrqy.dah.qo.GetTokenQO;
import com.qrqy.dah.utils.QiniuUtils;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

/**
 * route : common-qiniu-token-get
 *
 * @author : QRQY
 * @date : 2021-06-30 10:56
 */
@Service
@Slf4j
@Validated
@Api(value = "获取七牛token", tags = {"获取七牛token", "标签", "这里放筛选标签"})
public class CommonQiniuTokenGetService implements IBaseService<GetTokenQO> {

    @Autowired
    private QiniuUtils qiniuUtils;

    @Override
    public ICommonResult execute(@Valid GetTokenQO qo, HttpServletRequest request, IBaseUser curUser, String version) {

        String upToken = qiniuUtils.uploadToken("dongaohui-file");
        Map<String, String> map = new HashMap<>();
        map.put("token", upToken);

        return new CommonObjectResult<>(map);

    }
}
