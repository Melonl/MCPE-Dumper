package com.mcal.MCPEDumper.vtable;
import java.io.*;
import java.util.*;
import com.mcal.MCPEDumper.nativeapi.*;
import android.content.*;
import android.widget.*;

public class VtableDumper
{
	public static MCPEVtable dump(String path,String classn)
	{
		Dump d=new Dump(path);

		symbol sym=null;
		section symsec=null;
		
		for (section sec:d.elf.sections)
		{
			if (sec.type == 2 || sec.type == 11)
			{
				for (int i=0;i < d.getSymNum(sec);++i)
				{
					symbol sym_=d.getSym(sec, i);
					if (sym_.name.equals(classn))
					{
						sym = sym_;
						symsec = sec;
						break;
					}
				}
			}
		}

		if (sym == null)
			return null;

		HashMap<Integer,symbol>map=new HashMap<Integer,symbol>();//为了排序
		int c=0;

		for (section sec:d.elf.sections)
		{
			if (sec.name.equals(".rel.dyn"))
			{
				for (int i=0;i < d.getRelNum(sec);++i)
				{
					relocation rel=d.getRel(sec, i);
					for (int j=0;j < sym.size / 4 - 2;++j)
					{
						if (sym.value + 8 + j * 4 == rel.offset)
						{
							++c;
							symbol vsym=d.getSym(symsec, rel.info >> 8);
							
							map.put(rel.offset, vsym);
						}
					}
					if (map.size() == sym.size / 4 - 2)
					{
						break;
					}
				}
			}
		}
		
		Vector<MCPESymbol> virtual_table_symbols=new Vector<MCPESymbol>();
		
		for (int j=0;j < sym.size / 4 - 2;++j)
		{
			if (map.get(sym.value + 8 + j * 4) != null)
				virtual_table_symbols.addElement(new MCPESymbol(map.get(sym.value + 8 + j * 4).name,MCPEDumper.demangle(map.get(sym.value + 8 + j * 4).name),2));
		}
		return new MCPEVtable(classn,virtual_table_symbols);
	}
}
