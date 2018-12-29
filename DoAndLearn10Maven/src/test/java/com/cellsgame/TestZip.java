package com.cellsgame;

import com.cellsgame.common.util.zlib.ZLibUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * File Description.
 *
 * @author Yang
 */
public class TestZip {
    private static class A {
        private int a;

        public A(int a) {
            this.a = a;
        }

        public boolean isAlive() {
            return a % 2 == 0;
        }
    }

    public static void main(String[] args) throws IOException {
        InputStream is = TestZip.class.getResourceAsStream("/format.json");

        BufferedReader br = new BufferedReader(new
                InputStreamReader(is));

        StringBuilder sb = new StringBuilder();
        br.lines().forEach(str -> sb.append(str));

        String needZip = sb.toString();
        System.out.println("before ziped size = :"+ needZip.length());
        byte[] ziped = ZLibUtil.compress(needZip.getBytes());
        System.out.println("after zipped size = :"+ziped.length);
    }
}
