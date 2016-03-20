package com.ypf.kuaicha.util;

import android.graphics.Bitmap;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;

import java.io.File;

/**
 * Created by Administrator on 2016/3/15.
 */
public class ShareUtil {
    public static boolean shareToWXFriend(String imagepath) {
        File file = new File(imagepath);
        if (!file.exists()) {
            ToastUtil.showToast(R.string.nopic);
            return false;
        }
        WXImageObject wxImageObject = new WXImageObject();
        wxImageObject.setImagePath(imagepath);

        WXMediaMessage wxMediaMessage = new WXMediaMessage();
        wxMediaMessage.mediaObject = wxImageObject;
        wxMediaMessage.description = StringUtil.getString(R.string.myinfor);

        Bitmap bitmap = BitmapUtil.getBitmapFromDisk(imagepath);
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap, 120, 150, true);
        wxMediaMessage.thumbData = BitmapUtil.bmpToByteArray(thumbBitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = wxMediaMessage;
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        return TApplication.mApi.sendReq(req);
    }

}
