package netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/7 15:27
 */
public class SubReqProtoClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<10;i++){
//            ctx.write(subReq(i));
            ctx.writeAndFlush(subReq(i));
        }
//        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(Integer i){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setAddress("南京市江宁区方山国家地质公园");
        builder.setPhoneNumber("1877498****");
        builder.setUserName("huangxin");
        builder.setSubReqId(i);
        builder.setProductName("netty 开发指南");
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response :" + "【" + msg + "】");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
