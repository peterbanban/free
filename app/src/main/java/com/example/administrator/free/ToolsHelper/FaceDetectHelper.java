package com.example.administrator.free.ToolsHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.camera2.params.Face;
import android.media.FaceDetector;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.administrator.free.R;

import static android.R.attr.scaleHeight;
import static android.R.attr.scaleWidth;

/**
 * Created by Administrator on 2017/08/04.
 */

public class FaceDetectHelper extends View {
    private int imageWidth,imageHeight;
    private int numberOfFace=5;              //最大检测的人脸数
    private FaceDetector myFaceDetect;      //声明人脸检测对象
    private FaceDetector.Face[] myFace;    //存储多张人脸数组变量
    float myEyesDistance;                  //两眼之间的距离
    int numberOfFaceDetected;             //实际检测到到的人脸数
    Bitmap myBitmap;
    Context context;

    public  Bitmap getAutoSizeBMP(){    //调整图片大小适应屏幕
        BitmapFactory.Options BitmapFactoryOptionInfo=new BitmapFactory.Options();
        BitmapFactoryOptionInfo.inPreferredConfig=Bitmap.Config.RGB_565;   //构造位图生产参数必须为565
        Bitmap imageBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.detect,BitmapFactoryOptionInfo);
        imageWidth=imageBitmap.getWidth();
        imageHeight=imageBitmap.getHeight();
        DisplayMetrics dmp = context.getResources().getDisplayMetrics();  //获取屏幕像素
        int screenWidth=dmp.widthPixels;
        int screenHeight=dmp.heightPixels;
        Log.w("屏幕长度",""+screenHeight);
        float scaleWidth;
        float scaleHeight;
        if(imageWidth>imageHeight) {
            scaleWidth = 1.0f * screenWidth / imageWidth;
            scaleHeight = 1.0f * screenWidth / imageWidth;
        }
        else{
             scaleWidth=1.0f*screenWidth/imageWidth;        //不用浮点数结果可能为0,则会报错
             scaleHeight=1.0f*screenHeight/imageHeight;
        }
        Matrix matrix =new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);                  //设置缩放对象
        Bitmap bitmap= Bitmap.createBitmap(imageBitmap,0,0,imageWidth,imageHeight,matrix,true);
        return bitmap;
    }

    public  FaceDetectHelper(Context context){    //必须有的构造方法
        super(context);
        Log.d("进入到构造函数","1");
        this.context=context;
        myBitmap = getAutoSizeBMP();
        imageWidth=myBitmap.getWidth();
        imageHeight=myBitmap.getHeight();
        myFace=new FaceDetector.Face[numberOfFace];       //分配人脸数组空间
        myFaceDetect=new FaceDetector(imageWidth,imageHeight,numberOfFace);
        numberOfFaceDetected=myFaceDetect.findFaces(myBitmap,myFace);
        Log.w("检测到的人脸数",""+numberOfFaceDetected);
    }
    protected void  onDraw(Canvas canvas){         //必有的override函数 用于框出识别出来的人脸
        Log.e("进入到 onDraw函数","1");
        canvas.drawBitmap(myBitmap,0,0,null);     //画位图-
        Paint myPaint =new Paint();
        myPaint.setColor(Color.GREEN);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(3);

        for (int i =0;i<numberOfFaceDetected;i++){
            FaceDetector.Face face=myFace[i];
            PointF myMidPoint = new PointF();
            face.getMidPoint(myMidPoint);
            myEyesDistance=face.eyesDistance();     //得到人间中心点和眼间参数

            canvas.drawRect(                        //矩形框位置参数
                    (int)(myMidPoint.x-myEyesDistance),
                    (int)(myMidPoint.y-myEyesDistance),
                    (int)(myMidPoint.x+myEyesDistance),
                    (int)(myMidPoint.y+myEyesDistance),
                    myPaint);
        }
    }
}
