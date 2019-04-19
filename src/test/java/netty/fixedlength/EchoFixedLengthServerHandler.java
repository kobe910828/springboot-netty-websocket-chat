package netty.fixedlength;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/6 16:18
 */
public class EchoFixedLengthServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每次收到消息的时候被调用;
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        String body = (String) msg;
        System.out.println("Receive client： 【" + msg + "】");
    }

    /**
     * 在读操作异常被抛出时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//打印异常的堆栈跟踪信息
        ctx.close();//关闭这个channel
    }
}
