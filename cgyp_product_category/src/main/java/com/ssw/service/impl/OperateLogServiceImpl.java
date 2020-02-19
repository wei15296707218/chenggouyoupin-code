package com.ssw.service.impl;

import com.edu.pojo.User;
import com.ssw.dao.OperateLogMapper;
import com.ssw.pojo.OperateLog;
import com.ssw.service.IOperateLogService;
import com.ssw.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: cgyp_product_category
 * @BelongsPackage: com.ssw.service.impl
 * @Author: Wss
 * @CreateTime: 2020-02-06 12:52
 * @Description: 日志实现类
 */
@Service
public class OperateLogServiceImpl implements IOperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public Boolean addOperateRecord(HttpServletRequest request, String handleModule, String handleType, String context) {

        Boolean flag = true;
        User user = (User)request.getSession().getAttribute(Const.CURRENT_USER);
        OperateLog operateLog = new OperateLog();
        operateLog.setUserId(user==null?null:user.getId());
        operateLog.setUserName(user==null?"游客":user.getUsername());
        operateLog.setUserIp(request.getLocalAddr());
        operateLog.setUserRole(user==null?null:user.getRole());
        operateLog.setHandleModule(handleModule);
        operateLog.setHandleType(handleType);
        operateLog.setContext(context);
        int result = operateLogMapper.insert(operateLog);
        if(result <= 0){
            flag=false;
        }
        return flag;
    }
}