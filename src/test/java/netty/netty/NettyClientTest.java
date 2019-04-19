package netty.netty;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/2 17:00
 */
public class NettyClientTest {
    public static void main(String[] args) throws Exception {
        NettyClient client=new NettyClient();
        client.connect("127.0.0.1", 9999);

    }
}
