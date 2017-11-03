package com.hpe.findlover.contoller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.*;
import com.hpe.findlover.service.*;
import com.hpe.findlover.util.Constant;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.SessionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

/***
 * @author  gss
 */
@Controller
@RequestMapping("other_says")
public class OtherSayController {
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserPickService userPickService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageLikeService messageLikeService;
	@Autowired
	private MessageReplyService messageReplyService;
	@Autowired
	private FollowService followService;
	@Autowired
	private EssayService essayService;
	@Autowired
	private UploadService uploadService;
	@Autowired
	private WriterEssayLikeService writerEssayLikeService;

	private Logger logger = LogManager.getLogger(this.getClass());
	private final MessageService messageService;

	@Autowired
	public OtherSayController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping
	public String otherSays(HttpServletRequest request,Model model) {
		UserBasic user = SessionUtils.getSessionAttr("user",UserBasic.class);
		UserPick userPick = userPickService.selectByPrimaryKey(user.getId());
		List<UserBasic> userBasicStarPick = LoverUtil.getRandomStarUser(userPick,Constant.SEARCH_SHOW_STAR_USER_NUMBER,userService);
		userBasicStarPick.forEach(logger::info);
		model.addAttribute("userBasicStarPick",userBasicStarPick);
		return "front/other_says";
	}

	@PostMapping("/message")
	@ResponseBody
	public boolean insert(Message message, HttpServletRequest request) throws Exception {
		UserBasic user = SessionUtils.getSessionAttr("user", UserBasic.class);
		assert user != null;
		message.setUserId(user.getId());
		message.setLikeCount(0);
		message.setReplyCount(0);
		message.setPubTime(new Date());
		logger.info("进行个人动态增加："+message);
		return messageService.insert(message);
	}

	@GetMapping("/message")
	@ResponseBody
	public String message(@Param("pageNum")Integer pageNum,@Param("type")String type,HttpServletRequest request) throws JsonProcessingException {
		logger.info("pageNum=="+pageNum+"......type=="+type);
		Integer userId = SessionUtils.getSessionAttr("user",UserBasic.class).getId();
		if(Constant.HOT.equals(type)){
			PageHelper.startPage(pageNum,4,"reply_count desc,like_count desc");
		}else if(Constant.NEW.equals(type)){
			PageHelper.startPage(pageNum,4,"pub_time desc");
		}
		List<Message> list = messageService.selectList();
		formatMessage(list,userId);
		list.forEach(logger::info);
		PageInfo pageInfo = new PageInfo(list);
		return JSON.toJSONStringWithDateFormat(pageInfo,"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
	}

	@GetMapping("/followMessage")
	@ResponseBody
	public String followMessage(@Param("pageNum")Integer pageNum,HttpServletRequest request) throws JsonProcessingException {
		logger.info("pageNum=="+pageNum);
		UserBasic userBasic = SessionUtils.getSessionAttr("user",UserBasic.class);
		PageHelper.startPage(pageNum,4,"pub_time desc");
		List<Message> list = messageService.selectMessageByFollow(userBasic.getId());
		formatMessage(list,userBasic.getId());
		list.forEach(logger::info);
		PageInfo pageInfo = new PageInfo(list);
		return JSON.toJSONStringWithDateFormat(pageInfo,"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
	}


	@GetMapping("/likeMessage/{messageId}")
	@ResponseBody
	public String likeMessage(@PathVariable("messageId") Integer messageId,HttpServletRequest request){
		Integer userId = SessionUtils.getSessionAttr("user",UserBasic.class).getId();

		MessageLike old = messageLikeService.selectOne(new MessageLike(messageId,userId,null));
		if (old!=null){
			return "error";
		}
		MessageLike messageLike = new MessageLike(messageId,userId,new Date());
		boolean result = messageLikeService.insertSelective(messageLike);
		if (result) {
			return "success";
		}else {
			return "error";
		}
	}

	@GetMapping("/replyMessage")
	@ResponseBody
	public String replyMessage(@Param("reply")String reply,@Param("messageId")Integer messageId, HttpServletRequest request){
		logger.info("reply=="+reply+"messageId=="+messageId);
		Integer userId = SessionUtils.getSessionAttr("user",UserBasic.class).getId();
		MessageReply messageReply = new MessageReply(messageId,userId,reply,new Date());
		boolean result = messageReplyService.insertSelective(messageReply);
		if (result) {
			return "success";
		}else {
			return "error";
		}
	}


    /**
     * 加载文章详情内容
     * @param id 文章id
     * @param model
     * @return
     */
	@GetMapping("essaydetail/{id}")
	public String essayDetailUI(@PathVariable Integer id,Model model,HttpServletRequest request){
        Essay essayObj = essayService.selectEssayAndWriter(id);
        essayObj.setVisitCount(essayObj.getVisitCount()+1);
        essayService.updateByPrimaryKeySelective(essayObj);
        UserBasic user = SessionUtils.getSessionAttr( "user", UserBasic.class);
        WriterEssayLike writerEssayLike = new WriterEssayLike();
        writerEssayLike.setUserId(user.getId());
        writerEssayLike.setEssayId(id);
        List<WriterEssayLike> select = writerEssayLikeService.select(writerEssayLike);
        if (select.size()==0){
            model.addAttribute("like",true);
        }else {
            model.addAttribute("like",false);
        }
        byte[] bytes = uploadService.downloadFile(essayObj.getFilename());
        try {
            String essay = new String(bytes, "utf-8");
            model.addAttribute("essay",essay);
            model.addAttribute("essayObj",essayObj);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //跳转错误页面
            return "front/other_says_detail";
        }
		return "front/other_says_detail";
	}

    /**
     * 分页加载文章内容
     * @param page
     * @return
     */
	@GetMapping("essays")
	@ResponseBody
	public PageInfo<Essay> essayList(Page<Essay> page) {
		logger.info("接收参数：pageNum=" + page.getPageNum() + ",pageSize=" + page.getPageSize());
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Essay> essays = essayService.selectAllByPutaway();
		PageInfo<Essay> pageInfo = new PageInfo<>(essays);
		logger.info(JSONObject.toJSON(pageInfo));
		return pageInfo;
	}

    /**
     * 加载5条访问量大的文章
     * @return
     */
	@GetMapping("hot_essays")
	@ResponseBody
	public Object hotEssayList() {
        List<Essay> essays = essayService.selectHotEssay();
		return essays;
	}

    /**
     * 文章点赞
     * @return
     */
	@GetMapping("like/{essayId}")
    @ResponseBody
	public Object likeEssay(@PathVariable Integer essayId, HttpServletRequest request) {
        boolean result =false;
        UserBasic user = SessionUtils.getSessionAttr( "user", UserBasic.class);
        WriterEssayLike writerEssayLike = new WriterEssayLike();
        writerEssayLike.setUserId(user.getId());
        writerEssayLike.setEssayId(essayId);
        if(writerEssayLikeService.select(writerEssayLike).size()==0){
            writerEssayLike.setLikeTime(new Date());
            result = writerEssayLikeService.insert(writerEssayLike);
        }
		Essay essay = essayService.selectByPrimaryKey(essayId);
        Integer likeCount = essay.getLikeCount();
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount",likeCount);
        map.put("result",result);
        return map;
    }

	public void formatMessage(List<Message> list,Integer userId){
		for (int i=0;i<list.size();i++){
			List<MessageReply> messageReplies = list.get(i).getReplies();
			List<MessageLike> messageLikes = list.get(i).getLikes();
			boolean flag = false;
			for (int j=0;j<messageReplies.size();j++){
				messageReplies.get(j).setUserBasic(userService.selectByPrimaryKey(messageReplies.get(j).getUserId()));
			}
			for (int x=0;x<messageLikes.size();x++){
				if (userId.equals(messageLikes.get(x).getUserId())) {
					flag=true;
				}
			}
			list.get(i).setLike(flag);
		}
	}
}
