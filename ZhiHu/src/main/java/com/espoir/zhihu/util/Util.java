package com.espoir.zhihu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Util {

	public static   BlockingQueue<String> topicID = new  ArrayBlockingQueue<String>(50);
	
	public static BlockingQueue<String> SecondtopicID = new ArrayBlockingQueue<String>(50000);
	
	public static AtomicInteger UserCount=new AtomicInteger(0);
	//public static HashMap<String,Integer> map=new HashMap<String,Integer>();
	
	public static  Map<String,Integer> map = Collections.synchronizedMap(new HashMap<String,Integer>());
	
	public static BlockingQueue<String> userurl = new ArrayBlockingQueue<String>(100000);

	public static Connection conn= getConnection();
    public static Connection getConnection() {
		    String url = "jdbc:mydql://59.110.243.60/zhihu?characterEncoding=utf8";
		    String username = "root";
		    String password = "admin";

		    Connection conn = null;
		    try {
				Class.forName("com.mysql.jdbc.Driver"); //
				conn = (Connection) DriverManager.getConnection(url, username, password);
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return conn;
	}
    
	public static String[] cookies=new String[22];
	static{

		cookies[0]="Mi4wQUZEQ243R3JrUXNBQUlKU1lBQ1hDeGNBQUFCaEFsVk5lOGNWV1FEUkxIMkZrdG5mLWp4eTBTbEhaejdldU9rVm5B|1492007551|10c21c3b1bab28a836483200300f73d8e38f6be2";

	}

    
    public static String getCookie() {
        int index=new Random().nextInt(21);
        return cookies[index];
    }
	
	
}