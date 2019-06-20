package com.aiyi.server.manager.nginx.utils;

import com.aiyi.server.manager.nginx.common.SystemUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


@Slf4j
public class PropsUtils {

  private static Properties prop = null;

  static {
    String confDir = System.getProperty("conf.dir");
    if (null == confDir){
      System.setProperty("conf.dir", System.getProperty("user.dir"));
    }
  }

  /**
   * 从注入参数中设置系统变量
   * @param args
   */
  public static void setSysPropByArgs(String[] args){
    if (null != args && args.length > 0){
      for (String arg: args){
        if (arg.contains("=")){
          String[] conf = arg.split("=");
          System.setProperty(conf[0], conf[1]);
        }
      }
    }
  }
  
  /**
   * 获得配置值
   * @param key
   * @return
   */
  public static String get(String key) {
    String dir = System.getProperty("conf.dir");
    if (SystemUtils.isWindows()) {
      dir += "/target/classes/conf.properties";
    } else {
      dir += "/classes/conf.properties";
    }
    log.info("conf.dir -> {}", dir);
    try (FileInputStream fileInputStream = new FileInputStream(dir);
         InputStreamReader reader = new InputStreamReader(fileInputStream, "UTF-8")){
      if(null == prop) {
        prop = new Properties();
        prop.load(reader);
      }
     return prop.getProperty(key);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void set(String key, String value) {
    String property = System.getProperty("conf.dir");
    FileOutputStream out = null;
    if (null == prop) {
      get("default");
    }
    prop.setProperty(key, value);
    try {
      out = new FileOutputStream(property);
      prop.store(out, "账号");
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void main(String[] args) {
    System.out.println(get("bc.server.address"));
  }
}
