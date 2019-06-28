package com.zyt.itoken.web.admin.service.fallback;

import com.zyt.itoken.common.hystrix.Fallback;
import com.zyt.itoken.web.admin.service.AdminService;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceFallback implements AdminService {
    @Override
    public String page(int pageNum, int pageSize, String tbSysUserJson) {
        return Fallback.badGateway ();
    }

    @Override
    public String get(String userCode) {
        return null;
    }

    @Override
    public String save(String tbSysUserJson, String optsBy) {
        return Fallback.badGateway ();
    }
}
