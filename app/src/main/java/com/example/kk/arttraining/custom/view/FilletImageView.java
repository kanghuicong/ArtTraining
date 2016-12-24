package com.example.kk.arttraining.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.kk.arttraining.R;

import java.lang.ref.WeakReference;

/**
 * 图片类型
 * 0 圆形
 * 1 圆角
 */
public class FilletImageView extends ImageView{

    private Context mContext;

    private Paint mPaint;//画笔
    //覆盖类型
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;
    //弱引用
    private WeakReference<Bitmap> mWeakBitmap;

    /**
     * 图片类型
     * 0 圆形
     * 1 圆角
     */
    private int type;
    private final static int TYPE_CIRCLE=0;
    private final static int TYPE_ROUND=1;

    //默认圆角大小
    private static final int BORDER_RADIUS_DEFAULT = 10;
    private int mBorderRadius;


    public FilletImageView(Context context) {
        super(context);
        init();
    }

    public FilletImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        //属性
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.FilletImageView);
        mBorderRadius=array.getDimensionPixelSize(R.styleable.FilletImageView_border_radius,10);
        type=array.getInt(R.styleable.FilletImageView_type,TYPE_CIRCLE);

        array.recycle();//回收
    }

    public FilletImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        //则将高宽设置成相等
//        if(type==TYPE_CIRCLE){
////            取小的那个
//            int width = Math.min(getMeasuredWidth(),getMeasuredHeight());
////            setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
//            setMeasuredDimension(width,width);
//
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);不能调用这个家伙。。


        //从缓存中获取图片
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
        //如果没有图片
        if(bitmap==null || bitmap.isRecycled()){

            //从drawable中获取图片
            Drawable drawable=getDrawable();
            if(drawable!=null) {

                int dWidth = drawable.getIntrinsicWidth();
                int dHeight = drawable.getIntrinsicHeight();

                //创建bitmap
                bitmap=Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
                //创建画布
                Canvas drawCanvas=new Canvas(bitmap);

                float scale=1.0f;//缩放比例
                //根据图片高宽dWidth,dHeight和控件高宽getWidth,getHeight计算缩放比例，去大值
                if(type==TYPE_CIRCLE){
                    scale=(getWidth()*1.0f)/Math.min(dWidth,dHeight);
                }else{
                    //图片宽高要大于控件宽高，这样才能使图片填充控件，所以取比例大的
                    scale=Math.max(getWidth()*1.0f/dWidth,getHeight()*1.0f/dHeight);
                }

                //根据缩放比例，设置bounds，相当于缩放图片了
                drawable.setBounds(0, 0, (int) (scale * dWidth), (int) (scale * dHeight));
                drawable.draw(drawCanvas);

                //覆盖圆角的bitmap
                if(mMaskBitmap==null || mMaskBitmap.isRecycled()){
                    mMaskBitmap=getBitmap();
                }

                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);

                //绘制形状
                drawCanvas.drawBitmap(mMaskBitmap,0,0,mPaint);
                mPaint.setXfermode(null);
                //绘制图片
                canvas.drawBitmap(bitmap,0,0,mPaint);
                //将bitmap缓存起来
                mWeakBitmap=new WeakReference<Bitmap>(bitmap);
                bitmap.recycle();

            }
        }else {//bitmap存在
            mPaint.setXfermode(null);
            canvas.drawBitmap(bitmap,0,0,mPaint);

        }

    }

    @Override
    public void invalidate() {
        //重绘的时候需要将性质图片回收，否则变换图形无效
        if(mMaskBitmap!=null){
            mMaskBitmap.recycle();
            mMaskBitmap=null;
        }
        super.invalidate();
    }

    //创建一个绘制形状的bitmap，圆形或者圆角矩形
    private Bitmap getBitmap(){
        Bitmap bitmap=Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        if(type==TYPE_CIRCLE){//画圆形
            canvas.drawCircle(getWidth()/2,getWidth()/2,getWidth()/2,paint);
        }else{//画圆角矩形
            canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()),mBorderRadius,mBorderRadius,paint);
        }

        return bitmap;
    }
}
