package netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/7 15:15
 */
public class SubReqProtoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq subscribeReq = (SubscribeReqProto.SubscribeReq) msg;
        if("huangxin".equals(subscribeReq.getUserName())){
            System.out.println("Server accept Client subscribe req : 【" + subscribeReq.getSubReqId() + " "
                    + subscribeReq.getUserName() + " " + subscribeReq.getPhoneNumber() + " "
                    + subscribeReq.getProductName() + " " + subscribeReq.getAddress() + " "+ "】") ;
//            System.out.println("Server accept Client subscribe req : 【" + subscribeReq.toString() + "】");
            ctx.writeAndFlush(resp(subscribeReq.getSubReqId()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private SubscribeRespProto.SubscribeResp resp(Integer subReqId){
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setDesc("netty Book order success,3 day later ,send to the designated address");
        builder.setRespCode(0);
        builder.setSubReqId(subReqId);
        return builder.build();
    }
}
