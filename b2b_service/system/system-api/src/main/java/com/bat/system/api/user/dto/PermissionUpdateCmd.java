package com.bat.system.api.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lim
 * @description: TODO
 * @date: 2018/4/11 15:50
 */
@Data
@ApiModel(value = "PermissionUpdateCmd", description = "权限更新")
public class PermissionUpdateCmd {

    @NotBlank(message = "P_PERMISSION_SERVICE_NAME_NULL")
    @ApiModelProperty(value = "服务名称", required = true, example = "系统服务")
    private String service;

    @NotBlank(message = "P_PERMISSION_MODULE_NAME_NULL")
    @ApiModelProperty(value = "模块名称", required = true, example = "权限管理")
    private String module;

    @NotNull(message = "P_PERMISSION_ID_NULL")
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

    @NotBlank(message = "P_PERMISSION_NAME_NULL")
    @ApiModelProperty(value = "权限名称", required = true, example = "权限查看")
    private String permissionName;

    @ApiModelProperty(value = "权限英文名称", example = "Permission get")
    private String permissionModule;

    @NotBlank(message = "P_URL_PATH_NULL")
    @ApiModelProperty(value = "访问url地址", required = true, example = "/system/v1/web/permission")
    private String urlPath;

    @NotBlank(message = "P_PERMISSION_METHOD_NULL")
    @ApiModelProperty(value = "访问方法", required = true, example = "get")
    private String method;
}
