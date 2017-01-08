package com.mcal.MCPEDumper;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.view.View.*;
import com.gc.materialdesign.widgets.*;

public class FloatingMenu
{
	public static int xPos=0;
	public static int yPos=0;
	public boolean isAdded = false;
	public WindowManager wm;
	public WindowManager.LayoutParams params; 
	public FloatingMenuView floatView;

	private Context context;
	private String path;
	FloatingMenu(Context c,String filePath)
	{
		context = c;
		path=filePath;
	}

	public void show()
	{  
		floatView = new FloatingMenuView(context,this,path);
		floatView.setClickable(true);

		wm = (WindowManager) context.getApplicationContext()  .getSystemService(Context.WINDOW_SERVICE);  
		params = new WindowManager.LayoutParams();  


		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;  

		params.format = PixelFormat.RGBA_8888;


		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;    
		params.width = wm.getDefaultDisplay().getWidth()/2;
		params.height = wm.getDefaultDisplay().getHeight()/2;
		params.x = xPos;
		params.y = yPos;


		floatView.setOnTouchListener(new OnTouchListener() {  
				int lastX, lastY;  
				int paramX, paramY;  

				public boolean onTouch(View v, MotionEvent event)
				{  
					switch (event.getAction())
					{  
						case MotionEvent.ACTION_DOWN:  
							lastX = (int) event.getRawX();  
							lastY = (int) event.getRawY();  
							paramX = params.x;  
							paramY = params.y;  
							break;  
						case MotionEvent.ACTION_MOVE:  
							int dx = (int) event.getRawX() - lastX;  
							int dy = (int) event.getRawY() - lastY;  
							params.x = paramX + dx;  
							params.y = paramY + dy;    
							wm.updateViewLayout(floatView, params);  
							break;
					}
					FloatingMenu.xPos=params.x;
					FloatingMenu.yPos=params.y;
					return false;
				}  
			});  

		wm.addView(floatView, params);
		isAdded = true;
	}
	
	public void dismiss()
	{
		wm.removeView(floatView);
	}
}
