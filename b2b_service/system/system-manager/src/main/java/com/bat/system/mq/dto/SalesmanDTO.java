package com.bat.system.mq.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Lim
 * @version 1.0
 * @description: TODO
 * @date 2018/6/13 10:49
 */
@Data
public class SalesmanDTO implements Serializable {

    // 业务员ID
    private Integer salesId;

    // 业务员所在新部门
    private Integer newDepartmentId;

    // 业务员所在老部门
    private Integer oldDepartmentId;

}
