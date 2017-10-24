package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.*;
import com.hpe.findlover.service.front.*;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.MD5Code;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 用户中心控制类
 * @author hgh
 */
@Controller
@RequestMapping("/usercenter")
public class UserCenterController {

    private Logger logger = LogManager.getLogger(UserCenterController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private UserLifeService userLifeService;
    @Autowired
    private UserStatusService userStatusService;
    @Autowired
    private UserPickService userPickService;
    @Autowired
    private UserAssetService userAssetService;

    /**
     * 跳转到用户中心界面
     */
    @GetMapping
    public String userCenter(Model model, HttpSession session){
        //跳转前查询用资产
        UserBasic user = (UserBasic) session.getAttribute("user");
        UserAsset userAsset = userAssetService.selectByPrimaryKey(user.getId());
        logger.info("userAsset:"+userAsset);
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
            logger.info("vipDate="+vipDate+"....starDate="+starDate+".....asset="+userAsset.getAsset());
        }
        logger.info("vipDate="+vipDate+"....starDate="+starDate+".....asset="+asset);
        model.addAttribute("vipDate",vipDate);
        model.addAttribute("starDate",starDate);
        model.addAttribute("asset",asset);
        return "front/user_center";
    }

    /**
     * 用户图片上传
     * @param request
     * @return 严格返回JSON格式
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(HttpServletRequest request){
        String realPath = "src/main/resources/static/upload/user/photo/";
        System.out.println("/////////////////////////////////////////////////"+realPath);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("photos");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            System.out.println(UUID.randomUUID()+"."
                    +file.getContentType().substring(file.getContentType().indexOf("/")+1));
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(realPath, UUID.randomUUID()+"."
                                    +file.getContentType().substring(file.getContentType().indexOf("/")+1))));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    e.printStackTrace();
                    return "上传失败 " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "上传失败 " + i
                        + " 因为是一个空文件。";
            }
        }
        return "[{\"result\":\"successful\"}]";
    }

    /**
     * 查询出用户的基本资料
     * @return 返回Json格式
     */
    @RequestMapping("userbasic")
    @ResponseBody
    public Object selectUserBasic(HttpSession session){
        UserBasic user = (UserBasic) session.getAttribute("user");
        UserBasic userBasic = null;
        try {
            userBasic = userService.selectByPrimaryKey(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userBasic;
    }

    /**
     * 用户充值
     * @return 返回Json格式
     */
    @PutMapping("pay")
    @ResponseBody
    public Object selectUserBasic(HttpSession session,Double cost){
        boolean result =false;
        if (cost!=null) {
            UserBasic user = (UserBasic) session.getAttribute("user");
            UserAsset userAsset = userAssetService.selectByPrimaryKey(user.getId());
            if (userAsset == null) {//用户资产为空情况
                UserAsset insertUserAsset = new UserAsset();
                insertUserAsset.setId(user.getId());
                insertUserAsset.setCost(cost);
                insertUserAsset.setAsset(cost.intValue());
                result = userAssetService.insert(insertUserAsset);
            } else {//用户资产为非空情况
                userAsset.setCost(userAsset.getCost()+cost);
                userAsset.setAsset(userAsset.getAsset()+cost.intValue());
                result = userAssetService.updateByPrimaryKey(userAsset);
            }
        }
        return result;
    }

    /**
     * 查询出用户的详细资料
     * @return 返回Json格式
     */
    @RequestMapping("userdetail")
    @ResponseBody
    public Object selectUserDetail(HttpSession session){
        UserBasic user = (UserBasic) session.getAttribute("user");
        UserDetail userDetail = null;
        try {
            userDetail = userDetailService.selectByPrimaryKey(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetail;
    }

    /**
     * 查询出用户的工作生活
     * @return 返回Json格式
     */
    @RequestMapping("userlife")
    @ResponseBody
    public Object selectUserLife(HttpSession session){
        UserBasic user = (UserBasic) session.getAttribute("user");
        UserLife userLife = null;
        try {
            userLife = userLifeService.selectByPrimaryKey(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userLife;
    }

    /**
     * 查询出用户的工作生活
     * @return 返回Json格式
     */
    @RequestMapping("userstatus")
    @ResponseBody
    public Object selectUserStatus(HttpSession session){
        UserBasic user = (UserBasic) session.getAttribute("user");
        UserStatus userStatus = null;
        try {
            userStatus = userStatusService.selectByPrimaryKey(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userStatus;
    }
    /**
     * 查询出用户的择偶条件
     * @return 返回Json格式
     */
    @RequestMapping("userpick")
    @ResponseBody
    public Object selectUserPick(HttpSession session){
        UserBasic user = (UserBasic) session.getAttribute("user");
        UserPick userPick = null;
        if(user!=null) {
            userPick = userPickService.selectByPrimaryKey(user.getId());
        }
        return userPick;
    }

    /**
     * 用户基本信息维护
     *
     * @return 返回Json格式
     */
    @PutMapping("basic")
    @ResponseBody
    public Object updateUserBasic(UserBasic userBasic, String work_province, String work_city,HttpSession session) {
        boolean result = false;
        if(userBasic!=null) {
            if ( work_city != null) {
                userBasic.setWorkplace(work_province + "-" + work_city);
            }else {
                userBasic.setWorkplace(work_province);
            }
            if (userBasic.getPassword() != null) {
                userBasic.setPassword(new MD5Code().getMD5ofStr(userBasic.getPassword()));
            }
            logger.error(userBasic);
            result = userService.updateUserBasicAndUserLabel(userBasic);
            //result = userService.updateByPrimaryKeySelective(userBasic);
            //如果用户修改权限之后更新session中user值
            if (userBasic.getAuthority() != null) {
                UserBasic ubInSession = (UserBasic) session.getAttribute("user");
                ubInSession.setAuthority(userBasic.getAuthority());
                session.setAttribute("user", ubInSession);
            }
        }
        return result;
    }

    /**
     * 用户详情维护
     *
     * @return 返回Json格式
     */
    @PutMapping("detail")
    @ResponseBody
    public Object updateUserDetail(UserDetail userDetail, String graduation0, String graduation1,
                                  String birthplace_province,String birthplace_city) {
        boolean result = false;
        logger.warn("userDetail=   "+userDetail);
        logger.warn("graduation0=   "+graduation0);
        logger.warn("graduation1=   "+graduation1);
        logger.warn("birthplace_province=   "+birthplace_province);
        logger.warn("birthplace_city=   "+birthplace_city);
        if (graduation1 != null) {
            userDetail.setGraduation(graduation0 + "-" + graduation1);
        }else {
            userDetail.setGraduation(graduation0);
        }
        if (birthplace_city != null ) {
            userDetail.setBirthplace(birthplace_province + "-" + birthplace_city);
        }else {
            userDetail.setBirthplace(birthplace_province);
        }
        logger.error(userDetail);
        if(userDetailService.selectByPrimaryKey(userDetail)!=null){
            result = userDetailService.updateByPrimaryKey(userDetail);
        }else {
            result = userDetailService.insert(userDetail);
        }
        return result;
    }

    /**
     * 用户生活工作维护
     *
     * @return 返回Json格式
     */
    @PutMapping("life")
    @ResponseBody
    public Object updateUserLife(UserLife userLife) {
        boolean result = false;
        logger.error(userLife);
        if(userLife!=null) {
            result = userLifeService.insertUserLifeAndUserLabel(userLife);
        }
        return result;
    }
    /**
     * 用户婚姻观维护
     *
     * @return 返回Json格式
     */
    @PutMapping("status")
    @ResponseBody
    public Object updateUserLife(UserStatus userStatus) {
        boolean result = false;
        logger.error(userStatus);
        if(userStatusService.selectByPrimaryKey(userStatus)!=null){
            result = userStatusService.updateByPrimaryKey(userStatus);
        }else {
            result = userStatusService.insert(userStatus);
        }
        return result;
    }

    /**
     * 用户择偶条件维护
     *
     * @return 返回Json格式
     */
    @PutMapping("pick")
    @ResponseBody
    public Object updateUserLife(UserPick userPick,String workplace_province1,String workplace_city1
    ,String birthplace_province1,String birthplace_city1) {
        boolean result = false;
        if ( workplace_city1 != null) {
            userPick.setWorkplace(workplace_province1 + "-" + workplace_city1);
        }else {
            userPick.setWorkplace(workplace_province1);
        }
        if (birthplace_city1 != null) {
            userPick.setWorkplace(birthplace_province1 + "-" + birthplace_city1);
        }else {
            userPick.setWorkplace(birthplace_province1);
        }
        logger.error(userPick);
        if(userPickService.selectByPrimaryKey(userPick)!=null){
            result = userPickService.updateByPrimaryKey(userPick);
        }else {
            result = userPickService.insert(userPick);
        }
        return result;
    }


}
