package com.ktw.ktwlib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ktw.ktwlib.R;
import com.ktw.ktwlib.widget.PromptDialog;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Util {
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**获取屏幕分辨率宽*/
	public static int getScreenWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		//((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		display.getMetrics(dm);

		return dm.widthPixels;
	}

	/**
	 * 弹出toast提示信息
	 *
	 * @param activity 弹出的页面
	 * @param message 要弹出的信息
	 */
	public static void showToast(Activity activity, String message) {
		Toast toast = new Toast(activity);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LayoutInflater inflater = activity.getLayoutInflater();
		LinearLayout toastLayout = (LinearLayout) inflater.inflate(R.layout.toast_layout, null);
		TextView toastText = toastLayout.findViewById(R.id.toast_text);
		toastText.setText(message);
		toast.setView(toastLayout);
		toast.show();
	}

	/**
	 * 在UI线程中弹出toast提示信息
	 *
	 * @param activity 要弹出来的页面
	 * @param message 要弹出的信息
	 */
	public static void showOnUiThreadToast(final Activity activity, final String message) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showToast(activity, message);
			}
		});
	}

	/**
	 * 退出程序提示
	 * */
	public static void quit(final Context context) {
		String ok = context.getString(R.string.comfirm);
		String cancel = context.getString(R.string.cancel);
		String tip1 = context.getString(R.string.exit_system);
		String tip2 = context.getString(R.string.confirm_exit);

		PromptDialog.Builder promptDialog = new PromptDialog.Builder(context);
		promptDialog.setViewStyle(PromptDialog.VIEW_STYLE_TITLEBAR_SKYBLUE);
		promptDialog.setTitle(tip1);
		promptDialog.setMessage(tip2);
		promptDialog.setButton1TextColor(Color.parseColor("#FFFFFF"));
		promptDialog.setButton1(ok, new PromptDialog.OnClickListener() {
			@Override
			public void onClick(Dialog dialog, int which) {
				dialog.dismiss();
				// 关闭当前的Activity
				((Activity) context).finish();
				System.exit(0);
			}
		});
		promptDialog.setButton2TextColor(Color.parseColor("#FFFFFF"));
		promptDialog.setButton2(cancel, new PromptDialog.OnClickListener() {
			@Override
			public void onClick(Dialog dialog, int which) {
				dialog.dismiss();
			}
		});
		promptDialog.setCanceledOnTouchOutside(false);
		promptDialog.create().show();

	}

	public static void quitEdit(final Context context) {
		String ok = context.getString(R.string.comfirm);
		String cancel = context.getString(R.string.cancel);
		String tip1 = context.getString(R.string.exit);
		String tip2 = context.getString(R.string.exit_remind);

		PromptDialog.Builder promptDialog = new PromptDialog.Builder(context);
		promptDialog.setViewStyle(PromptDialog.VIEW_STYLE_TITLEBAR_SKYBLUE);
		promptDialog.setTitle(tip1);
		promptDialog.setMessage(tip2);
		promptDialog.setButton1TextColor(Color.parseColor("#FFFFFF"));
		promptDialog.setButton1(ok, new PromptDialog.OnClickListener() {
			@Override
			public void onClick(Dialog dialog, int which) {
				dialog.dismiss();
				// 关闭当前的Activity
				((Activity) context).finish();
			}
		});
		promptDialog.setButton2TextColor(Color.parseColor("#FFFFFF"));
		promptDialog.setButton2(cancel, new PromptDialog.OnClickListener() {
			@Override
			public void onClick(Dialog dialog, int which) {
				dialog.dismiss();
			}
		});
		promptDialog.setCanceledOnTouchOutside(false);
		promptDialog.create().show();
	}

	/**
	 * 显示键盘
	 *
	 * @param et 输入焦点
	 */
	public static void showInput(Activity activity, final EditText et) {
		et.requestFocus();
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
		imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
	}

	/**
	 * 隐藏键盘
	 */
	public static void hideInput(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
		View v = activity.getWindow().peekDecorView();
		if (null != v) {
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	public static boolean isNetWorkConnected(Context context)
	{
		if(null != context) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if(null != mNetworkInfo) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 将地图原比例尺转换成带单位的比例尺
	 *
	 * @param scale 原比例尺
	 * @return 转换后的带单位的比例尺
	 */
	public static String getScaleText(double scale) {
		scale /= 100;// 转成厘米：米
		String formatStr = null;
		if (scale >= 1000) {
			scale /= 1000;
//			formatStr = "%1$.1f公里";
			formatStr = "%1$.1f km";
		} else if (scale >= 1) {
			formatStr = "%1$.1f m";
		} else if (scale >= 0.1) {
			scale *= 10;
			formatStr = "%1$.1f dm";
		} else {
			scale *= 100;
			formatStr = "%1$.1f cm";
		}
		return String.format(formatStr, (float) scale);
	}
}
