package io.appsforfree.oauth2_jersey.business;

import java.security.SecureRandom;
import java.util.Random;

public class StringUtils 
{
	private static final String upperLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String lowerLetters = upperLetters.toLowerCase();
	private static final String digits = "0123456789";
	private static final String alphanum = upperLetters + lowerLetters + digits;
	private static final Random random = new SecureRandom();
	
	public static String random(int len)
	{
		StringBuilder sb = new StringBuilder(len);
		for(int i = 0; i < len; i++)
			sb.append(alphanum.charAt(random.nextInt(alphanum.length())));
		return sb.toString();
	}
}
