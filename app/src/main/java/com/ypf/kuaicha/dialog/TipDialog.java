package com.ypf.kuaicha.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ypf.kuaicha.R;

/**
 * @Description 多米的提示Dialog 两个按钮的
 */
public class TipDialog extends CommonDialog{
    protected OnDialogBtnClickListener mOnClickListener;//按钮点击监听
    
    private TextView txt_title;//标题
    private TextView txt_content;//提示内容
    private Button btn_cancel;//取消按钮
    private Button btn_confirm;//确认按钮

    public TipDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_tip);

        txt_title=(TextView) findViewById(R.id.txt_title);//标题
        txt_content=(TextView) findViewById(R.id.txt_content);//提示内容
        
        btn_cancel=(Button) findViewById(R.id.btn_cancel);//取消按钮
        btn_cancel.setOnClickListener(this);
        
        btn_confirm=(Button) findViewById(R.id.btn_confirm);//确认按钮
        btn_confirm.setOnClickListener(this);
    }
    /** 创建一个实例 */
    public static TipDialog newInstance(Context context){
    	return new TipDialog(context);
    }
    /** 隐藏标题 */
    public void hideTitle(){
        txt_title.setVisibility(View.GONE);
    }
    /**
     * 设置标题
     */
    public void setTitle(String title){
        txt_title.setText(title);
    }
    /**
     * 设置提示内容
     */
    public void setContent(String content) {
        txt_content.setText(content);
    }
    /**
     * 设置左边按钮文字
     */
    public void setLeftBtnText(String leftBtnText){
        btn_cancel.setText(leftBtnText);
    }
    /**
     * 设置右边按钮文字
     */
    public void setRightBtnText(String rightBtnText){
        btn_confirm.setText(rightBtnText);
    }
    /**
     * 设置按钮的监听
     */
    public void setOnBtnClickListener(OnDialogBtnClickListener onClickListener){
        this.mOnClickListener=onClickListener;
    }

    public interface OnDialogBtnClickListener{
        public void onLeftBtnClicked(TipDialog dialog);
        public void onRightBtnClicked(TipDialog dialog);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_cancel://取消
                if(null!=mOnClickListener){
                    mOnClickListener.onLeftBtnClicked(this);
                }
                break;
            case R.id.btn_confirm://确认
                if(null!=mOnClickListener){
                    mOnClickListener.onRightBtnClicked(this);
                }
                break;
            default:
                break;
        }
    }
}