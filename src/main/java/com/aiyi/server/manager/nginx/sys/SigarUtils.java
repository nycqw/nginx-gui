package com.aiyi.server.manager.nginx.sys;

import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.Sigar;

import java.io.File;
import java.io.IOException;

@Slf4j
public class SigarUtils {
    public final static Sigar sigar = initSigar();

    private static Sigar initSigar() {
        // 寻找 classpath 根目录下的 sigar 文件夹
        String userDir = System.getProperty("user.dir");
        File classPath = new File(userDir + "/sigarLibs");
        try {
            // 追加库路径
            String sigarLibsPath = classPath.getCanonicalPath();
            log.info("sigarLibsPath -> {}", sigarLibsPath);

            System.setProperty("org.hyperic.sigar.path", sigarLibsPath);
        } catch (IOException e) {
            log.error("append sigar to java.library.path error", e);
        }
        return new Sigar();
    }

}
