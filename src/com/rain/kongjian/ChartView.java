package com.rain.kongjian;

import com.rain.db.Constant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ChartView extends View{
    public int XPoint=40;    //ԭ���X����
    public int YPoint=Constant.point.y/2;     //ԭ���Y����
    public int XScale=55;     //X�Ŀ̶ȳ���
    public int YScale=40;     //Y�Ŀ̶ȳ���
    public int XLength=Constant.point.x-100;        //X��ĳ���
    public int YLength=Constant.point.y/2-100;        //Y��ĳ���
    public String[] XLabel;    //X�Ŀ̶�
    public String[] YLabel;    //Y�Ŀ̶�
    public String[] Data;      //����
    public String Title;    //��ʾ�ı���
    public ChartView(Context context)
    {
        super(context);
    }
    public void SetInfo(String[] XLabels,String[] YLabels,String[] AllData,String strTitle)
    {
        XLabel=XLabels;
        YLabel=YLabels;
        Data=AllData;
        Title=strTitle;
        XScale=XLength/AllData.length;//ʵ��X�Ŀ̶ȳ���
        YScale=YLength/YLabels.length;
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);//��дonDraw����

        //canvas.drawColor(Color.WHITE);//���ñ�����ɫ
        Paint paint= new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//ȥ���
        paint.setColor(Color.BLACK);//��ɫ
        Paint paint1=new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);//ȥ���
        paint1.setColor(Color.DKGRAY);
        paint.setTextSize(12);  //���������ִ�С
        //����Y��
        canvas.drawLine(XPoint, YPoint-YLength, XPoint, YPoint, paint);   //����
        for(int i=0;i*YScale<YLength ;i++)               
        {
            canvas.drawLine(XPoint,YPoint-i*YScale, XPoint+5, YPoint-i*YScale, paint);  //�̶�
            try
            {
                canvas.drawText(YLabel[i] , XPoint-30, YPoint-i*YScale+5, paint);  //����
            }
            catch(Exception e)
            {
            }
        }
        canvas.drawLine(XPoint,YPoint-YLength,XPoint-3,YPoint-YLength+6,paint);  //��ͷ
        canvas.drawLine(XPoint,YPoint-YLength,XPoint+3,YPoint-YLength+6,paint);
        //��������Ĵ�С�Ƕȵ�
        paint.setTextSize(20);
        drawText(canvas,"��λ:kWh", XPoint-5, YPoint-YLength+YScale-5, paint,-90);
        
        //����X��
        paint.setTextSize(12);
        canvas.drawLine(XPoint,YPoint,XPoint+XLength,YPoint,paint);   //����
        for(int i=0;i*XScale<XLength;i++)   
        {
            canvas.drawLine(XPoint+i*XScale, YPoint, XPoint+i*XScale, YPoint-5, paint);  //�̶�
            try
            {
//				canvas.drawText(XLabel[i], XPoint + i * XScale - 10,
//						YPoint + 20, paint); // ����
				drawText(canvas,XLabel[i], XPoint + i * XScale,
						YPoint + 40, paint,-45); // ����
				// ����ֵ
				if (i > 0 && YCoord(Data[i - 1]) != -999
						&& YCoord(Data[i]) != -999) // ��֤��Ч����
					canvas.drawLine(XPoint + (i - 1) * XScale,
							YCoord(Data[i - 1]), XPoint + i * XScale,
							YCoord(Data[i]), paint);
				canvas.drawCircle(XPoint + i * XScale, YCoord(Data[i]), 2,
						paint);
			} catch (Exception e) {
			}
        }
        canvas.drawLine(XPoint+XLength,YPoint,XPoint+XLength-6,YPoint-3,paint);    //��ͷ
        canvas.drawLine(XPoint+XLength,YPoint,XPoint+XLength-6,YPoint+3,paint); 
        //���ñ���λ��
        paint.setTextSize(28);
        canvas.drawText(Title, XLength/2-28, 50, paint);
    }
    //����������ʾ����
    void drawText(Canvas canvas ,String text , float x ,float y,Paint paint ,float angle){  
        if(angle != 0){  
            canvas.rotate(angle, x, y);   
        }  
        canvas.drawText(text, x, y, paint);  
        if(angle != 0){  
            canvas.rotate(-angle, x, y);   
        }  
    } 
    
    private int YCoord(String y0)  //�������ʱ��Y���꣬������ʱ����-999
    {
        int y;
        try
        {
            y=Integer.parseInt(y0);
        }
        catch(Exception e)
        {
            return -999;    //�����򷵻�-999
        }
        try
        {
            return YPoint-y*YScale/Integer.parseInt(YLabel[1]);
        }
        catch(Exception e)
        {
        }
        return y;
    }
}
