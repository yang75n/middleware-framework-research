package testNetty.client;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Hello world!
 */
public class SocketClient {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 8088));
        System.out.println("连接成功");


        //Socket socket = new Socket("baidu.com", 443);
       socket.getOutputStream().write("nihao".getBytes());
        byte[] data = new byte[1024];
        int ret = socket.getInputStream().read(data);
        System.out.println("ret=" + ret);
        System.out.println("收到=" + new String(data));
        System.in.read();
    }
}
