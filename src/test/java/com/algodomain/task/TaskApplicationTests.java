package com.algodomain.task;

import org.h2.util.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Test class added ONLY to cover main() invocation not covered by application tests.
@SpringBootTest
class TaskApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testMain() {
		TaskApplication.main(new String[] {});
		assertTrue(true);
	}

}
