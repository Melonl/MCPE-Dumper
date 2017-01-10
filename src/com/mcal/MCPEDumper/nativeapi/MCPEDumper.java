package com.mcal.MCPEDumper.nativeapi;

public class MCPEDumper
{
	public static native void load(String path);
	public static native boolean hasFile(String path);
	public static native String getNameAt(long pos);
	public static native String getDemangledNameAt(int pos);
	public static native short getTypeAt(int pos);
	public static native short getBindAt(int pos);
	public static native int getSize();
	public static native String demangle(String name);
	public static native String demangleOnly(String name);
}
