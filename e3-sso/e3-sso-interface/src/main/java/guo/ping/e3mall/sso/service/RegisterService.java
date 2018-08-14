package guo.ping.e3mall.sso.service;

import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.pojo.TbUser;

public interface RegisterService {
    E3Result checkData(String param, Integer type);
    E3Result register(TbUser tbUser);
}
