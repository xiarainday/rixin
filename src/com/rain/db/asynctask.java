package com.rain.db;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class asynctask extends AsyncTask<String, Integer, String> {

	String info;
//	Handler handler;
	//String url;
		
	//doInBackground方法内部执行后台任务,不可在此方法内修改UI 
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
        String list = getURLResponse(params[0]); 
		return list;
	}
		    		
    //doInBackground方法的返回值被当作参数给onPostExecute
	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        if (a) {
//			return;
//		}
        Message msg =  Message.obtain();
		msg.obj = result;
        if(!(result.equals(null)||result.equals("")||(result==null)||(result==""))){
            msg.what = 1;
            msg.obj = result;
        }else{
            msg.what = 2;
        }
        Constant.handler.sendMessage(msg);
    }

	/**
	 * 获取指定URL的响应字符串
	 * 
	 * @param urlString
	 * @return
	 */
	private String getURLResponse(String urlString) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(urlString);
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			
//			if(a){//更新数据不执行下面的代码
//				return null;
//			}

			// 获取网页中内容的字节数
			long totalLength = entity.getContentLength();
			InputStream is = entity.getContent();

			String s = null;
			if (is != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buf = new byte[128];
				int ch = -1;
				int count = 0;
				while ((ch = is.read(buf)) != -1) {
					baos.write(buf, 0, ch);
					count += ch;
					if (totalLength > 0) {
						// 如果知道响应的长度，调用publishProgress（）更新进度
						publishProgress((int) ((count / (float) totalLength) * 100));
					}
					// 让线程休眠100ms
					Thread.sleep(100);
				}
				s = new String(baos.toByteArray());
			}
			return s;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
		return null;				
	}

}
