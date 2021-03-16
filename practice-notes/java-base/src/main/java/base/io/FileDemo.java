package base.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.WatchService;


@Slf4j
public class FileDemo {

    @Test
    public void testFile() throws Exception{

        File testFile = File.createTempFile("test", ".txt", new File("D:\\文档\\"));
        log.info("mkdirs:{}", testFile.mkdirs());
        log.info("getAbsolutePath:{}", testFile.getAbsolutePath());
        log.info("getPath:{}", testFile.getPath());
        log.info("getCanonicalPath:{}", testFile.getCanonicalPath());
        log.info("getUsableSpace:{}", testFile.getUsableSpace());
        log.info("getTotalSpace:{}", testFile.getTotalSpace());
        log.info("getParent:{}", testFile.getParent());
        testFile.deleteOnExit();

        Path path = testFile.toPath();
        FileSystem fileSystem = path.getFileSystem();
        WatchService watchService = fileSystem.newWatchService();
        log.info("isAbsolute:{}", path.isAbsolute());
        log.info("getFileName:{}",  path.getFileName());
        log.info("toAbsolutePath:{}", path.toAbsolutePath());
        log.info("getParent:{}", path.getParent());
        log.info("startsWith:{}", path.startsWith("test"));
        log.info("getNameCount:{}", path.getNameCount());
        log.info("resolveSibling:{}", path.resolveSibling("D:\\文档\\url.txt"));
        log.info("getRoot:{}", path.getRoot());
    }
}
