package base.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Slf4j
public class StreamDemo {

    @Test
    public void operateZipTest() throws Exception {
        File zipFile = new File("D:\\zOperate\\ac.zip");
        File[] files = {new File("D:\\zOperate\\aaaaa.txt"),
                        new File("D:\\zOperate\\bbbbb.java"),
                        new File("D:\\zOperate\\ccccc.php")};

        /**
         * @写入Zip文件
         */
//        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFile))) {
//            for (File file : files) {
//                zip.putNextEntry(new ZipEntry(file.getName()));
//                zip.write(getFileDataAsBytes(file));
//                zip.closeEntry();
//            }
//        }

        /**
         * @从zip文件中读取
         */
        try(ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile)))
        {
            ZipEntry entry;
            while((entry = zip.getNextEntry()) != null)
            {
                String name = entry.getName();
                log.info("name:{}", name);
                if(!entry.isDirectory())
                {
                    int n;
                    while((n=zip.read())!=-1)
                    {
                        log.info("n:{}", (char)n);
                    }
                }
            }
        }
    }

    private static byte[] getFileDataAsBytes(File f) throws Exception {
        byte[] data = new byte[(int) f.length()];
        byte[] bytes = new byte[1024];

//        String str;
//        try(InputStream input = new FileInputStream(f)){
//            StringBuilder stringBuffer = new StringBuilder();
//            int i;
//            while ((i = input.read()) != -1){
//                stringBuffer.append((char) i);
//            }
//            str = stringBuffer.toString();
//        }
//        try(InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8))){
//            int i;
//            while ((i = inputStream.read(data)) != -1){
//                log.info("read:{} bytes", i);
//            }
//        }catch (Exception ex){
//            log.info("写入失败 异常信息:{}", ex.getMessage());
//        }

        try (InputStream is = new FileInputStream(f)) {
            int n;
            while ((n = is.read(data)) != -1) {
                System.out.println("read:" + n + "bytes");
            }
        } catch (IOException ex) {
            log.info("写入失败 异常信息:{}", ex.getMessage());
        }
        return data;
    }

    @Test
    public void inputStreamTest() throws Exception {
        // 定义1000个字节大小的缓冲区:
        byte[] buffer = new byte[1024];
        // FileInputStream实现了文件流输入
        // inputStream 实现了java.lang.AutoCloseable接口 自动加上finally语句并调用close()方法
        try (InputStream input = new FileInputStream("D:\\文档\\url.txt")){
            String str = readAsString(input);
            log.info("str:{}", str);

            int n;
            while (((n = input.read(buffer))) != -1) {
                System.out.println(n);
                char[] chars1 = Character.toChars(n);
                log.info("chars:{}", chars1);
            }
        }

        // ByteArrayInputStream在内存中模拟一个字节流输入
//        String str = "hello world";
//        try (InputStream input = new ByteArrayInputStream(str.getBytes())) {
//            String s = readAsString(input);
//            System.out.println(s);
//        }
    }

    @Test
    public void outputStreamTest() throws Exception {
        File file = new File("d:\\文档\\output.txt");
        try (OutputStream output = new FileOutputStream(file)) {
            output.write("哈喽".getBytes(StandardCharsets.UTF_8));
            output.write("大家".getBytes(StandardCharsets.UTF_8));
            output.write("好".getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    public void copyTest() throws Exception {
//        File targetFile = new File("d:\\文档\\input.txt");
//        File outputFile = new File("d:\\文档\\output.txt");
//        File temFile = new File("d:\\文档\\output.txt");
//
//        temFile.createNewFile();
//        outputFile.createNewFile();
//        targetFile.createNewFile();
//
//        try(OutputStream targetStream = new FileOutputStream(targetFile);
//            InputStream inputStream = new FileInputStream(temFile);
//            OutputStream outputStream = new FileOutputStream(outputFile)){
//            outputStream.write("大家好奥拉夫水电费大范甘迪收购".getBytes(StandardCharsets.UTF_8));
//            String str = readAsString(inputStream);
//            log.info("str:{}", str);
//            targetStream.write(str.getBytes(StandardCharsets.UTF_8));
//        }

        try (InputStream input = new FileInputStream("d:\\文档\\output.txt");
             OutputStream output = new FileOutputStream("d:\\文档\\input.txt")) {
            //StringBuilder方法
            int n;
            StringBuilder sb = new StringBuilder();
            while ((n = input.read()) != -1) {
                log.info("char:{}", (char) n);
                sb.append((char) n);
            }
            log.info("str:{}", sb.toString());
            output.write(sb.toString().getBytes(StandardCharsets.UTF_8));

            //Byte []方法
//            byte[] b = new byte[1024];
//            while (input.read(b) != -1) {
//                output.write(b);
//            }
        }
    }

    @Test
    public void filterStreamTest() throws IOException {
        // 统计输入的字节数
        byte[] data = "hello, world!".getBytes(StandardCharsets.UTF_8);
        try (CountInputStream input = new CountInputStream(new ByteArrayInputStream(data))) {
            int n;
            while ((n = input.read()) != -1) {
                System.out.println((char) n);
            }
            System.out.println("Total read " + input.getBytesRead() + " bytes");
        }
    }

    // Filter模式可以在运行期动态增加功能（又称Decorator模式）
    static class CountInputStream extends FilterInputStream {
        private int count = 0;

        CountInputStream(InputStream in) {
            super(in);
        }

        public int getBytesRead() {
            return this.count;
        }

        public int read() throws IOException {
            int n = in.read();  // n表示读取到的字节值
            if (n != -1) {
                this.count++;
            }
            return n;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            int n = in.read(b, off, len);  // n 表示读取到的字节数
            this.count += n;
            return n;
        }
    }

    private static String readAsString(InputStream input) throws IOException {
        int n;
        StringBuilder sb = new StringBuilder();
        while ((n = input.read()) != -1) {
            sb.append((char) n);
        }
        return sb.toString();
    }


}
