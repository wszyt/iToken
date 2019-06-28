package com.zyt.itoken.service.admin.service.impl;

import com.zyt.itoken.common.domain.TbSysUser;
import com.zyt.itoken.common.mapper.TbSysUserMapper;
import com.zyt.itoken.common.service.Impl.BaseServiceImpl;
import com.zyt.itoken.service.admin.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl extends BaseServiceImpl<TbSysUser, TbSysUserMapper> implements AdminService<TbSysUser> {

}
