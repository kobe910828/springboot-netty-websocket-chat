package netty.objEncoderDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/7 15:15
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq subscribeReq = (SubscribeReq) msg;
        if("huangxin".equals(subscribeReq.getUserName())){
            System.out.println("Server accept Client subscribe req : 【" + subscribeReq.toString() + "】") ;
            ctx.writeAndFlush(resp(subscribeReq.getSubReqId()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private SubscribeResp resp(Integer subReqId){
        SubscribeResp resp = new SubscribeResp();
        resp.setDesc("netty Book order success,3 day later ,send to the designated address");
        resp.setRespCode(0);
        resp.setSubReqId(subReqId);
        return resp;
    }
}
