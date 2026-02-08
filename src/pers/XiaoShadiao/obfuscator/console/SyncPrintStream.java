package pers.XiaoShadiao.obfuscator.console;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

public class SyncPrintStream extends PrintStream {

    private final Printer printer;
    private final StringBuffer lineBuffer = new StringBuffer();
    private int tier = 0;

    public SyncPrintStream(Printer printer, OutputStream out) {
        super(out);
        this.printer = printer;
    }

    private void flushLine() {
        // if (lineBuffer.length() > 0) {
        synchronized (printer) {
            tier++;
            boolean flag = tier == 1;
            if (flag) printer.deleteText();

            // 直接调用底层的 write 方法，避免递归
            try {
                out.write(lineBuffer.toString().getBytes());
                out.write('\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (flag) printer.printText();
            tier--;
        }
        lineBuffer.setLength(0);
        // }
    }

    @Override
    public void write(int b) {
        char c = (char) b;
        if (c == '\n') {
            flushLine();
        } else {
            lineBuffer.append(c);
        }
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        for (int i = off; i < off + len; i++) {
            char c = (char) buf[i];
            if (c == '\n') {
                flushLine();
            } else {
                lineBuffer.append(c);
            }
        }
    }

    @Override
    public void print(boolean b) {
        lineBuffer.append(b);
    }

    @Override
    public void print(char c) {
        if (c == '\n') {
            flushLine();
        } else {
            lineBuffer.append(c);
        }
    }

    @Override
    public void print(int i) {
        lineBuffer.append(i);
    }

    @Override
    public void print(long l) {
        lineBuffer.append(l);
    }

    @Override
    public void print(float f) {
        lineBuffer.append(f);
    }

    @Override
    public void print(double d) {
        lineBuffer.append(d);
    }

    @Override
    public void print(char[] s) {
        for (char c : s) {
            if (c == '\n') {
                flushLine();
            } else {
                lineBuffer.append(c);
            }
        }
    }

    @Override
    public void print(String s) {
        if (s == null) {
            lineBuffer.append("null");
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\n') {
                flushLine();
            } else {
                lineBuffer.append(c);
            }
        }
    }

    @Override
    public void print(Object obj) {
        lineBuffer.append(String.valueOf(obj));
    }

    @Override
    public void println() {
        flushLine();
    }

    @Override
    public void println(boolean x) {
        print(x);
        println();
    }

    @Override
    public void println(char x) {
        print(x);
        println();
    }

    @Override
    public void println(int x) {
        print(x);
        println();
    }

    @Override
    public void println(long x) {
        print(x);
        println();
    }

    @Override
    public void println(float x) {
        print(x);
        println();
    }

    @Override
    public void println(double x) {
        print(x);
        println();
    }

    @Override
    public void println(char[] x) {
        print(x);
        println();
    }

    @Override
    public void println(String x) {
        print(x);
        println();
    }

    @Override
    public void println(Object x) {
        print(x);
        println();
    }

    // 确保程序退出时刷新所有缓冲内容
    @Override
    public void flush() {
        flushLine();
        super.flush();
    }

    @Override
    public void close() {
        flushLine();
        super.close();
    }

    // 其他方法保持默认实现
    @Override
    public PrintStream printf(String format, Object... args) {
        print(String.format(format, args));
        return this;
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        print(String.format(l, format, args));
        return this;
    }

    @Override
    public PrintStream format(String format, Object... args) {
        print(String.format(format, args));
        return this;
    }

    @Override
    public PrintStream format(Locale l, String format, Object... args) {
        print(String.format(l, format, args));
        return this;
    }

    @Override
    public PrintStream append(CharSequence csq) {
        if (csq == null) {
            print("null");
        } else {
            print(csq.toString());
        }
        return this;
    }

    @Override
    public PrintStream append(CharSequence csq, int start, int end) {
        if (csq == null) {
            print("null");
        } else {
            print(csq.subSequence(start, end).toString());
        }
        return this;
    }

    @Override
    public PrintStream append(char c) {
        print(c);
        return this;
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }
}