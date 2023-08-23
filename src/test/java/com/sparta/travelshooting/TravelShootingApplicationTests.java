package com.sparta.travelshooting;

import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class TravelShootingApplicationTests {
	EntityManager em;

	@Test
	void contextLoads() {
	}
}
