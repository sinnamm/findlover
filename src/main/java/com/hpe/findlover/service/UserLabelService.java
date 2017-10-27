package com.hpe.findlover.service;

import com.hpe.findlover.model.UserLabel;
import com.hpe.findlover.service.BaseService;

/**
 * @author sinnamm
 * @Date Create in  2017/10/23.
 */

public interface UserLabelService extends BaseService<UserLabel> {
	void reloadLabel(String meaning, int userId, boolean condition);
}
