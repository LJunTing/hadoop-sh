package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

public class NettyClient {

    @Test
    public void startclient() {
        String host = "localhost";
        int port = 8080;
        //链接池处理数据
        NioEventLoopGroup workgruop = new NioEventLoopGroup();
        //客户端引导类  方法链 配置
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workgruop) //绑定链接池
                .channel(NioSocketChannel.class)//指定通道类型 NIOserver socket
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //添加handler链   各种任务链
                        socketChannel.pipeline().addLast(new ClientHandler());
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        try {
            //最后链接服务器等待直到链接成功,调用sync()方法会阻塞到链接服务器成功 ,然后服务器等待链接关闭
            ChannelFuture sync = bootstrap.connect(host, port).sync();
            sync.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                workgruop.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

//    public static void main(String[] args) {
//        String host = args[0];
//        int port = Integer.parseInt(args[1]);
//        //链接池处理数据
//        NioEventLoopGroup workgruop = new NioEventLoopGroup();
//        //客户端引导类  方法链 配置
//        Bootstrap bootstrap = new Bootstrap();
//        bootstrap.group(workgruop) //绑定链接池
//                .channel(NioServerSocketChannel.class)//指定通道类型 NIOserver socket
//                .option(ChannelOption.SO_KEEPALIVE, true)
//                .handler(new ChannelInitializer<SocketChannel>() {
//                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        //添加handler链   各种任务链
//                        socketChannel.pipeline().addLast(new ClientHandler());
//                    }
//                });
//        try {
//            //最后链接服务器等待直到链接成功,调用sync()方法会阻塞到链接服务器成功 ,然后服务器等待链接关闭
//            ChannelFuture sync = bootstrap.connect(host, port).sync();
//            sync.channel().closeFuture().sync();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            workgruop.shutdownGracefully();
//        }
//
//
//    }
}
