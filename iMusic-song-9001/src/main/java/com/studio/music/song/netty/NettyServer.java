package com.studio.music.song.netty;

import com.studio.music.song.service.impl.ChatServiceImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @Author: BinBin
 * @Date: 2023/06/05/20:27
 * @Description:
 */
@Component
public class NettyServer {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatServiceImpl chatService;

    @Autowired
    public NettyServer(RedisTemplate<String, Object> redisTemplate, ChatServiceImpl chatService) {
        this.redisTemplate = redisTemplate;
        this.chatService = chatService;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(8000).sync();
            System.out.println("Netty服务启动成功");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            // 处理接收到的消息
            System.out.println("Received message: " + msg);

            // 解析消息中的用户ID和群组ID以及消息内容
            String[] parts = msg.split(":");
            Integer userId = Integer.parseInt(parts[0]);
            Integer groupId = Integer.parseInt(parts[1]);
            String message = parts[2];

            // 发送消息到群组
            chatService.sendMessage(userId, groupId, message);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            System.out.println("Client connected: " + ctx.channel().remoteAddress());

            // 获取客户端IP地址
            String clientIp = ((InetSocketAddress) ctx.channel().remoteAddress()).getHostString();

            // 根据客户端IP地址获取用户ID
            Long userId = getUserIdByIp(clientIp);

            // 订阅用户的消息队列（使用用户ID作为队列名称）
            redisTemplate.execute((RedisCallback<Object>) connection -> {
                connection.subscribe((message, pattern) -> {
                    // 收到消息时的处理逻辑
                    String jsonMessage = new String(message.getBody());
                    System.out.println("Received message from queue: " + jsonMessage);

                    // 将消息发送给客户端
                    ctx.writeAndFlush(jsonMessage);
                }, userId.toString().getBytes());
                return null;
            });
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            System.out.println("Client disconnected: " + ctx.channel().remoteAddress());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        private Long getUserIdByIp(String ip) {
            // 根据IP地址查询用户ID的逻辑
            // 例如，可以从数据库或Redis中查询与IP地址相关联的用户ID
            return 1L;
        }
    }
}

