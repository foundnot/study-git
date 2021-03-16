package base.time;


import javax.servlet.http.HttpServletRequest;

public class IPV4Util {

    public static long getClientIp2Long(HttpServletRequest request) {
        String ip = getClientIP(request);
        return ip2Long(ip);
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("cdn-src-ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            return ip.split(",")[0].trim();
        }
        return "0.0.0.0";
    }

    public static int getClientPort(HttpServletRequest request) {
        return request.getHeader("remote_port") != null ? Integer.parseInt(request.getHeader("remote_port")) : 0;
    }

    public static long ip2Long(String ipAddress) {
        // ipAddressInArray[0] = 192
        String[] ipAddressInArray = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            // 1. 192 * 256^3(2^24) 	相当于192的二进制左移了24位
            // 2. 168 * 256^2(2^16)		相当于168的二进制左移了16位
            // 3. 1 * 256^1(2^8)		相当于1的二进制左移了8位
            // 4. 2 * 256^0(2^0)		相当于2的二进制左移了0位

//            System.out.println("pow:" + Math.pow(256, power));
//            System.out.println("ip:" + ip * Math.pow(256, power));
            result += ip * Math.pow(256, power);
        }
        return result;
    }

    public static String long2Ip(long i) {
        System.out.println(">>24:" + (i >> 24));
        System.out.println(">>16:" + (i >> 16));
        System.out.println(">>8:" + (i >> 8));
        return ((i >> 24) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + (i & 0xFF);
    }


    public static void main(String[] args) {
//		long ipnum = ip2Long("123.245.23.183");
//		String ipstring = long2Ip(2079659959);
//		System.out.println(ipnum);
//		System.out.println(ipstring);

//        long ipnum = ip2Long("192.168.1.2");
//        System.out.println(ipnum);
        String ipstring = long2Ip(2085001870);
        System.out.println(ipstring);

//        System.out.println(15 >> 2); // 3
//        System.out.println(10 << 2); // 40
//        System.out.println(10 << 2 & 0xff); // 40
//		System.out.println(2079659959 >> 16);
//		System.out.println(2079659959 >> 8 & 0Xff);
//		System.out.println(2079659959 >> 8 );
//		System.out.println(2079659959 & 0xFF);
    }

}
