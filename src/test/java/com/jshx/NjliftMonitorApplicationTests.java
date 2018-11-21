package com.jshx;

import com.jshx.domain.MyTest;
import com.jshx.domain.TestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NjliftMonitorApplicationTests {

	@Autowired
	private TestRepository testRepository;

	@Autowired
	private  MyTest myTest;

	@Test
	public void contextLoads() {
        List<com.jshx.domain.Test> abc = testRepository.findByName("abc");

		System.out.println(myTest.getMyName());
		myTest.setMyName("my name is yaoyujing");
		System.out.println(myTest.getMyName());
	}

}
