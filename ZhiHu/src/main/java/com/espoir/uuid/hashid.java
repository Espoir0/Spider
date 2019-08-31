package com.espoir.uuid;

import java.util.UUID;

public class hashid { 
    public hashid() {}

    public static String getUUID(){ 
        String uuid = UUID.randomUUID().toString();

        return uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24);
    } 

    public static String[] getUUID(int number){ 
        if(number < 1){ 
            return null; 
        } 
        String[] ss = new String[number]; 
        for(int i=0;i<number;i++){ 
            ss[i] = getUUID(); 
        } 
        return ss; 
    } 
    public static void main(String[] args){ 
        String[] ss = getUUID(10); 
        for(int i=0;i<ss.length;i++){ 
            System.out.println(ss[i]); 
        } 
    } 
}   
