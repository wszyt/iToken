package com.zyt.itoken.service.sso.service;

import com.zyt.itoken.common.domain.TbSysUser;

public interface LoginService {

    /**
     * 登录功能
     * @param loginCode
     * @param plantPassword
     * @return
     */
    public TbSysUser login (String loginCode, String plantPassword);
}
