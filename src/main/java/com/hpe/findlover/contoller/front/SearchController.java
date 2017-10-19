package com.hpe.findlover.contoller.front;

import  com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Dict;
import com.hpe.findlover.model.Search;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserPick;
import com.hpe.findlover.service.front.DictService;
import com.hpe.findlover.service.front.UserPickService;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
@Controller
@RequestMapping("search")
public class SearchController {
    private Logger logger = LogManager.getLogger(SearchController.class);

    @Autowired
    private DictService dictService;
    @Autowired
    private UserPickService userPickService;

    /**
    * @Author sinnamm
    * @Describtion: 跳转到search页面，要查询数据库传递的页面信息有
     * 1、数据字典表：职业、婚史、学历，住房条件、星座、生肖、信仰
     * 2、搜索位用户信息：默认是根据用户的择偶条件进行初步查询
     * 3、广告位用户：随机从VIP用户中选择符合其性取向的用户（四位）显示在页面
    * @Date Create in  9:03 2017/10/19
    **/
    @GetMapping
    public String search(Model model, HttpServletRequest request) throws Exception {
        /*1、搜索列表项*/
        //职业
        List<Dict> jobList = dictService.selectDictByType("job");
        //婚史
        List<Dict> marryStatusList = dictService.selectDictByType("marry_status");
        //学历
        List<Dict> educationList = dictService.selectDictByType("education");
        //住房条件
        List<Dict> liveConditionList = dictService.selectDictByType("live_condition");
        //星座
        List<Dict> zodiacList = dictService.selectDictByType("zodiac");
        //生肖
        List<Dict> animalList = dictService.selectDictByType("animal");
        //信仰
        List<Dict> religionList = dictService.selectDictByType("religion");
        model.addAttribute("jobList",jobList);
        model.addAttribute("marryStatusList",marryStatusList);
        model.addAttribute("educationList",educationList);
        model.addAttribute("liveConditionList",liveConditionList);
        model.addAttribute("zodiacList",zodiacList);
        model.addAttribute("animalList",animalList);
        model.addAttribute("religionList",religionList);
        /*2、搜索位用户信息*/
        UserBasic user = SessionUtils.getSessionAttr(request,"user",UserBasic.class);

        /*3、广告位VIP用户信息*/

        return "front/search";
    }

    @GetMapping("/initUserPick")
    @ResponseBody
    public UserPick initUserPick(HttpServletRequest request) throws Exception {
        UserBasic user = SessionUtils.getSessionAttr(request,"user",UserBasic.class);
        UserPick userPick = null;
        if (user!=null) {
            userPick = userPickService.selectByPrimaryKey(user.getId());
        }
        return userPick;
    }

    @PostMapping("/getSearchUser")
    @ResponseBody
    public String getSearchUser(Search search){

        System.out.println("search......"+search.toString());

        //分页插件，使用分页查询,传入页码和每页数量
       // PageHelper.startPage(pageNum,5);
        //startPage后面紧跟的查询就是分页查询
        //List<UserBasic> employees = u.getAll();
        //用PageInfo对结果进行包装,只需要将pageinfo交给页面
        // pageInfo里面封装了详细的分页信息。第二个参数是连续显示的页数
       // PageInfo page = new PageInfo(employees,5);
        return "success";
    }
}
