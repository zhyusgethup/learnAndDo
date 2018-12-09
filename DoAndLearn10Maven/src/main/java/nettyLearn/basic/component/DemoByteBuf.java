package nettyLearn.basic.component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class DemoByteBuf {
    public static void main(String[] args) {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char)buf.readByte());
        int readerIndex = buf.readerIndex();
        int writeIndex = buf.writerIndex();
        buf.writeByte((byte)'?');
        assert readerIndex == buf.readerIndex();
        assert writeIndex != buf.writerIndex();
        System.out.println("over");
    }
}
