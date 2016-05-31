package fr.iutvalence.info.dut.m2107.toolbox;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

public class TrackingPrintStream extends PrintStream {
	
	private static final String newLine = System.getProperty("line.separator");
	
	private final StringBuffer sb = new StringBuffer();
	private final PrintStream original;
	
	public TrackingPrintStream(PrintStream original) {
		super(original);
		this.original = original;
	}
	
	
	
	public PrintStream append(CharSequence csq) {
		sb.append(csq);
		return original.append(csq);
	}

	public PrintStream append(char c) {
		sb.append(c);
		return original.append(c);
	}

	public PrintStream append(CharSequence csq, int start, int end) {
		sb.append(csq.subSequence(start, end));
		return original.append(csq, start, end);
	}

	public boolean checkError() {
		return original.checkError();
	}

	public void close() {
		original.close();
	}

	public boolean equals(Object obj) {
		return original.equals(obj);
	}

	public void flush() {
		original.flush();
	}

	public PrintStream format(String format, Object... args) {
		return original.format(format, args);
	}

	public PrintStream format(Locale l, String format, Object... args) {
		return original.format(l, format, args);
	}

	public int hashCode() {
		return original.hashCode();
	}
	
	public void print(boolean b) {
		sb.append(b);
		original.print(b);
	}

	public void print(char c) {
		sb.append(c);
		original.print(c);
	}

	public void print(int i) {
		sb.append(i);
		original.print(i);
	}

	public void print(long l) {
		sb.append(l);
		original.print(l);
	}

	public void print(float f) {
		sb.append(f);
		original.print(f);
	}

	public void print(double d) {
		sb.append(d);
		original.print(d);
	}

	public void print(char[] s) {
		sb.append(s);
		original.print(s);
	}

	public void print(String s) {
		sb.append(s);
		original.print(s);
	}

	public void print(Object obj) {
		sb.append(obj);
		original.print(obj);
	}

	public PrintStream printf(String format, Object... args) {
		return original.printf(format, args);
	}

	public PrintStream printf(Locale l, String format, Object... args) {
		return original.printf(l, format, args);
	}

	public void println() {
		sb.append(newLine);
		original.println();
	}

	public void println(boolean x) {
		sb.append(x).append(newLine);
		original.println(x);
	}

	public void println(char x) {
		sb.append(x).append(newLine);
		original.println(x);
	}
	
	public void println(int x) {
		sb.append(x).append(newLine);
		original.println(x);
	}

	public void println(long x) {
		sb.append(x).append(newLine);
		original.println(x);
	}

	public void println(float x) {
		sb.append(x).append(newLine);
		original.println(x);
	}

	public void println(double x) {
		sb.append(x).append(newLine);
		original.println(x);
	}

	public void println(char[] x) {
		sb.append(x).append(newLine);
		original.println(x);
	}

	public void println(String x) {
		sb.append(x).append(newLine);
		original.println(x);
	}

	public void println(Object x) {
		sb.append(x).append(newLine);
		original.println(x);
	}

	public String toString() {
		return original.toString();
	}

	public void write(int b) {
		original.write(b);
	}

	public void write(byte[] buf, int off, int len) {
		original.write(buf, off, len);
	}

	public void write(byte[] b) throws IOException {
		original.write(b);
	}
	
	
	
	public String getAllWrittenText() {
		return sb.toString();
	}
	
	public String getLastWrittenLines(int lines) {
		String sbStr = sb.toString();
		String search = sbStr.substring(0);
		int index = search.lastIndexOf("\n");
		for (int nbLines = 0; nbLines < lines && index != -1; nbLines++) {
			search = search.substring(0, index);
			index = search.lastIndexOf("\n");
		}
		if (index != -1)
			return sbStr.substring(index, sbStr.length());
		else
			return sbStr;
	}
}
