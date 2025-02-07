package com.bat.flexible.manager.exchange.executor;

import com.bat.flexible.dao.exchange.ExchangeRefundOrderDOMapper;
import com.bat.flexible.dao.exchange.dataobject.ExchangeRefundOrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRefundOrderQryExe {

    @Autowired
    private ExchangeRefundOrderDOMapper exchangeRefundOrderDOMapper;

    public ExchangeRefundOrderDO getByRefundNo(String refundNo) {
        return exchangeRefundOrderDOMapper.getByRefundNo(refundNo);
    }
}
