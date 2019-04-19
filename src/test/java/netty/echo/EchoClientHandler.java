package netty.echo;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/6 16:22
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private int counter = 0;

    private static final String REQ = "LOUYYUTING netty. $_";

    /**
     * 循环向服务端发送10条消息.
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for(int i=0; i<10; i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(REQ.getBytes()));
        }
    }

    /**
     * 每当收到数据时这个方法会被调用.打印收到的消息日志
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("client received: " + "counter:" + (++counter) + "  msg:" + body);
    }

    /**
     * 异常发生时,记录错误日志,关闭channel
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//打印堆栈的错误日志
        ctx.close();
    }
}
