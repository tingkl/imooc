package com.mix.miaosha.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mix.miaosha.domain.entity.MiaoshaUser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserUtil {

	private static List<MiaoshaUser> fakeUsers(int count) {
		List<MiaoshaUser> users = new ArrayList<MiaoshaUser>(count);
		//生成用户
		for(int i=0;i<count;i++) {
			MiaoshaUser user = new MiaoshaUser();
			user.setMobile((13000000000L+i) + "");
			user.setName("user"+i);
			user.setSalt("1a2b3c");
			user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSalt()));
			users.add(user);
		}
		return users;
	}
	
	private static void createUser(List<MiaoshaUser> users) throws Exception {
		System.out.println("create user");
		//插入数据库
		Connection conn = DBUtil.getConn();
		String sql = "insert into miaosha_user(name, salt, password, mobile)values(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for(int i=0;i<users.size();i++) {
			MiaoshaUser user = users.get(i);
			pstmt.setString(1, user.getName());
//			pstmt.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
			pstmt.setString(2, user.getSalt());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getMobile());
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		pstmt.close();
		conn.close();
		System.out.println("insert to db");
	}
	private static void createToken(List<MiaoshaUser> users) throws Exception {
		//登录，生成token
		String urlString = "http://localhost:8080/login/do_login";
		File file = new File("/Users/tingkl/Desktop/java/imooc/spring-boot/seckill/tokens.txt");
		if(file.exists()) {
			file.delete();
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		file.createNewFile();
		raf.seek(0);
		for(int i=0;i<users.size();i++) {
			MiaoshaUser user = users.get(i);
			URL url = new URL(urlString);
			HttpURLConnection co = (HttpURLConnection)url.openConnection();
			co.setRequestMethod("POST");
			co.setDoOutput(true);
			OutputStream out = co.getOutputStream();
			String params = "mobile="+user.getMobile()+"&password="+MD5Util.inputPassToFormPass("123456");
			out.write(params.getBytes());
			out.flush();
			InputStream inputStream = co.getInputStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte buff[] = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buff)) >= 0) {
				bout.write(buff, 0 ,len);
			}
			inputStream.close();
			bout.close();
			String response = new String(bout.toByteArray());
			JSONObject jo = JSON.parseObject(response);
			String token = jo.getString("data");
			System.out.println("create token : " + user.getMobile());

			String row = user.getMobile()+","+token;
			raf.seek(raf.length());
			raf.write(row.getBytes());
			raf.write("\r\n".getBytes());
			System.out.println("write to file : " + user.getMobile());
		}
		raf.close();

		System.out.println("over");
	}
	
	public static void main(String[] args)throws Exception {
		List<MiaoshaUser> users = fakeUsers(500);
//		createUser(users);
		createToken(users);
	}
}
