package com.ssw.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: cgyp_product_category
 * @BelongsPackage: com.ssw.service
 * @Author: Wss
 * @CreateTime: 2020-02-06 12:51
 * @Description: 日志serveice
 */
public interface IOperateLogService {
    Boolean addOperateRecord(HttpServletRequest request, String handleModule, String handleType, String context);

}
