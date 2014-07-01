package sys;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.resgain.base.abs.AbstractTestCase;
import com.resgain.base.service.InitService;

public class InitServiceTest extends AbstractTestCase
{
	@Autowired InitService initService;

	@Test
	public void initData(){
		initService.initData();
	}

}
