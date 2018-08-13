package guo.ping.e3mall.manager.service;

import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.common.pojo.EasyUIDataGridResult;
import guo.ping.e3mall.pojo.TbItem;
import guo.ping.e3mall.pojo.TbItemDesc;

public interface TbItemService {
    TbItem getItemById(Long itemId);
    EasyUIDataGridResult getItemList(int page, int rows);
    E3Result addItem(TbItem item, String desc);
    TbItemDesc getItemDescById(Long itemId);
}
