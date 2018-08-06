package guo.ping.e3mall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import guo.ping.e3mall.common.pojo.EasyUIDataGridResult;
import guo.ping.e3mall.manager.service.TbItemService;
import guo.ping.e3mall.pojo.TbItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class TbItemController {

    @Reference
    private TbItemService tbItemService;

    @GetMapping("/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        return tbItemService.getItemById(itemId);
    }

    @GetMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = tbItemService.getItemList(page, rows);
        return result;
    }

}
