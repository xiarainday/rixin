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
		
	//doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI 
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
        String list = getURLResponse(params[0]); 
		return list;
	}
		    		
    //doInBackground�����ķ���ֵ������������onPostExecute
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
	 * ��ȡָ��URL����Ӧ�ַ���
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
			
//			if(a){//�������ݲ�ִ������Ĵ���
//				return null;
//			}

			// ��ȡ��ҳ�����ݵ��ֽ���
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
						// ���֪����Ӧ�ĳ��ȣ�����publishProgress�������½���
						publishProgress((int) ((count / (float) totalLength) * 100));
					}
					// ���߳�����100ms
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
