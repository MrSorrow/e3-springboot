package guo.ping.e3mall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.common.pojo.EasyUIDataGridResult;
import guo.ping.e3mall.common.utils.IDUtils;
import guo.ping.e3mall.manager.mapper.TbItemDescMapper;
import guo.ping.e3mall.manager.mapper.TbItemMapper;
import guo.ping.e3mall.manager.service.TbItemService;
import guo.ping.e3mall.pojo.TbItem;
import guo.ping.e3mall.pojo.TbItemDesc;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

import java.util.Date;
import java.util.List;

@Service
public class TbItemServiceImpl implements TbItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public TbItem getItemById(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        List<TbItem> list = tbItemMapper.getItemList();
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);

        //创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);

        return result;
    }

    /**
     * 后台管理添加商品至数据库
     * @param item 商品
     * @param desc 商品描述
     * @return
     */
    @Override
    public E3Result addItem(TbItem item, String desc) {
        // 1、生成商品id
        long itemId = IDUtils.genItemId();
        // 2、补全TbItem对象的属性
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        // 3、向商品表插入数据
        tbItemMapper.insert(item);
        // 4、创建一个TbItemDesc对象
        TbItemDesc itemDesc = new TbItemDesc();
        // 5、补全TbItemDesc的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // 6、向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);
        // 7、发送消息队列，通知新增商品id
        ActiveMQTopic itemAddTopic = new ActiveMQTopic("itemAddTopic");
        jmsMessagingTemplate.convertAndSend(itemAddTopic, item.getId());
        // 8、E3Result.ok()
        return E3Result.ok();
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        TbItemDesc itemDesc = tbItemDescMapper.selectItemDescByPrimaryKey(itemId);
        return itemDesc;
    }
}
