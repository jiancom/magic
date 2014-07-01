package sys;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.resgain.base.abs.AbstractTestCase;
import com.resgain.base.entity.User;
import com.resgain.base.service.UserService;

public class ConfigServiceTest extends AbstractTestCase
{
	@Autowired UserService userService;

	@Test
	public void initData(){
		User user = userService.getUserByCode("admin");
		user.setPass("admin");
		userService.saveUser(user);
	}

}
