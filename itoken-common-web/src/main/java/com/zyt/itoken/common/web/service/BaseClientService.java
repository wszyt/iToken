package com.zyt.itoken.common.web.service;

import com.zyt.itoken.common.hystrix.Fallback;

public interface BaseClientService {
    default String page(int pageNum, int pageSize, String domainJson) {
        return Fallback.badGateway ();
    }
}
