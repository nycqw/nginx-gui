package com.aiyi.server.manager.nginx.sys;

import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.Sigar;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class SigarUtils {
    public final static Sigar sigar = initSigar();

    private static Sigar initSigar() {
        String osName = System.getProperty("os.name");
        // 寻找 classpath 根目录下的 sigar 文件夹
        String userDir = System.getProperty("user.dir");
        File classPath;
        if (osName.indexOf("Windows") != -1) {
            classPath = new File(userDir + "/sigarLibs");
        } else {
            classPath = new File(userDir + "/classes/sigarLibs");
        }

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
