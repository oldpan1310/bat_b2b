package com.bat.system.api.organization.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lim
 * @description: TODO
 * @date: 2018/4/11 15:49
 */
@Data
@ApiModel(value = "UserId", description = "后台用户id")
public class OrganizationIds {
    @NotEmpty(message = "P_ORGANIZATION_ID_NULL")
    @ApiModelProperty(value = "ids", required = true, example = "1")
    private List<Integer> ids;
}
