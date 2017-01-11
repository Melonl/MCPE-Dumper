package com.mcal.MCPEDumper;
import android.os.*;
import android.app.*;
import android.view.*;
import android.content.*;
import com.mcal.MCPEDumper.nativeapi.*;
import com.mcal.MCPEDumper.util.*;
import com.gc.materialdesign.widgets.*;

public class MenuActivity extends Activity
{
	private String path;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity);
		
		path=getIntent().getExtras().getString("filePath");
	}
	
	public void toNameDemangler(View view)
	{
		startActivity(new Intent(this,NameDemanglerActivity.class));
	}
	
	public void showFloatingMenu(View view)
	{
		new FloatingButton(this,path).show();
	}
	
	private void _saveSymbols()
	{
		String [] strings=new String[Dumper.symbols.size()];
		for(int i=0;i<Dumper.symbols.size();++i)
			strings[i]=Dumper.symbols.get(i).getName();

		FileSaver saver=new FileSaver(this,Environment.getExternalStorageDirectory().toString()+"/MCPEDumper/symbols/","Symbols.txt",strings);
		saver.save();
		
		String [] strings_=new String[Dumper.symbols.size()];
		for(int i=0;i<Dumper.symbols.size();++i)
			strings_[i]=Dumper.symbols.get(i).getDemangledName();
		FileSaver saver_=new FileSaver(this,Environment.getExternalStorageDirectory().toString()+"/MCPEDumper/symbols/","Symbols_demangled.txt",strings_);
		saver_.save();
		
		
	}
	
	private com.gc.materialdesign.widgets.ProgressDialog mDialog;
	private SnackBar mBar;
	private Handler mHandler=new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if(mDialog!=null)
				mDialog.dismiss();
			mDialog=null;
			if(mBar!=null)
				mBar.show();
			else
				new SnackBar(MenuActivity.this,MenuActivity.this.getString(R.string.done)).show();
		}
	};
	public void saveSymbols(View view)
	{
		mDialog=new com.gc.materialdesign.widgets.ProgressDialog(this,getString(R.string.saving));
		mDialog.show();
		mBar=new SnackBar(this,getString(R.string.done));
		new Thread()
		{
			public void run()
			{
				_saveSymbols();
				Message msg=new Message();
				mHandler.sendMessage(msg);
			}
		}.start();
	}
}
