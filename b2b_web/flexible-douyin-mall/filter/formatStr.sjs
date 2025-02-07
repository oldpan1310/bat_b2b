// 格式化时间
function formatDate(ts, option) {
  var date = getDate(ts);
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  var hour = date.getHours();
  var minute = date.getMinutes();
  var second = date.getSeconds();

  // 获取 年月日
  if (option == 'YY-MM-DD') return ([year, month, day].map(formatNumber).join('-'));

  // 获取 月日
  if (option == 'MM月DD日') return ([month].map(formatNumber) + '月' + [day].map(formatNumber) + '日');
  
  // 获取 时分秒
  if (option == 'hh:mm:ss') return ([hour, minute, second].map(formatNumber).join(':'));
  
  // 获取 年月日 时分秒
  return ([year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':'));
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

module.exports = {
  formatDate: formatDate
}