package guo.ping.e3mall.manager.service;

import guo.ping.e3mall.common.pojo.EasyUIDataGridResult;
import guo.ping.e3mall.pojo.TbItem;

public interface TbItemService {
    TbItem getItemById(Long itemId);
    EasyUIDataGridResult getItemList(int page, int rows);
}