package com.stellariver.meeting.adapter.tunnel;

import com.stellariver.milky.common.tool.common.UK;
import com.stellariver.milky.domain.support.dependency.ConcurrentOperate;

public class ConcurrentOperateImpl extends ConcurrentOperate {

    @Override
    protected boolean tryLock(UK nameSpace, String lockKey, String encryptionKey, int milsToExpire) {
        return false;
    }

    @Override
    protected boolean unlock(UK nameSpace, String lockKey, String encryptionKey) {
        return false;
    }

}
