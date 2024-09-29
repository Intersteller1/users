package com.galvatron.users.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Shashank on 22/09/24.
 */
public class UniqueIdentifierGenerator {

    private static Random rnd = new Random();

    public static String getRandomNumber(Integer digitCount) {
        StringBuilder sb = new StringBuilder(digitCount);
        for(int i=0; i < digitCount; i++) {
            if(i==0){
                int firstDigit = 0;
                while(firstDigit == 0){
                    firstDigit = rnd.nextInt(10);
                }
                sb.append((char) ('0' + firstDigit));
            }
            else {
                sb.append((char) ('0' + rnd.nextInt(10)));
            }
        }
        return sb.toString();
    }

    public static String getRandomPassword(Integer digitLength){
        String password = RandomStringUtils.randomAlphanumeric(digitLength);;
        try{
            password = password.toUpperCase();
        }catch (Throwable th){
            th.printStackTrace();
        }

        return password;
    }
    
    public static String getRandomUUID(int length) {
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		uuidString = uuidString.replaceAll("-", "");
		
    	return uuidString.substring(0, length);
    	
    }
   
}
