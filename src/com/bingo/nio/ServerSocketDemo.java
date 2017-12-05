package com.bingo.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketDemo {

    private Selector selector;//用于处理所有的网络连接
    private ExecutorService tp= Executors.newCachedThreadPool();
    public static Map<Socket,Long> time_stat=new HashMap<>();//统计每个socket上处理时间

    private void startServer ()throws Exception{
        selector= SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc=ServerSocketChannel.open();//获取服务端socketChannel
        ssc.configureBlocking(false);//设置非阻塞
        InetSocketAddress isa=new InetSocketAddress(8888);
        ssc.socket().bind(isa);//绑定端口

        //注册为accept事件，这样当ssc有新的客户端连接时，selector就会通知ssc进行处理
        //SelectionKey表示一对channel与selector的关系。
        // 当channel注册到selector上，关系就产生了。当selector或channel关闭，对应的SelectionKey失效
        SelectionKey acceptkey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        //无限循环，等待分发网络消息
        for (;;){
            //select()是一个阻塞方法，如果数据没准备好就会一直等待
            //一旦有数据可读，就会立即返回
            selector.select();
            Set<SelectionKey> skey = selector.selectedKeys();
            Iterator i=skey.iterator();
            long e=0;
            while (i.hasNext()) {
                SelectionKey sk=(SelectionKey) i.next();
                i.remove();

                if (sk.isAcceptable()){//判断当前sk所代表的channel是否在acceptable状态，如果是，就进行客户端的接受
                    doAccept(sk);
                }else if (sk.isValid()&&sk.isReadable()){//判断channel是否已经可以读了
                    if (!time_stat.containsKey(((SocketChannel)sk.channel()).socket())){
                        time_stat.put(((SocketChannel)sk.channel()).socket(),System.currentTimeMillis());
                    }
                    doRead(sk);
                }else if (sk.isValid()&&sk.isWritable()){//判断channel是否已经可以写了
                    doWrite(sk);
                    e=System.currentTimeMillis();
                    long b=time_stat.remove(((SocketChannel)sk.channel()).socket());
                    System.out.println("time="+(e-b)+"ms");
                }
            }
        }

    }

    private void doRead(SelectionKey sk) {
        ServerSocketChannel ssc=(ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel=ssc.accept();
            clientChannel.configureBlocking(false);
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            EchoClient ec=new EchoClient();
            clientKey.attach(ec);
            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("accept connect from "+clientAddress.getHostAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void doWrite(SelectionKey sk) {

    }

    private void doAccept(SelectionKey sk) {
    }

    class EchoClient{
        private LinkedList<ByteBuffer> outq;
        EchoClient(){
            outq=new LinkedList<>();
        }
        public LinkedList<ByteBuffer> getOutq(){
            return outq;
        }
        public void entqueue(ByteBuffer bb){
            outq.addFirst(bb);
        }
    }
}
