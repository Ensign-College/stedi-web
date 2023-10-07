package com.getsimplex.steptimer.tcp;

import com.getsimplex.steptimer.model.DeviceInterest;
import com.getsimplex.steptimer.service.MessageIntake;
import com.getsimplex.steptimer.service.SessionValidator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TCPChannelHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Utils.log(ctx.channel().remoteAddress(), "Channel Active");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        String[] messageParts = message.split(":");
        if(messageParts.length<2){
            Utils.log(ctx.channel().remoteAddress(),"Incomplete message, should be token:message, ex: 2130920923:scmurdock@gmail.com");
        } else if (messageParts.length==2){
            String token = messageParts[0];
            String tokenEmail = SessionValidator.emailFromToken(token);
            String email = messageParts[1];
            if (email.contains("\r\n")){
                email = email.split("\r\n")[0];//get rid of carriage return line feed from testing
            }
            if (!tokenEmail.isEmpty() && tokenEmail.equals(email)){
                Utils.log(ctx.channel().remoteAddress(),"Socket subscribing to updates for :"+email);
                DeviceInterest deviceInterest = new DeviceInterest();
                deviceInterest.setInterestedChannel(ctx.channel());
                deviceInterest.setDeviceId(tokenEmail);//for TCP Socket requests we will use the user email as the device ID
                deviceInterest.setInterestedUser(tokenEmail);
                MessageIntake.route(deviceInterest);
            } else{
                Utils.log(ctx.channel().remoteAddress(),"Unable to subscribe to: "+email+" due to invalid token or mismatched email: "+tokenEmail);
            }
        }
        ctx.channel().writeAndFlush("Thanks\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Utils.log(ctx.channel().remoteAddress(), "Channel Inactive");
    }
}
