package guo.ping.e3mall.search.service;

import guo.ping.e3mall.common.pojo.SearchResult;

public interface SearchService {
    SearchResult search(String keyWord, int page, int rows) throws Exception;
}
