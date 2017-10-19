package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.Dict;
import com.hpe.findlover.service.front.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private DictService dictService;
    /**
    * @Author sinnamm
    * @Describtion: 跳转到search页面，要查询数据库传递的页面信息有
     * 1、数据字典表：职业、婚史、学历，住房条件、星座、生肖、民族，信仰
     * 2、搜索位用户信息：默认是根据用户的择偶条件进行初步查询
     * 3、广告位用户：随机从VIP用户中选择符合其性取向的用户（四位）显示在页面
    * @Date Create in  9:03 2017/10/19
    **/
    @GetMapping
    public String search() {
        //职业

        return "front/search";
    }
}
