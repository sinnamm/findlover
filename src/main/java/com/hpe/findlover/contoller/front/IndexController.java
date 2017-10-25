package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.Dict;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserPick;
import com.hpe.findlover.service.front.DictService;
import com.hpe.findlover.service.front.UserAssetService;
import com.hpe.findlover.service.front.UserPickService;
import com.hpe.findlover.service.front.UserService;
import com.hpe.findlover.util.Constant;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.SessionUtils;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Autowired
    private UserAssetService userAssetService;
    @Autowired
    private UserPickService userPickService;
    @Autowired
    private DictService dictService;
    @Autowired
    private UserService userService;

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
    @GetMapping
    public String index(Model model, HttpServletRequest request) throws Exception {
        //1.用户信息，基本信息可以从session中直接获取，消费信息需要我们查询数据库
        UserBasic user = (UserBasic)request.getSession().getAttribute("user");
        user.setAge(LoverUtil.getAge(user.getBirthday()));
        logger.info("user:"+user);
        UserAsset userAsset = userAssetService.selectByPrimaryKey(user.getId());
        logger.info("userAsset:"+userAsset);
        //剩余时间计算
        int vipDate=0, starDate=0,asset=0;
        if (userAsset!=null){
            if (userAsset.getVipDeadline()!=null){
                vipDate = LoverUtil.getDiffOfHours(userAsset.getVipDeadline());
            }
            if (userAsset.getStarDeadline()!=null){
                starDate = LoverUtil.getDiffOfHours(userAsset.getStarDeadline());
            }
            if (userAsset.getAsset()!=null){
               asset= userAsset.getAsset();
            }
        }
        logger.info("vipDate="+vipDate+"....starDate="+starDate+".....asset="+asset);
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
        return "front/index";
    }

    @GetMapping("/initSearch")
    @ResponseBody
    public UserPick initSearch(HttpServletRequest request){
        UserBasic user = (UserBasic)request.getSession().getAttribute("user");
        UserPick userPick = userPickService.selectByPrimaryKey(user.getId());
        return userPick;
    }

    @PostMapping("/getSearchUser")
    @ResponseBody
    public List<UserBasic> getSearchUser(UserPick userPick,HttpServletRequest request){
        UserBasic user = SessionUtils.getSessionAttr(request,"user",UserBasic.class);
        return getDayLovers(userPick,user);
    }

    /**
     * 更具用户择偶条件选出16个每日情缘，
     * 如果择偶条件对应的人数不够，则从所有性取向对应用户随机选取凑够16个
     * @param userPick
     * @return
     */
    public List<UserBasic> getDayLovers(UserPick userPick,UserBasic user){
        userPick.setId(user.getId());
        logger.info("userPick..."+userPick);
        List<UserBasic> userBasicList = userService.selectUserByUserPick(userPick);
        LoverUtil.formatUserInfo(userBasicList);

        if (userBasicList.size()> Constant.INDEX_SHOW_USER_NUMBER){
            logger.info("根据择偶条件选出来的用户大于16，需要随机选取");
            List<UserBasic> userBasics = LoverUtil.getRandomUser(userBasicList,Constant.INDEX_SHOW_USER_NUMBER);
            return userBasics;
        }else {
            logger.info("根据择偶条件选出来的用户小于16，需要从数据库随机获取");
            int size = Constant.INDEX_SHOW_USER_NUMBER-userBasicList.size();
            List<UserBasic> userBasics = userService.
                    selectUserBySexualAndWorkProvince(user.getId(),user.getSexual(),user.getWorkplace().substring(0,2));
            if (userBasics==null||userBasics.size()<size){
                logger.info("根据性取向和工作最地选出来的用户小于16，只选取性取向对应的用户");
                userBasics =userService.
                        selectUserBySexualAndWorkProvince(user.getId(),user.getSexual(),null);
            }
            LoverUtil.formatUserInfo(userBasics);
            List<UserBasic> allUsers = LoverUtil.getRandomUser(userBasics,size);
            for (int i=0;i<allUsers.size();i++){
                userBasicList.add(allUsers.get(i));
            }
            userBasicList.forEach(logger::info);
            return userBasicList;
        }
    }

}
