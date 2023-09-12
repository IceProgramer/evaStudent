package com.itmo.evastudent.constant;


import com.itmo.evastudent.model.entity.Student;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/9/6 15:35
 */

public class AuthUserContext {

	private static final ConcurrentHashMap<Long, Student> map = new ConcurrentHashMap<>();

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void set(Long userId, Student student, long timeout, TimeUnit timeUnit) {
		map.put(userId, student);
		scheduler.schedule(() -> map.remove(userId), timeout, timeUnit);
	}

	public static Student get(Long userId) {
		return map.get(userId);
	}

	public static void clear() {
		map.clear();
	}

}
