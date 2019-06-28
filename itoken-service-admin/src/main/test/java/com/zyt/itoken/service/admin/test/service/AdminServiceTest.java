package com.zyt.itoken.service.admin.test.service;

import com.zyt.itoken.service.admin.ServiceAdminApplication;
import com.zyt.itoken.service.admin.domain.TbSysUser;
import com.zyt.itoken.service.admin.service.AdminService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith (SpringRunner.class)
@SpringBootTest(classes = ServiceAdminApplication.class)
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void register() {
        TbSysUser tbSysUser = new TbSysUser ();
        tbSysUser.setUserCode (UUID.randomUUID ().toString ());
        tbSysUser.setLoginCode ("admin@qq.com");
        tbSysUser.setUserName ("zyt");
        tbSysUser.setPassword ("123456");
        tbSysUser.setUserType ("0");
        tbSysUser.setMgrType ("1");
        tbSysUser.setStatus ("0");
        tbSysUser.setCreateDate (new Date ());
        tbSysUser.setCreateBy (tbSysUser.getUserCode ());
        tbSysUser.setUpdateDate (new Date ());
        tbSysUser.setUpdateBy (tbSysUser.getUserCode ());
        tbSysUser.setCorpCode ("0");
        tbSysUser.setCorpName ("iToken");
        adminService.register (tbSysUser);

    }

    @Test
    public void login() {
        TbSysUser tbSysUser = adminService.login ("admin@qq.com", "123456");
        Assert.assertNotNull (tbSysUser);
    }
}
