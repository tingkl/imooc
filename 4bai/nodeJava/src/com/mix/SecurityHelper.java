package com.mix;

/**
 * 
 */
public class SecurityHelper {
    private static String privateKey = "";
    
    /**
     * @param str
     * @return
     * @throws Exception
     */
    public static String encrypt(String str) throws Exception {
        return encrypt(str, privateKey);
    }


    public static String encrypt(String str,String encrypt_key) throws Exception {
        DES des;
        while(str.getBytes().length%8!=0){
            str+=" ";
        }
        String tmp =  str;
        des = new DES(encrypt_key);
        return des.encrypt(tmp);
    }
    
    /**
     * @param str
     * @return
     * @throws Exception
     */
    public static String decrypt(String str) throws Exception {
        DES des;
        des = new DES(privateKey);
        String tmp = des.decrypt(str);
        return tmp.trim();
    }
    
    public static String decrypt(String str,String encrypt_key) throws Exception {
        DES des;
        des = new DES(encrypt_key);
        String tmp = des.decrypt(str);
        return tmp.trim();
    }
    
    
    public static void main(String[]args){
        System.out.println("main1");
        String key = "jP9xl59o";
        System.out.println("shit whatis that?");
    	try{
   		   // System.out.println(decrypt("db3de9a2bbd67f1fd2e101d9ffb304654827bc98d243a1cfc49997aac8f8638664114736eb43ebbd212c2e013846f47536f93f40470d0748ad4fb47c3433c80a6deb8eb533a30f1d491d13db530dd822b7a95d9282c8a26a550f753617c3ad1af2396ba9b6bccd2483d1cff05f17076faa96dfb2d99ebfebed5d45d144819b26e2f76a038223bad4582b68a411b33fde","jP9xl59o"));
            System.out.println(encrypt("扯淡了", key));
            //                             "e65d37b6767a3901ff2b3b814b2557ca"
            System.out.println(decrypt("e65d37b6767a3901ff2b3b814b2557ca", key));
        }catch(Exception e){
    		
    	}
    }

}

