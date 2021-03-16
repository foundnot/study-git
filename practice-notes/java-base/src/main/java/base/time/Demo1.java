package base.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Slf4j
public class Demo1 {

    @Test
    public void test2() {
        LocalDate currentLocalDate = LocalDate.now();
        String format = currentLocalDate.format(DateTimeFormatter.ofPattern("yyyyMM"));
        log.info("LocalDate转换int:{}", Integer.parseInt(format));

        long timestamp = currentLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("LocalDate转换时间戳:{}", timestamp);
    }


    @Test
    public void test1() {
        //获取秒数 gmt+8
        Long second8 = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        //获取毫秒数 gmt+8
        Long milliSecond8 = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

        //获取秒数 gmt+0
        Long second0 = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+0"));
        //获取毫秒数 gmt+0
        Long milliSecond0 = LocalDateTime.now().toInstant(ZoneOffset.of("+0")).toEpochMilli();

        //获取秒数 currentTimeMillis
        long totalMilliSeconds = System.currentTimeMillis();

        System.out.println("+8 second:      " + second8);
        System.out.println("+8 milliSecond: " + milliSecond8);
        System.out.println("+0 seconds:     " + second0);
        System.out.println("+0 milliSeconds:" + milliSecond0);
        //显示时间  System.currentTimeMillis() 获取的是返回当前的计算机时间  当前计算机时间是跟你的计算机所在时区是有关的！！！
        System.out.println("+s seconds:     " + totalMilliSeconds);
    }

    @Test
    public void getExpiration() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        Long NowSecond = now.toEpochSecond(ZoneOffset.of("+8"));
        long tomorrowZero = now.plusDays(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();

        log.info("当前时间秒：{}", NowSecond);
        log.info("明天0点时间秒：{}", tomorrowZero);
        log.info("过期时间戳：{}", tomorrowZero - NowSecond);

        long nowTime = System.currentTimeMillis();
        long tomorrowZeroPoint = LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("当前时间戳：{}", nowTime);
        log.info("明天0点时间戳：{}", tomorrowZeroPoint);
        log.info("过期时间戳：{}", (tomorrowZeroPoint - nowTime));

        LocalDateTime zeroPoint = LocalDate.now().plusDays(1).atTime(0, 0);
        log.info("明天0点时间秒：{}", zeroPoint.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
    }

    @Test
    public void testTenClock() {
        long nowTime = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDate.now(ZoneId.systemDefault()).atTime(10, 0);
        boolean res = localDateTime.isBefore(LocalDateTime.now());
        log.info("当前时间是否在十点之前:{}", res);
    }

    @Test
    public void testLocalDate() {
        LocalDate yesterdayLocalDate = LocalDate.now().minusDays(1);
        String yesterday = yesterdayLocalDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("yesterday:{}", yesterday);
        log.info("统计前一天数据 当前时间：{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        String tableName = String.format("im_chat_message_%s", LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMM")));
        log.info("tableName:{}", tableName);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = dateTimeFormatter.format(LocalDateTime.now());
        log.info("hello，当前时间:{}", time);

        LocalDate currentLocalDate = LocalDate.now();
        String format = currentLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        log.info("LocalDate转换字符串:{}", format);
        long timestamp = currentLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("LocalDate转换时间戳:{}", timestamp);
        String date = "2020-08-11 11:30:15";
        LocalDate parseLocalDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("字符串转换localDate:{}", parseLocalDate);
        LocalDate localDate = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        log.info("时间戳转换localDate:{}", localDate);
        LocalDateTime localDateTime = currentLocalDate.atTime(11, 1, 10);
        log.info("LocalDate转换localDateTime:{}", localDateTime);
        LocalDate localDate1 = localDateTime.toLocalDate();
        log.info("localDateTime转换LocalDate:{}", localDate1);

    }

    @Test
    public void timeWeightTest() {
        final Instant systemOnlineDate = Instant.ofEpochMilli(1546272000000L);
        log.info("day:{}", ChronoUnit.DAYS.between(systemOnlineDate, Instant.now().minus(1, ChronoUnit.DAYS)));
        long startId = ChronoUnit.DAYS.between(systemOnlineDate, Instant.now().minus(1, ChronoUnit.DAYS)) << 16;
        log.info("startId:{}", startId);
    }

    /**
     * 获取用户ID
     * 用户ID生成规则 :
     * 0 - 00000000000000000000 - 0000000000000000
     * 最高位固定为0 - 20位,当前日期与系统上线日期间隔天数 - 低16位为每日注册用户自增
     */
    @Test
    public void testId() {
        final Instant systemOnlineDate = Instant.ofEpochMilli(1546272000000L);
        long day = ChronoUnit.DAYS.between(systemOnlineDate, Instant.ofEpochMilli(1599565373000L));
        log.info("相距：{}天", day);
        long incNumber = 1;

        long leftMove = day << 16;
        log.info("leftMove:{}", leftMove);
        long id = (day << 16) | incNumber;
        log.info("生成ID：{}", id);

        long day1 = ChronoUnit.DAYS.between(systemOnlineDate, Instant.ofEpochMilli(1599565373000L));
        log.info("day1:{}", day1);
    }

    @Test
    public void test11() {
//        int num = 2;
//        String strInt = String.valueOf(num);
//        int intLength = strInt.length() -1;
//        String str = strInt.substring(0, intLength);
//        log.info("str:{}", str);
//        String replaceStr = strInt.replace(str, this.getNewStr(intLength));
//        log.info("replaceStr:{}", replaceStr);

        log.info("str:{}", getHideScore(123));
    }

    private String getHideScore(int score) {
        String scoreStr = String.valueOf(score);
        int scoreLength = scoreStr.length() - 1;
        if (scoreLength > 0) {
            scoreStr = scoreStr.substring(scoreLength);
        }
        return "******" + scoreStr;
    }

    private String getNewStr(int intLength){
        String newStr = "*";
        if(intLength == 2){
            newStr = "**";
        }else if(intLength == 3){
            newStr = "***";
        }else if(intLength == 4){
            newStr = "****";
        }else if(intLength == 5){
            newStr = "*****";
        }else if(intLength == 6){
            newStr = "******";
        }else if(intLength == 7){
            newStr = "*******";
        }else if(intLength == 8){
            newStr = "********";
        }else if(intLength == 9){
            newStr = "*********";
        }else if(intLength == 10){
            newStr = "**********";
        }
        return newStr;
    }
}
