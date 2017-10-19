package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.front.UserAssetService;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Autowired
    private UserAssetService userAssetService;

    private Logger logger = LogManager.getLogger(IndexController.class);
    /**
     * @Author sinnamm
     * @Describtion: 跳转到是首页，需要给首页传递的数据有
     * 1、用户信息，用于显示用户资料
     * 2、每日情缘
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

        int userId = user.getId();
        UserAsset userAsset = userAssetService.selectByPrimaryKey(userId);
        logger.info("userAsset:"+userAsset);
        //剩余时间计算
        int vipDate=0, starDate=0,asset=0;
        if (userAsset!=null){
            if (userAsset.getVipDeadline()!=null){
                vipDate = LoverUtil.getTime(userAsset.getVipDeadline());
            }
            if (userAsset.getStarDeadline()!=null){
                starDate = LoverUtil.getTime(userAsset.getStarDeadline());
            }
            if (userAsset.getAsset()==null){
                userAsset.setAsset(asset);
            }
        }
        logger.info("vipDate="+vipDate+"....starDate="+starDate+".....asset="+userAsset.getAsset());


        model.addAttribute("vipDate",vipDate);
        model.addAttribute("starDate",starDate);
        model.addAttribute("userAsset",userAsset);
        return "front/index";
    }


}
