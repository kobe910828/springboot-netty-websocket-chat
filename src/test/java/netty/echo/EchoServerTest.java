package netty.echo;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/6 16:23
 */
public class EchoServerTest {
    public static void main(String[] args) throws Exception{
        int port = 56789;
        new EchoServer().bind(port);
    }
}
