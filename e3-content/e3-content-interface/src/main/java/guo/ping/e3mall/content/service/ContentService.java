package guo.ping.e3mall.content.service;

import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.common.pojo.EasyUIDataGridResult;
import guo.ping.e3mall.pojo.TbContent;

import java.util.List;

public interface ContentService {
    E3Result addContent(TbContent content);
    EasyUIDataGridResult getContentListByCategoryId(Long categoryId, int page, int rows);
    List<TbContent> getContentList(Long cid);
}
