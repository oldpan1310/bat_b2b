/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.sys.controller;

import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.entity.SysCacheEntity;
import com.platform.modules.sys.service.SysCacheService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统缓存管理
 *
 * @author 李鹏军
 */
@RestController
@RequestMapping("/sys/cache")
public class SysCacheController {
    @Autowired
    SysCacheService sysCacheService;

    /**
     * 查询缓存
     *
     * @param params 查询参数
     * @return
     */
    @RequiresPermissions("sys:cache:queryAll")
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, String> params) {
        String type = params.get("type");
        if (Constant.STR_ONE.equals(type)) {
            //查询所有缓存
            params.put("pattern", "*");
        } else if (Constant.STR_TWO.equals(type)) {
            //查询session缓存
            params.put("pattern", Constant.SESSION + "*");
        } else if (Constant.STR_THREE.equals(type)) {
            //查询系统缓存
            params.put("pattern", Constant.SYS_CACHE + "*");
        } else if (Constant.STR_FOUR.equals(type)) {
            //查询业务缓存
            params.put("pattern", Constant.MTM_CACHE + "*");
        }
        List<SysCacheEntity> list = sysCacheService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 删除cache
     *
     * @param keys keys
     * @return RestResponse
     */
    @SysLog("删除redis缓存")
    @RequiresPermissions("sys:cache:deleteCache")
    @RequestMapping("/deleteCache")
    public RestResponse deleteBatch(@RequestBody String[] keys) {
        sysCacheService.deleteBatch(keys);

        return RestResponse.success();
    }
}
