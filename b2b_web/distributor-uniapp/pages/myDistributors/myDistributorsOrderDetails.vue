<template>
  <view class="orderDetail">
    <view class="top-order-moudle">
      <view class="top-bg">
        <view class="status_bar"> <!-- 这里是状态栏 --></view>
        <view class="top-titleD">
          <image src="../../static/img/back_icon.png" @click="toBack"></image>
          <view>我的分销订单详情</view>
        </view>

        <view class="topBg-tip">
          <!-- 前端订单状态0.全部 1.待审核 2已同意 3已拒绝 4已拒绝 5已同意 -->
          <image
            src="../../static/img/filter_icon.png"
            v-if="orderDetail.frontOrderStatus == 7"
          ></image>
          <!-- 待确认-->
          <image
            src="../../static/img/confirm_icon.png"
            v-if="orderDetail.frontOrderStatus == 1"
          ></image>
          <!-- 待发货 -->
          <image
            src="../../static/img/shop_icon.png"
            v-if="orderDetail.frontOrderStatus == 2"
          ></image>
          <!-- 出库中 -->
          <image
            src="../../static/img/delivery_icon.png"
            v-if="orderDetail.frontOrderStatus == 9"
          ></image>
          <!-- 部分发货 -->
          <image
            src="../../static/img/some_icon.png"
            v-if="orderDetail.frontOrderStatus == 3"
          ></image>
          <!-- 已发货 -->
          <image
            src="../../static/img/yifahuo_icon.png"
            v-if="orderDetail.frontOrderStatus == 4"
          ></image>
          <!-- 已完成 -->
          <image
            src="../../static/img/wancheng_icon.png"
            v-if="
              orderDetail.frontOrderStatus == 6 ||
              orderDetail.frontOrderStatus == 5
            "
          ></image>
          <text>{{ confirmOrderStatus(orderDetail.frontOrderStatus) }}</text>
        </view>
      </view>
      <!-- 部分发货,已发货显示 &&orderDetail.frontOrderStatus!=5 -->
      <view
        class="orderDetail-delivery"
        v-if="
          orderDetail.frontOrderStatus != 7 &&
          orderDetail.frontOrderStatus != 1 &&
          orderDetail.frontOrderStatus != 2 &&
          orderDeliverDetail.length > 0
        "
      >
        <view
          class="delivery-infom"
          @click="toMyParcel"
          v-if="orderDeliverDetail.length > 0"
        >
          <!-- <image src="../../static/img/cart2_icon.png" class="delivery-cart"></image> -->
          <text class="iconfont icon-233 wuliuIcon"></text>
          <view
            >{{
              orderDeliverDetail[0].orderDeliverBill.distributionName
            }}，点击查看详情</view
          >
          <text class="iconfont icon-more moreIcon"></text>
        </view>
        <!-- 已发货才显示日期 -->
        <view
          class="delivery-date"
          v-if="
            (orderDetail.frontOrderStatus == 3 ||
              orderDetail.frontOrderStatus == 4 ||
              orderDetail.frontOrderStatus == 5) &&
            orderDeliverDetail.length > 0
          "
          >{{ orderDeliverDetail[0].orderDeliverBill.updateTime }}</view
        >
      </view>
      <!-- 部分发货显示-end -->
      <view class="top-logistics">
        <text class="iconfont icon-Addresslocation locationIcon"></text>
        <view class="top-logistics-ifm">
          <view class="top-logistics-namePhone"
            ><text>{{ orderDelivery.userName }}</text
            ><text>{{ orderDelivery.mobile }}</text></view
          >
          <view class="top-logistics-address">
            {{ orderDelivery.countryName }}
            {{ orderDelivery.provinceName }}
            {{ orderDelivery.cityName }}
            {{ orderDelivery.districtName ? orderDelivery.districtName : "" }}
            {{ orderDelivery.address }}
          </view>
        </view>
      </view>
    </view>

    <view class="orderDetails-inform">
      <view class="inform-label">商品信息</view>
      <view class="orderDetails-inform-category">
        <!-- 二类 -->
        <view
          class="orderDetails-inform-smallCategory"
          v-for="(item, oIndex) in orderGood"
          :key="oIndex"
        >
          <image :src="item.orderGoods.imageUrl"></image>
          <view class="inform-smallCategory-center">
            <view>
              颜色：{{
                item.orderGoods.colorName ? item.orderGoods.colorName : ""
              }}
            </view>
            <view v-if="item.orderGoods.specsName">
              规格：{{ item.orderGoods.specsName }}
            </view>
            <view>￥{{ 100 }}/件</view>
          </view>
          <view class="inform-smallCategory-btm">
            <view>
              <text>{{
                item.orderGoodsDistributorCost.actualPrice &&
                item.orderGoodsDistributorCost.actualPrice != 0
                  ? "￥"
                  : ""
              }}</text>
              <text>{{
                item.orderGoodsDistributorCost.actualPrice != 0
                  ? item.orderGoodsDistributorCost.actualPrice
                  : ""
              }}</text>
            </view>
            <view>下单：{{ item.orderGoods.itemCount }}</view>
            <view>
              发货：{{
                item.orderGoods.deliverCount ? item.orderGoods.deliverCount : 0
              }}
            </view>
          </view>
        </view>
        <!-- 二类-end -->
      </view>

      <view class="orderDetails-inform-line">
        <view class="inform-line">
          <view>用户留言</view>
          <view>{{ remark }}</view>
        </view>
        <view class="inform-line">
          <view>商品总价</view>
          <view class="inform-line-num">
            <text>￥</text>{{ orderDistributorCost.goodsAmount }}
          </view>
        </view>
        <view class="inform-line">
          <view>配送费</view>
          <view class="inform-line-num">
            ￥{{ orderDistributorCost.distributionAmount }}
          </view>
        </view>
        <view class="inform-line">
          <view>活动优惠</view>
          <view class="inform-line-num">
            <text>￥</text>
            {{
              (
                orderDistributorCost.payAmount -
                orderDistributorCost.distributionAmount -
                orderDistributorCost.goodsAmount
              ).toFixed(2)
            }}
          </view>
        </view>
        <view
          class="inform-line"
          v-if="orderDistributorCost.rebateVoucherAmount"
        >
          <view>代金券</view>
          <view class="inform-line-num">
            ￥-{{ orderDistributorCost.rebateVoucherAmount.toFixed(2) }}
          </view>
        </view>
        <view class="inform-total">
          共<text class="total-num">{{ totalCount }}</text> 件 实付总额
          <text class="total-text">（含运费）：</text>
          <text class="total-num">￥{{ orderDistributorCost.payAmount }}</text>
        </view>
      </view>
    </view>

    <view class="orderDetails-goodsInform">
      <view>
        <text>订单编号</text><text>{{ orderInfo.orderNo }}</text>
      </view>
      <view>
        <text>配送方式</text>
        <text>{{ orderDelivery.distributionName }}</text>
      </view>
      <view>
        <text>付款方式</text>
        <text v-if="orderDistributorData.payWay == 1">支付宝</text>
        <text v-if="orderDistributorData.payWay == 2">微信</text>
        <text v-if="orderDistributorData.payWay == 3">区间结算</text>
        <text v-if="orderDistributorData.payWay == 4">线下转账</text>
        <text v-if="orderDistributorData.payWay == 5">余额支付</text>
        <text v-if="orderDistributorData.payWay == 6">快钱支付</text>
      </view>
      <view>
        <text>创建时间</text>
        <text>{{ orderDistributorCost.createTime }}</text>
      </view>
      <!-- 前端订单状态 0.全部 1.待确认 2待发货 3部分发货 4待收货（已发货） 5已关闭 6已完成 7 待付款 -->
      <!-- 待确认，待发货，部分发货 -->
      <view v-if="orderDetail.frontOrderStatus != 7">
        <text>付款时间</text>
        <text>{{ orderDistributorCost.updateTime }}</text>
      </view>
      <!-- 待发货，部分发货 -->
      <view
        v-if="
          orderDetail.frontOrderStatus == 2 || orderDetail.frontOrderStatus == 3
        "
      >
        <text>确认时间</text>
        <text>{{ orderDistributorCost.updateTime }}</text>
      </view>
      <!-- 部分发货，已发货 -->
      <view v-if="orderDetail.frontOrderStatus == 4">
        <text>发货时间</text>
        <text>{{ orderInfo.deliverTime }}</text>
      </view>
      <!-- 已完成 -->
      <view v-if="orderDetail.frontOrderStatus == 6">
        <text>完成时间</text>
        <text>{{ orderDistributorCost.updateTime }}</text>
      </view>
    </view>

    <view class="orderDetails-btn" v-if="orderDistributorData.orderStatus == 1">
      <!-- 待支付 -->
      <!-- <text class="btn-cancel">取消</text> -->
      <!-- <text class="btn-pay" v-if="orderDetail.frontOrderStatus==7" @click="payFun">立即支付</text> -->
      <!-- 待确认，待发货，部分发货 ,已发货，已完成-->
      <text
        class="btn-cancel"
        v-if="orderDistributorData.orderStatus == 1"
        @click="auditFun"
      >
        立即审核
      </text>
      <!-- 已发货 -->
      <!-- <text class="btn-pay">确认收货</text> -->
    </view>

    <view class="tipText" v-show="tipTextShow">{{ tipText }}</view>
  </view>
</template>

<script>
import { orderMbDetail, orderChecktOrder } from "../../common/api.js";
import { isH5, isMpWeixin } from "../../common/common.js";
export default {
  data() {
    return {
      orderId: "",
      orderDetail: {},
      orderDelivery: {}, //收货地址信息
      orderGood: [], //货品列表
      remark: "", //备注
      orderDistributorCost: {},
      totalCount: 0, //总件数
      orderInfo: {}, //订单信息
      orderDistributorData: {},
      orderDeliverDetail: [], //物流信息
      orderDeliverInfo: {}, //物流信息第一个值
      distributorId: "",
      tipTextShow: false,
      tipText: "",
    };
  },
  onLoad(option) {
    this.orderId = option.id;
    this.distributorId = option.distributorId;
    this.orderMbDetailFun(option.id, option.distributorId);
  },
  methods: {
    toBack() {
      uni.navigateTo({
        url: "myDistributorsOrder",
      });
    },
    // 轻提示弹框
    tipFun(text) {
      let that = this;
      this.tipText = text;
      this.tipTextShow = true;
      setTimeout(function () {
        that.tipTextShow = false;
      }, 3000);
    },
    // 订单详情
    orderMbDetailFun(id, distributorId) {
      let that = this;
      let params = {
        distributorId: distributorId,
        orderId: id,
      };
      orderMbDetail(params).then((res) => {
        if (res.success) {
          console.log("订单详情：", res.data);
          that.orderDetail = res.data;
          if (
            that.orderDetail.frontOrderStatus == 3 ||
            that.orderDetail.frontOrderStatus == 4 ||
            that.orderDetail.frontOrderStatus == 5
          ) {
            that.orderDeliverDetail = res.data.orderDeliverDetail;
          }

          that.orderDelivery = res.data.orderDelivery;
          that.orderGood = res.data.orderInfoDetailGoods;
          that.remark = res.data.orderDistributorData.remark;
          that.orderDistributorCost = res.data.orderDistributorCost;
          that.orderInfo = res.data.orderInfo;
          that.orderDistributorData = res.data.orderDistributorData;
          res.data.orderInfoDetailGoods.forEach((item) => {
            that.totalCount += item.orderGoods.itemCount;
          });
        }
      });
    },
    // 订单状态--Y
    confirmOrderStatus(row) {
      //  前端订单状态 0.全部 1.待确认 2待发货 3部分发货 4待收货（已发货） 5已关闭 6已完成 7 待付款
      switch (row) {
        case 1:
          return "待确认"; //待确认
        case 2:
          return "待发货"; //待发货
        case 3:
          return "部分发货"; //部分发货
        case 4:
          return "已发货"; //已发货
        case 5:
          return "已关闭"; //已关闭
        case 6:
          return "已完成"; //已完成
        case 7:
          return "待付款"; //待付款
        case 9:
          return "出库中"; //出库中
      }
    },
    // 支付
    payFun() {
      let that = this;
      let PayWay = this.orderDistributorData.payWay;
      let payMethod = "";
      if (PayWay == 1) {
        payMethod = "alipay_wap";
      } else if (PayWay == 2) {
        payMethod = isH5 ? "wxpay_h5" : "wxpay_mini_program";
      } else if (PayWay == 5) {
        payMethod = "balance";
      } else if (PayWay == 6) {
        payMethod = "kuaiqian_merchant";
      }
      let payParams = {
        customerFlag: 0,
        orderId: that.orderDistributorData.orderId,
        payMethod: payMethod,
        payerId: uni.getStorageSync("userId"),
        payerName: uni.getStorageSync("userName"),
      };
      payCreateTrade(payParams).then((payRes) => {
        if (payRes.success) {
          if (PayWay == 6) {
            //快钱支付
            window.location.href = payRes.data.kuaiQianReap.h5Url; // 跳转链接
          } else if (PayWay == 2) {
            //微信
            let url = payRes.data.wxResp.from;
            window.location.href =
              url +
              "&redirect_url=" +
              encodeURIComponent(
                window.location.href.split("?")[0] +
                  "/checkstandSuccess?ids=" +
                  ids
              );
          } else if (PayWay == 1) {
            //支付宝
            let resData = payRes.data.alipayResp.from;
            const div = document.createElement("div");
            div.id = "alipay";
            div.innerHTML = resData;
            document.body.appendChild(div);
            document.querySelector("#alipay").children[0].submit(); // 执行后会唤起支付宝
          }
        }
      });
    },
    // 我的包裹
    toMyParcel() {
      uni.navigateTo({
        url: "myParcel?id=" + this.orderId,
      });
    },

    // 再来一单
    againOrderFun() {
      let that = this;
      let list = this.orderGood; //货品信息
      console.log("货品信息：", list);
      let isAllType = false; //货品在库在途是否同时存在
      let zaiKuAllType = false;
      let zaiTuAllType = false;
      let orderList = [];
      list.forEach((item) => {
        if (item.orderGoods.itemInCount > 0) {
          zaiKuAllType = true;
        }
        if (item.orderGoods.itemOnWayCount > 0) {
          zaiTuAllType = true;
        }
        that.$set(
          item.orderGoods,
          "zaiKuCount",
          item.orderGoods.itemInCount ? item.orderGoods.itemInCount : 0
        );
        that.$set(
          item.orderGoods,
          "zaiTuCount",
          item.orderGoods.itemOnWayCount ? item.orderGoods.temOnWayCount : 0
        );
        that.$set(
          item.orderGoods,
          "goodsPromotionId",
          item.orderGoodsDistributorCost.goodsPromotionId
            ? item.orderGoodsDistributorCost.goodsPromotionId
            : "no"
        );
        that.$set(
          item.orderGoods,
          "conditionsId",
          item.orderGoodsDistributorCost.goodsPromotionId
            ? item.orderGoodsDistributorCost.goodsPromotionId
            : "no"
        );
        that.$set(
          item.orderGoods,
          "salePrice",
          item.orderGoodsDistributorCost.salePrice
        );
        that.$set(
          item.orderGoods,
          "itemType",
          item.orderGoodsDistributorCost.itemType
        );
        that.$set(
          item.orderGoods,
          "groupSeckillId",
          item.orderGoodsDistributorCost.spellGroupId
            ? item.orderGoodsDistributorCost.spellGroupId
            : ""
        );
        that.$set(
          item.orderGoods,
          "lastPrice",
          item.orderGoodsDistributorCost.actualPrice * item.orderGoods.itemCount
        );
        orderList.push(item.orderGoods);
      });

      isAllType = zaiKuAllType & zaiTuAllType ? true : false;

      if (orderList.length == 0) {
        that.tipFun("请选择至少一个货品！");
      } else {
        uni.setStorageSync("shopOrderList", JSON.stringify(orderList));
        uni.navigateTo({
          url: "/pages/shoppingCart/confirmOrder?isTwoWay=" + isAllType,
        });
      }
    },
    // 立即审核
    auditFun(id) {
      let that = this;
      uni.showModal({
        title: "审核",
        content: "是否审核通过？",
        cancelText: "拒绝", // 取消按钮的文字
        confirmText: "通过", // 确认按钮文字
        showCancel: true, // 是否显示取消按钮，默认为 true
        confirmColor: "#09D5EE",
        cancelColor: "#999999",
        success: (res) => {
          if (res.confirm) {
            // 通过
            orderChecktOrder({
              ids: [that.orderDistributorData.orderId],
              orderStatus: 2,
            }).then((res) => {
              if (res.success) {
                that.tipFun("审核通过！");
                this.orderMbDetailFun(this.orderId);
              } else {
                that.tipFun(res.errMessage);
              }
            });
          } else if (res.cancel) {
            // 拒绝
            orderChecktOrder({
              ids: [that.orderDistributorData.orderId],
              orderStatus: 3,
            }).then((res) => {
              if (res.success) {
                that.tipFun("审核拒绝！");
                this.orderMbDetailFun(this.orderId);
              } else {
                that.tipFun(res.errMessage);
              }
            });
          }
        },
      });
    },
  },
};
</script>

<style lang="scss">
.wuliuIcon {
  font-size: 45rpx;
}
.locationIcon {
  font-size: 45rpx;
  color: $base-color1;
}
.orderDetail {
  padding-bottom: 30rpx;
  font-size: 26rpx;
  .top-order-moudle {
    background: #fff;
    top: 0;

    .top-bg {
      width: 100%;
      height: 332rpx;
      background: url(../../static/img/oderDetail_bg.png) no-repeat;
      background-size: 100% 100%;
      .top-titleD {
        display: flex;
        align-items: center;
        padding: 0 30rpx;
        image {
          width: 18rpx;
          height: 30rpx;
        }
        view {
          font-size: 32rpx;
          font-weight: 400;
          width: 650rpx;
          height: 80rpx;
          line-height: 80rpx;
          text-align: center;
        }
        /* #ifdef H5 */
        view {
          height: 88rpx;
          line-height: 88rpx;
        }
        /*#endif*/
      }
      .topBg-tip {
        // display: flex;
        // align-items: center;
        // justify-content: center;
        // margin-top: 25rpx;
        text-align: center;
        text {
          font-size: 40rpx;
          color: $base-color1;
          font-weight: 500;
          display: block;
          margin-top: -20rpx;
        }
        image {
          width: 180rpx;
          height: 180rpx;
          margin-top: -15rpx;
        }
      }
    }
    .top-logistics {
      display: flex;
      padding: 30rpx 30rpx 50rpx;
      image {
        width: 49rpx;
        height: 49rpx;
      }
      .top-logistics-ifm {
        font-size: 26rpx;
        margin-left: 20rpx;
        .top-logistics-namePhone {
          text:nth-child(2) {
            color: #999999;
            margin-left: 20rpx;
          }
        }
        .top-logistics-address {
          margin-top: 10rpx;
        }
      }
    }
    .orderDetail-delivery {
      padding: 50rpx 30rpx 30rpx;
      border-bottom: 1px solid $opacity-border;
      .delivery-infom {
        color: $base-color1;
        display: flex;
        align-items: center;

        .delivery-cart {
          width: 45rpx;
          height: 36rpx;
        }
        .delivery-right {
          width: 12rpx;
          height: 22rpx;
          margin-left: 20rpx;
        }
        view {
          margin: 0 21rpx;
        }
      }
      .delivery-date {
        font-size: 22rpx;
        font-weight: 400;
        color: #999;
        margin-left: 65rpx;
        margin-top: 10rpx;
      }
    }
  }
  .orderDetails-inform {
    background: #fff;
    margin-top: 20rpx;
    padding-bottom: 30rpx;
    // margin-top:550rpx;
    .inform-label {
      border-bottom: 1rpx solid $opacity-border;
      height: 73rpx;
      line-height: 73rpx;
      padding: 0 30rpx;
    }
    .orderDetails-inform-category {
      border-bottom: 1rpx solid $opacity-border;
      padding-bottom: 30rpx;
      .orderDetails-inform-bigCategory {
        display: flex;
        padding: 30rpx;
        box-shadow: 0px 3px 5px 1px rgba(9, 213, 238, 0.08);
        image {
          width: 160rpx;
          height: 160rpx;
        }
        .inform-bigCategory-rg {
          margin-left: 20rpx;

          .bigCategory-rg-name {
            font-weight: bold;
            margin-bottom: 15rpx;
          }
          .bigCategory-rg-num {
            font-size: 22rpx;
            color: #999;
            margin-top: 6rpx;
          }
        }
      }
      .orderDetails-inform-smallCategory {
        display: flex;
        padding: 30rpx 30rpx 0 30rpx;

        image {
          width: 111rpx;
          height: 111rpx;
        }
        .inform-smallCategory-center {
          font-size: 22rpx;
          color: #999;
          margin-left: 20rpx;
          width: 400rpx;
          view:nth-child(2),
          view:nth-child(3) {
            margin-top: 5rpx;
          }
        }
        .inform-smallCategory-btm {
          text-align: right;
          width: 150rpx;
          view:nth-child(1) {
            color: #333;
            text:nth-child(1) {
              font-size: 20rpx;
            }
            text:nth-child(2) {
              font-size: 28rpx;
              font-weight: 400;
            }
          }
          view:nth-child(2),
          view:nth-child(3) {
            margin-top: 5rpx;
            font-size: 20rpx;
            color: #999;
          }
        }
      }
    }
    .orderDetails-inform-line {
      .inform-line {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 30rpx 30rpx 0 30rpx;
        color: #999;
        font-size: 24rpx;
        text {
          font-size: 20rpx;
        }
        .inform-line-num {
          font-size: 28rpx;
        }
      }
      .inform-total {
        font-weight: bold;
        padding: 30rpx;
        text-align: right;
        .total-num {
          color: $base-color2;
        }
        .total-text {
          font-size: 22rpx;
        }
      }
    }
  }
  .orderDetails-goodsInform {
    background: #fff;
    margin-top: 20rpx;
    padding-bottom: 30rpx;
    view {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 30rpx 30rpx 0 30rpx;
    }
  }
  .orderDetails-btn {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    background: #fff;
    border-top: 1rpx solid #f2f3f8;
    padding: 30rpx;
    position: fixed;
    bottom: 0;
    width: 720rpx;
    text {
      display: block;
      font-size: 28rpx;
      border-radius: 40rpx;
      width: 220rpx;
      height: 80rpx;
      line-height: 80rpx;
      text-align: center;
      margin-right: 30rpx;
    }
    .btn-cancel {
      color: #09d5ee;
      border: 1px solid #09d5ee;
    }
    .btn-pay {
      color: #fff;
      background: linear-gradient(10deg, #09d5ee 0%, #6eefff 100%);
    }
  }
}
</style>
