package guo.ping.e3mall.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import guo.ping.e3mall.content.service.ContentService;
import guo.ping.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Value("${CONTENT_BANNER_ID}")
    private Long CONTENT_BANNER_ID;

    @Reference
    private ContentService contentService;

    @RequestMapping({"/index", "/", "index.html"})
    public String showIndex(Model model) {
        List<TbContent> contentList = contentService.getContentList(CONTENT_BANNER_ID);
        model.addAttribute("ad1List", contentList);
        return "index";
    }

}
