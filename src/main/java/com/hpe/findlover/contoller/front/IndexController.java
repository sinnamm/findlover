package com.hpe.findlover.contoller.front;

import com.github.pagehelper.PageHelper;
import com.hpe.findlover.model.*;
import com.hpe.findlover.service.*;
import com.hpe.findlover.util.Constant;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
@Controller
public class IndexController {

    @Autowired
    private UserAssetService userAssetService;
    @Autowired
    private UserPickService userPickService;
    @Autowired
    private DictService dictService;
    @Autowired
    private UserService userService;
    @Autowired
    private VisitTraceService visitTraceService;
    @Autowired
    private SuccessStoryService successStoryService;


    private Logger logger = LogManager.getLogger(IndexController.class);
    /**
     * @Author sinnamm
     * @Describtion: 跳转到是首页，需要给首页传递的数据有
     * 1、用户信息，用于显示用户资料
     * 2、用户择偶条件，用于推荐每日情缘
     * 3、光荣脱单榜
     * 4、成功故事
     * 5、谁看过我
     * @Date Create in  15:35 2017/10/17
     **/
    @GetMapping(value = {"","index"})
    public String index(Model model, HttpServletRequest request) throws Exception {
        logger.debug("User Subject: "+ SecurityUtils.getSubject().getPrincipal().toString());

        //获取光荣脱单榜
        Map<UserBasic, Integer> vipNotSingles = successStoryService.selectVipNotSingle();
        //1.用户信息，基本信息可以从session中直接获取，消费信息需要我们查询数据库
        UserBasic user = (UserBasic)request.getSession().getAttribute("user");
        user.setAge(LoverUtil.getAge(user.getBirthday()));
        logger.debug("user:"+user);
        UserAsset userAsset = userAssetService.selectByPrimaryKey(user.getId());
        logger.debug("userAsset:"+userAsset);
        //剩余时间计算
        int vipDate=0, starDate=0,asset=0;
        if (userAsset!=null){
            if (userAsset.getVipDeadline()!=null){
                vipDate = LoverUtil.getDiffOfDays(userAsset.getVipDeadline());
            }
            if (userAsset.getStarDeadline()!=null){
                starDate = LoverUtil.getDiffOfDays(userAsset.getStarDeadline());
            }
            if (userAsset.getAsset()!=null){
               asset= userAsset.getAsset();
            }
        }
        logger.debug("vipDate="+vipDate+"....starDate="+starDate+".....asset="+asset);
        model.addAttribute("vipDate",vipDate);
        model.addAttribute("starDate",starDate);
        model.addAttribute("asset",asset);
        //2、用户择偶条件，用于推荐每日情缘
        UserPick userPick = userPickService.selectByPrimaryKey(user.getId());
        List<UserBasic> userBasicList = getDayLovers(userPick,user);
        model.addAttribute("dayLover",userBasicList);
        //职业
        List<Dict> jobList = dictService.selectDictByType("job");
        model.addAttribute("userPick",userPick);
        model.addAttribute("jobList",jobList);
        model.addAttribute("vipNotSingles",vipNotSingles);
        logger.debug(vipNotSingles);
        //  * 5、谁看过我
        PageHelper.startPage(1,5,"visit_time desc");
        List<VisitTrace> visitTraces = visitTraceService.selectIndexVisitTracer(user.getId());
        model.addAttribute("visitTraces",visitTraces);
        // * 4、成功故事
        PageHelper.startPage(1,5,"success_time desc");
        List<SuccessStory> successStories = successStoryService.selectAll();
        for (SuccessStory success : successStories) {
            success.setUserLeft(userService.selectByPrimaryKey(success.getLeftUser()));
            success.setUserRight(userService.selectByPrimaryKey(success.getRightUser()));
        }
        model.addAttribute("successStories",successStories);
        return "front/index";
    }

    @GetMapping("index/initSearch")
    @ResponseBody
    public UserPick initSearch(HttpServletRequest request){
        UserBasic user = (UserBasic)request.getSession().getAttribute("user");
        return userPickService.selectByPrimaryKey(user.getId());
    }

    @PostMapping("index/getSearchUser")
    @ResponseBody
    public List<UserBasic> getSearchUser(UserPick userPick,HttpServletRequest request){
        UserBasic user = SessionUtils.getSessionAttr("user",UserBasic.class);
        return getDayLovers(userPick,user);
    }

    /**
     * 更具用户择偶条件选出16个每日情缘，
     * 如果择偶条件对应的人数不够，则从所有性取向对应用户随机选取凑够16个
     * @param userPick
     * @return
     */
    private List<UserBasic> getDayLovers(UserPick userPick, UserBasic user){
        userPick.setId(user.getId());
        logger.debug("userPick..."+userPick);
        List<UserBasic> userBasicList = userService.selectUserByUserPick(userPick);
        LoverUtil.formatUserInfo(userBasicList);

        if (userBasicList.size()> Constant.INDEX_SHOW_USER_NUMBER){
            logger.debug("根据择偶条件选出来的用户大于16，需要随机选取");
            return LoverUtil.getRandomUser(userBasicList,Constant.INDEX_SHOW_USER_NUMBER);
        }else {
            logger.debug("根据择偶条件选出来的用户小于16，需要从数据库随机获取");
            int size = Constant.INDEX_SHOW_USER_NUMBER-userBasicList.size();
            List<UserBasic> userBasics = userService.
                    selectUserBySexualAndWorkProvince(user.getId(),userPick.getSex(),user.getWorkplace().substring(0,2));
            if (userBasics==null||userBasics.size()<size){
                logger.debug("根据性取向和工作最地选出来的用户小于16，只选取性取向对应的用户");
                userBasics =userService.
                        selectUserBySexualAndWorkProvince(user.getId(),userPick.getSex(),null);
            }
            LoverUtil.formatUserInfo(userBasics);
            List<UserBasic> allUsers = LoverUtil.getRandomUser(userBasics,size);
            userBasicList.addAll(allUsers);
            userBasicList.forEach(logger::debug);
            return userBasicList;
        }
    }

}
