package com.hpe.findlover.service.front;

import com.hpe.findlover.model.Dict;
import com.hpe.findlover.model.Search;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserLabel;
import com.hpe.findlover.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface UserService extends BaseService<UserBasic> {
	UserBasic selectByEmail(String email);

	List<UserBasic> selectStarUser(String date,String sexual,String workspace);

	List<UserBasic> selectUserBySearch(Search search);

	List<UserBasic> selectUSerByLabel(UserLabel userLabel);

	List<UserBasic> selectAllUser();

}
