package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.front.UserAssetService;
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
        user.setAge(getAge(user.getBirthday()));
        logger.info("user:"+user);

        int userId = user.getId();
        UserAsset userAsset = userAssetService.selectByPrimaryKey(userId);
        logger.info("userAsset:"+userAsset);
        //剩余时间计算
        int vipDate=0, starDate=0,asset=0;
        if (userAsset.getVipDeadline()!=null){
            vipDate = getTime(userAsset.getVipDeadline());
        }
        if (userAsset.getStarDealline()!=null){
            starDate = getTime(userAsset.getStarDealline());
        }
        if (userAsset.getAsset()==null){
            userAsset.setAsset(asset);
        }
        logger.info("vipDate="+vipDate+"....starDate="+starDate+".....asset="+userAsset.getAsset());


        model.addAttribute("vipDate",vipDate);
        model.addAttribute("starDate",starDate);
        model.addAttribute("userAsset",userAsset);
        return "front/index";
    }

    /**
    * @Author sinnamm
    * @Describtion: 计算时间差的方法，用于计算会员和星级的有效时间
    * @Date Create in  21:12 2017/10/17
    **/
    public int getTime(Date toDate) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");//如2016-08-10 20:40
        String fromDate = simpleFormat.format(System.currentTimeMillis());
        long from = simpleFormat.parse(fromDate).getTime();
        long to = toDate.getTime();
        int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
        return days;
    }

    /**
    * @Author sinnamm
    * @Describtion: 计算年龄的方法
    * @Date Create in  21:36 2017/10/17
    **/
    public int getAge(Date birthday){
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (birthday != null) {
            now.setTime(new Date());
            born.setTime(birthday);
            if (born.after(now)) {
                throw new IllegalArgumentException("年龄不能超过当前日期");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
            int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
            System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
            if (nowDayOfYear < bornDayOfYear) {
                age -= 1;
            }
        }
        return age;
    }

}
