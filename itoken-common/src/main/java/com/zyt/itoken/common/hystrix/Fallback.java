package com.zyt.itoken.common.hystrix;

import com.google.common.collect.Lists;
import com.zyt.itoken.common.constants.HttpStatusContants;
import com.zyt.itoken.common.dto.BaseResult;
import com.zyt.itoken.common.utils.MapperUtils;

/**
 * 通用的熔断方法
 */
public class Fallback {

    /**
     * 502
     * @return
     */
    public static String badGateway() {
        BaseResult baseResult = BaseResult.notOk (Lists.newArrayList (new BaseResult.Error (
                String.valueOf (HttpStatusContants.BAD_GATEWAY.getStatus ()), HttpStatusContants.BAD_GATEWAY.getContent ())));
        try {
            return MapperUtils.obj2json (baseResult);
        } catch (Exception e) {
            e.printStackTrace ();
        }

        return null;
    }
}
