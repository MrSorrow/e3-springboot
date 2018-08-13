package guo.ping.e3mall.search.mapper;

import guo.ping.e3mall.common.pojo.SearchItem;

import java.util.List;

public interface SearchItemMapper {
    List<SearchItem> getItemList();
	SearchItem getItemById(Long itemId);
}