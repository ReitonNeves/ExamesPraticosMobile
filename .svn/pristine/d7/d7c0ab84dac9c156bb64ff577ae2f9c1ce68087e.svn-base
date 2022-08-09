package br.gov.ma.detran.futronictech;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import com.futronictech.Scanner;
import com.futronictech.UsbDeviceDataExchangeImpl;

public class FPScan {
    private final Handler mHandler;
    private ScanThread mScanThread;
    private UsbDeviceDataExchangeImpl ctx = null;

    private  long timebegin = 0;
    private final int DIODE_GREEN = 1;
    private final int DIODE_RED = 2;
    private int CURRENT_DIODE = DIODE_RED;



    
    public FPScan(UsbDeviceDataExchangeImpl context, Handler handler ) {
        mHandler = handler;
        ctx = context;
    }

    public synchronized void start() {
        if (mScanThread == null) {
        	mScanThread = new ScanThread();
        	mScanThread.start();
        }
    }
    
    public synchronized void stop() {
        if (mScanThread != null) {mScanThread.cancel(); mScanThread = null;}
    }
    
    private class ScanThread extends Thread {
    	private boolean bGetInfo;
    	private Scanner devScan = null;
    	private String strInfo;
    	private int mask, flag;
    	private int errCode;
    	private boolean bRet;
		private int nNfiq = 0;
    	
        public ScanThread() {
        	bGetInfo=false;
        	devScan = new Scanner();
        	/******************************************
        	// By default, a directory of "/mnt/sdcard/Android/" is necessary for libftrScanAPI.so to work properly
        	// in case you want to change it, you can set it by calling the below function
        	String SyncDir =  "/mnt/sdcard/test/";  // YOUR DIRECTORY
        	if( !devScan.SetGlobalSyncDir(SyncDir) )
        	{
                mHandler.obtainMessage(FtrScanDemoActivity.MESSAGE_SHOW_MSG, -1, -1, devScan.GetErrorMessage()).sendToTarget();
                mHandler.obtainMessage(FtrScanDemoActivity.MESSAGE_ERROR).sendToTarget();
           	    devScan = null;
	            return;
        	}
        	*******************************************/
        }

        public void run() {
            timebegin = SystemClock.uptimeMillis();
            while (!FtrScanUsbHostActivity.mStop)
            {
            	if(!bGetInfo)
            	{
            		Log.i("FUTRONIC", "Run fp scan");
            		boolean bRet;
         	        if( FtrScanUsbHostActivity.mUsbHostMode )
         	        	bRet = devScan.OpenDeviceOnInterfaceUsbHost(ctx);
         	        else
         	        	bRet = devScan.OpenDevice();
                    if( !bRet )
                    {
                        //Toast.makeText(this, strInfo, Toast.LENGTH_LONG).show();
                    	if(FtrScanUsbHostActivity.mUsbHostMode)
                    		ctx.CloseDevice();
                        mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_SHOW_MSG, -1, -1, devScan.GetErrorMessage()).sendToTarget();
                        mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_ERROR).sendToTarget();
                        return;
                    }
            		
            		if( !devScan.GetImageSize() )
	    	        {
	    	        	mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_SHOW_MSG, -1, -1, devScan.GetErrorMessage()).sendToTarget();
	    	        	if( FtrScanUsbHostActivity.mUsbHostMode )
	    	        		devScan.CloseDeviceUsbHost();
	    	        	else
	    	        		devScan.CloseDevice();
                        mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_ERROR).sendToTarget();
	    	            return;
	    	        }
					
	    	        FtrScanUsbHostActivity.InitFingerPictureParameters(devScan.GetImageWidth(), devScan.GetImaegHeight());
					
	    	        strInfo = devScan.GetVersionInfo();
    	        	mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_SHOW_SCANNER_INFO, -1, -1, strInfo).sendToTarget();
	    	        bGetInfo = true;            	
             	}
                //set options
                flag = 0;
                mask = devScan.FTR_OPTIONS_DETECT_FAKE_FINGER | devScan.FTR_OPTIONS_INVERT_IMAGE;
                if(FtrScanUsbHostActivity.mLFD)
                	flag |= devScan.FTR_OPTIONS_DETECT_FAKE_FINGER;
                if( FtrScanUsbHostActivity.mInvertImage)
                	flag |= devScan.FTR_OPTIONS_INVERT_IMAGE;                
                if( !devScan.SetOptions(mask, flag) )
    	        	mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_SHOW_MSG, -1, -1, devScan.GetErrorMessage()).sendToTarget();
                // get frame / image2
                long lT1 = SystemClock.uptimeMillis();
                if( FtrScanUsbHostActivity.mFrame )
                	bRet = devScan.GetFrame(FtrScanUsbHostActivity.mImageFP);
                else
                	bRet = devScan.GetImage2(4,FtrScanUsbHostActivity.mImageFP);
                if( !bRet )
                {
                    CURRENT_DIODE = DIODE_RED; //Lógica pata o LED do scanner
                	mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_SHOW_MSG, -1, -1, devScan.GetErrorMessage()).sendToTarget();
                	errCode = devScan.GetErrorCode();
                	if( errCode != devScan.FTR_ERROR_EMPTY_FRAME && errCode != devScan.FTR_ERROR_MOVABLE_FINGER &&  errCode != devScan.FTR_ERROR_NO_FRAME )
                	{
	    	        	if( FtrScanUsbHostActivity.mUsbHostMode )
	    	        		devScan.CloseDeviceUsbHost();
	    	        	else
	    	        		devScan.CloseDevice();
                        mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_ERROR).sendToTarget();
	    	            return;                		
                	}    	        	
                }
                else
                {
                    CURRENT_DIODE = DIODE_GREEN; //Lógica pata o LED do scanner
					if( FtrScanUsbHostActivity.mNFIQ )
                	{
	                	if( devScan.GetNfiqFromImage(FtrScanUsbHostActivity.mImageFP, FtrScanUsbHostActivity.mImageWidth, FtrScanUsbHostActivity.mImageHeight))
	                		nNfiq = devScan.GetNIFQValue();
                	}				
                	if( FtrScanUsbHostActivity.mFrame )
                		//strInfo = String.format("OK. GetFrame time is %d(ms)",  SystemClock.uptimeMillis()-lT1);
                		strInfo = String.format("%d",  SystemClock.uptimeMillis()-timebegin);
                	else
                		strInfo = String.format("OK. GetImage2 time is %d(ms)",  SystemClock.uptimeMillis()-lT1);
                	if( FtrScanUsbHostActivity.mNFIQ )
                	{
                		//strInfo = strInfo + String.format("NFIQ=%d", nNfiq);
                	}						
                	mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_SHOW_MSG, -1, -1, strInfo ).sendToTarget();
                }
				synchronized (FtrScanUsbHostActivity.mSyncObj)
                {
					//show image
					mHandler.obtainMessage(FtrScanUsbHostActivity.MESSAGE_SHOW_IMAGE).sendToTarget();
                    //Lógica pata o LED do scanner
					if(CURRENT_DIODE == DIODE_GREEN)
					    devScan.SetDiodesStatus(255,0);
					else
					    devScan.SetDiodesStatus(0,255);
					try {
						FtrScanUsbHostActivity.mSyncObj.wait(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
            //close device
        	if( FtrScanUsbHostActivity.mUsbHostMode ) {
                devScan.SetDiodesStatus(0, 0);
                devScan.CloseDeviceUsbHost();
            }else {
                devScan.CloseDevice();
            }
        }

        public void cancel() {
        	FtrScanUsbHostActivity.mStop = true;
        	try {
        		synchronized (FtrScanUsbHostActivity.mSyncObj)
		        {
        			FtrScanUsbHostActivity.mSyncObj.notifyAll();
		        }        		
        		this.join();	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	      	           	
        }
    }    
}
