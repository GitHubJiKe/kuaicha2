package com.ypf.kuaicha.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

    public static boolean isInstallWX() {
        return TApplication.mApi.isWXAppInstalled();
    }

    public static boolean shareToWXFriend(String imagepath, int flag) {
        File file = new File(imagepath);
        if (!file.exists()) {
            ToastUtil.showToast(R.string.sharefail);
            return false;
        }
        WXImageObject imageObject = new WXImageObject();
        imageObject.setImagePath(imagepath);
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = imageObject;
        mediaMessage.description = "img" + String.valueOf(System.currentTimeMillis());
        mediaMessage.title = "我的分享";

        Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap, 150, 120, true);
        bitmap.recycle();
        Log.d("TAG", "thumbBitmp.size = " + thumbBitmap.getByteCount() / 1024);
        mediaMessage.thumbData = BitmapUtil.bmpToByteArray(thumbBitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = mediaMessage;
        req.transaction = "img" + String.valueOf(System.currentTimeMillis());

        req.scene = flag == 1 ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        return TApplication.mApi.sendReq(req);
    }

}
