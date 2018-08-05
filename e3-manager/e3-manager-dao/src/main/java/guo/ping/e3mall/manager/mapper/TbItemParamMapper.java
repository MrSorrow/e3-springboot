package guo.ping.e3mall.manager.mapper;

import guo.ping.e3mall.pojo.TbItemParam;
import guo.ping.e3mall.pojo.TbItemParamAndName;

import java.util.List;

public interface TbItemParamMapper {

    List<TbItemParamAndName> getItemParamList();

    TbItemParam getItemParamByCid(Long cid);

    Integer insertItemParam(TbItemParam tbItemParam);
}
