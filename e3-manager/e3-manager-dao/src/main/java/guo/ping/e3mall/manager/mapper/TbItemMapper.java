package guo.ping.e3mall.manager.mapper;

import guo.ping.e3mall.pojo.TbItem;

import java.util.List;

public interface TbItemMapper {

    TbItem selectByPrimaryKey(Long id);

    List<TbItem> getItemList();

    void insert(TbItem item);
}