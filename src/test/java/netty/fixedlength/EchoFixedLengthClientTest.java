package netty.fixedlength;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/6 16:24
 */
public class EchoFixedLengthClientTest {
    public static void main(String[] args) throws Exception {
        int port = 56789;
        new EchoFixedLengthClient().connect(port, "localhost");
    }
}
