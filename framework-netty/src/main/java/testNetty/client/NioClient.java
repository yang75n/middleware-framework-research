package testNetty.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by iQiwen on 2019/3/26.
 */
public class NioClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8088));
        socketChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("nihao".getBytes());
        socketChannel.write(buffer);

        buffer.clear();
        int ret = socketChannel.read(buffer);
        System.out.println("ret=" + ret);
        System.out.println("收到=" + new String(buffer.array()));


//
//        Selector selector = Selector.open();
//        socketChannel.register(selector, SelectionKey.OP_WRITE);
//
//        while (selector.select() > 0) {
//            System.out.println("有了");
//            for (SelectionKey selectionKey : selector.selectedKeys()) {
//                if (selectionKey.isReadable()) {
//                    System.out.println("有可读了");
//                    SocketChannel sc = (SocketChannel) selectionKey.channel();
//                    ByteBuffer buffer = ByteBuffer.allocate(1024);
//                    sc.read(buffer);
//                    System.out.println("收到=" + new String(buffer.array()));
//                }
//                if (selectionKey.isWritable()) {
//                    System.out.println("有可写了");
//                    SocketChannel sc = (SocketChannel) selectionKey.channel();
//                    ByteBuffer buffer = ByteBuffer.allocate(1024);
//                    buffer.put("nihao".getBytes());
//                    sc.write(buffer);
//                }
//            }
//        }


    }
}
