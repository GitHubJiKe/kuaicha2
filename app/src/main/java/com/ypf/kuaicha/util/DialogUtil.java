package com.ypf.kuaicha.util;

import android.content.Context;

import com.ypf.kuaicha.dialog.TipDialog;
/**
 * @author WangChunliang
 * @date 2014年11月3日
 * @Description 用于弹出各种对于相应的调用类没有依赖性的Dialog
 */
public class DialogUtil {







	/**
	 * 显示一个提示框 两个按钮的 点击确定或者取消
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            提示内容
	 * @param onDialogBtnClickListener
	 *            按钮点击监听
	 */
	public static void showTipDialog(Context context, String title, String content,
			TipDialog.OnDialogBtnClickListener onDialogBtnClickListener) {
		if (null == context) {
			return;
		}
		final TipDialog tipDialog = new TipDialog(context);
		tipDialog.setTitle(title);
		tipDialog.setContent(content);
		tipDialog.setOnBtnClickListener(onDialogBtnClickListener);
		try {
			tipDialog.show();
		} catch (Exception e) {
			// android.view.WindowManager$BadTokenException:Unable to add window
			// --
			// token android.os.BinderProxy@491c67f is not valid; is your
			// activity running?
			e.printStackTrace();
		}
	}

	/**
	 * 显示一个提示框 两个按钮的 点击确定或者取消
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            提示内容
	 * @param leftBtnText
	 *            取消按钮的文字
	 * @param rightBtnText
	 *            确定按钮的文字
	 * @param onDialogBtnClickListener
	 *            按钮点击监听
	 */
	public static void showTipDialog(Context context, String title, String content, String leftBtnText,
			String rightBtnText, TipDialog.OnDialogBtnClickListener onDialogBtnClickListener) {
	    if(context==null){
            return;
        }
		final TipDialog tipDialog = new TipDialog(context);
		tipDialog.setTitle(title);
		tipDialog.setContent(content);
		tipDialog.setLeftBtnText(leftBtnText);
		tipDialog.setRightBtnText(rightBtnText);
		tipDialog.setOnBtnClickListener(onDialogBtnClickListener);
		try {
			tipDialog.show();
		} catch (Exception e) {
			// android.view.WindowManager$BadTokenException:Unable to add window
			// --
			// token android.os.BinderProxy@491c67f is not valid; is your
			// activity running?
			e.printStackTrace();
		}
	}

	/**
	 * 显示一个提示框 两个按钮的 点击确定或者取消，没有title
	 * 
	 * @param content
	 *            提示内容
	 * @param leftBtnText
	 *            取消按钮的文字
	 * @param rightBtnText
	 *            确定按钮的文字
	 * @param onDialogBtnClickListener
	 *            按钮点击监听
	 */
	public static void showTipDialogNoTitle(Context context, String content, String leftBtnText, String rightBtnText,
			TipDialog.OnDialogBtnClickListener onDialogBtnClickListener) {
	    if(context==null){
            return;
        }
		final TipDialog tipDialog = new TipDialog(context);
		tipDialog.hideTitle();
		tipDialog.setContent(content);
		tipDialog.setLeftBtnText(leftBtnText);
		tipDialog.setRightBtnText(rightBtnText);
		tipDialog.setOnBtnClickListener(onDialogBtnClickListener);
		try {
			tipDialog.show();
		} catch (Exception e) {
			// android.view.WindowManager$BadTokenException:Unable to add window
			// --
			// token android.os.BinderProxy@491c67f is not valid; is your
			// activity running?
			e.printStackTrace();
		}
	}
}
