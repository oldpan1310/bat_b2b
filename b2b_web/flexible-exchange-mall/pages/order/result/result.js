// pages/order/result/result.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /* 页面跳转 */
  // 返回首页
  goHome() {
    wx.switchTab({
      url: '/pages/index/index',
    })
  },

  // 查看订单
  goPath(e) {
    // 获取跳转path
    let path = e.currentTarget.dataset.path;
    // 订阅消息
    // wx.requestSubscribeMessage({
    //   tmplIds: ['w3TDCXVHgTgqV5RDgjB_aSxx-xWsBI4upr84AiJTbXE', 'MSpHXM5V9m7aKbuZuDT7kvDWTzfNFAR0LU7S8_A884o'],
    //   success(res) {
    //     if (res.errMsg === 'requestSubscribeMessage:ok') {
    wx.redirectTo({
      url: '/pages/webview/webview?enterFlag=' + path + '&sid=2',
    })
    //     }
    //   }
    // })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})