package com.example.common.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 一些常用的单例对象
 *
 * @author Leo
 * @date 2020.02.28
 */
public class Holder {

	/**
	 * RANDOM
	 */
	public final static Random RANDOM = new Random();

	/**
	 * SECURE_RANDOM
	 */
	public final static SecureRandom SECURE_RANDOM = new SecureRandom();
}
