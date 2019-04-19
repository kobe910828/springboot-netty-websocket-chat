package netty.time;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/6 16:24
 */
public class TimeClientTest {
    public static void main(String[] args) throws Exception {
        int port = 56789;
        new TimeClient().connect(port, "localhost");
    }
}
