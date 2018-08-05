package guo.ping.e3mall.manager.mapper;

import guo.ping.e3mall.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Test
    public void selectTbItemByIDTest() {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(536563L);
        System.out.println(tbItem);
    }
}
