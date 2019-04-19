package netty.fixedlength;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/6 16:22
 */
public class EchoFixedLengthClientHandler extends ChannelInboundHandlerAdapter {

    private static final String REQ = "Huangxin welcome to netty at HangZhou";

    /**
     * 向服务端发送消息.
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.copiedBuffer(REQ.getBytes()));
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
