package guo.ping.e3mall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import guo.ping.e3mall.manager.mapper.TbItemMapper;
import guo.ping.e3mall.manager.service.TbItemService;
import guo.ping.e3mall.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TbItemServiceImpl implements TbItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TbItem getItemById(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }
}
