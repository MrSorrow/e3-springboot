package guo.ping.e3mall.manager.service;

import guo.ping.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getCatList(Long parentId);
}
