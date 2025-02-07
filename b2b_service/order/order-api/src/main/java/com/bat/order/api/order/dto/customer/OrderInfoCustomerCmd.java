package com.bat.order.api.order.dto.customer;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bat.order.api.order.dto.distributor.OrderLogisticsCmd;
import com.bat.order.api.order.dto.common.OrderInvoiceCmd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "下单信息")
public class OrderInfoCustomerCmd {
    @NotNull(message = "P_ORDER_INVOICE_FLAG_NULL")
    @ApiModelProperty(value = "是否开具发票 0.否，1.是(不填、默认是0)", required = true, example = "0")
    private Short invoiceFlag;
    @ApiModelProperty(value = "结算币种 人民币CNY(不填、默认为CNY)", required = false, example = "CNY")
    private String currencyType;
    @ApiModelProperty(value = "付款方式 1.支付宝,2.微信,3.区间结算,4.线下转账,5.余额支付,6.快钱支付,7.余额+支付宝,8.余额+微信,9.余额+快钱支付", required = true,
        example = "1")
    @NotNull(message = "P_ORDER_PAY_WAY_NULL")
    private Short payWay;
    @ApiModelProperty(value = "订单结算金额(优惠后总金额，包含物流费用)", required = true, example = "15.65")
    @NotNull(message = "P_ORDER_ORDER_AMOUNT_NULL")
    private BigDecimal orderAmount;
    @ApiModelProperty(value = "物流费用", required = true, example = "15.65")
    @NotNull(message = "P_ORDER_DISTRIBUTION_AMOUNT_NULL")
    private BigDecimal distributionAmount;
    @ApiModelProperty(value = "订单是否拆分(在途在库) 1、拆 0、否（默认为否）", required = false, example = "0")
    private Short onWaySplitFlag;
    @ApiModelProperty(value = "订单备注", required = false, example = "0")
    private String remark;
    @ApiModelProperty(value = "店铺编码", required = false, example = "4666")
    private String shopCode;
    @ApiModelProperty(value = "店铺名称", required = false, example = "bat")
    private String shopName;

    @NotNull(message = "P_ORDER_GOODS_NULL")
    @Valid
    @ApiModelProperty(value = "下单商品明细列表", required = true)
    protected List<OrderGoodsCustomerCmd> goodss;

    @NotNull(message = "P_ORDER_LOGISTICS_NULL")
    @Valid
    @ApiModelProperty(value = "配送方式列表", required = true)
    private List<OrderLogisticsCmd> logisticss;

    @NotNull(message = "P_ORDER_DELIVERY_NULL")
    @Valid
    @ApiModelProperty(value = "收货地址信息", required = true)
    private OrderDeliveryCmd delivery;

    @Valid
    @ApiModelProperty(value = "发票信息", required = true)
    private OrderInvoiceCmd invoice;
}