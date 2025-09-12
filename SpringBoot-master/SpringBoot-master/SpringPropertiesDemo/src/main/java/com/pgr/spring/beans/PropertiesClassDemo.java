package com.pgr.spring.beans;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesClassDemo {

	public static void main(String[] args) throws IOException {
		Properties pro = new Properties();
		FileInputStream io = new FileInputStream("src/main/resources/config/application.properties");
			
		pro.load(io);
		System.out.println(pro.getProperty("user_name"));
	}
}
