package com.ssw.controller.backend;

import com.ssw.common.HandelType;
import com.ssw.common.ServerResponse;
import com.ssw.service.IOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssw.common.ModuleConst;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: cgyp_product_category
 * @BelongsPackage: com.ssw.controller.backend
 * @Author: Wss
 * @CreateTime: 2020-02-06 12:58
 * @Description: 日志controller
 */
@RestController
@RequestMapping("/OperateRecord/")
public class OperateLogController {

    @Autowired
    IOperateLogService operateLogService;

    @RequestMapping("addOperateRecord")
    public Boolean addOperateRecord(HttpServletRequest request){

        return operateLogService.addOperateRecord(request,ModuleConst.PRODUCT_MODULE, HandelType.INSERT_TYPE,"111");
    }
}