package br.gov.ma.detran.futronictech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.futronictech.Scanner;
import com.futronictech.UsbDeviceDataExchangeImpl;
import com.futronictech.ftrWsqAndroidHelper;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import br.gov.ma.detran.examespraticosmobile.R;
import br.gov.ma.detran.examespraticosmobile.modelo.AGC_Prova_Candidato;
import br.gov.ma.detran.examespraticosmobile.service.AGC_Provas_CandidatosService;
import br.gov.ma.detran.examespraticosmobile.util.MensagemErroUtil;


public class FtrScanUsbHostActivity extends AppCompatActivity {

    private static final int COLLECTION_TIME = 3000;
    /**
     * Called when the activity is first created.
     */
    private static Button mButtonScan;
    private static Button mButtonStop;
    private Button mButtonSave;
    private static TextView mMessage;
    private static TextView mScannerInfo;
    private static ImageView mFingerImage;
    private CheckBox mCheckFrame;
    private CheckBox mCheckLFD;
    private CheckBox mCheckInvertImage;
    private CheckBox mCheckUsbHostMode;
    private CheckBox mCheckNFIQ;

    public static boolean mStop = false;
    public static boolean mFrame = true;
    public static boolean mLFD = true;
    public static boolean mInvertImage = false;
    public static boolean mNFIQ = true;

    public static final int MESSAGE_SHOW_MSG = 1;
    public static final int MESSAGE_SHOW_SCANNER_INFO = 2;
    public static final int MESSAGE_SHOW_IMAGE = 3;
    public static final int MESSAGE_ERROR = 4;
    public static final int MESSAGE_TRACE = 5;

    public static byte[] mImageFP = null;
    public static Object mSyncObj = new Object();

    public static int mImageWidth = 0;
    public static int mImageHeight = 0;
    private static int[] mPixels = null;
    private static Bitmap mBitmapFP = null;
    private static Canvas mCanvas = null;
    private static Paint mPaint = null;

    private SoundPool soundPool;
    private int soundID = 0;

    private FPScan mFPScan = null;
    //
    public static boolean mUsbHostMode = true;

    // Intent request codes
    private static final int REQUEST_FILE_FORMAT = 1;
    private UsbDeviceDataExchangeImpl usb_host_ctx = null;
    private AGC_Prova_Candidato agcProvaCandidato;

    private boolean showConfig = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftr_scan_usb_host);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mFrame = true;
        mUsbHostMode = true;
        mInvertImage = false;
        mButtonScan = (Button) findViewById(R.id.btnScan);
        mButtonStop = (Button) findViewById(R.id.btnStop);
        mButtonSave = (Button) findViewById(R.id.btnSave);
        mMessage = (TextView) findViewById(R.id.tvMessage);
        mScannerInfo = (TextView) findViewById(R.id.tvScannerInfo);
        mFingerImage = (ImageView) findViewById(R.id.imageFinger);
        mCheckFrame = (CheckBox) findViewById(R.id.cbFrame);
        mCheckLFD = (CheckBox) findViewById(R.id.cbLFD);
        mCheckInvertImage = (CheckBox) findViewById(R.id.cbInvertImage);
        mCheckUsbHostMode = (CheckBox) findViewById(R.id.cbUsbHostMode);
        mCheckNFIQ = (CheckBox) findViewById(R.id.cbNFIQ);

        usb_host_ctx = new UsbDeviceDataExchangeImpl(this, mHandler);

        agcProvaCandidato = (AGC_Prova_Candidato) getIntent().getSerializableExtra("candidato");
        if (agcProvaCandidato.getImagemDigital() != null) {
            MyBitmapFile digital = new MyBitmapFile(320, 480, agcProvaCandidato.getImagemDigital());
            // mFingerImage.setImageBitmap(BitmapFactory.decodeByteArray(digital.toBytes(), 0, digital.toBytes().length));
            mFingerImage.setImageBitmap(BitmapFactory.decodeByteArray(agcProvaCandidato.getImagemDigital(), 0, agcProvaCandidato.getImagemDigital().length));
        }

        mButtonScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iniciar();
            }
        });

        mButtonStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               parar();
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvar();
            }
        });


        mCheckFrame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked())
                    mFrame = true;
                else {
                    mFrame = false;
                    mCheckLFD.setChecked(false);
                    mLFD = false;
                }
            }
        });


        mCheckLFD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked())
                    mLFD = true;
                else
                    mLFD = false;
            }
        });

        mCheckInvertImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked())
                    mInvertImage = true;
                else
                    mInvertImage = false;
            }
        });

        mCheckUsbHostMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked())
                    mUsbHostMode = true;
                else
                    mUsbHostMode = false;
            }
        });

        mCheckNFIQ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked())
                    mNFIQ = true;
                else
                    mNFIQ = false;
            }
        });

        //ShowBitmapNovo();
        loadSound();
        mostrarConfigs(showConfig);
        iniciar();
    }

    private void mostrarConfigs(boolean showConfig) {
        if (!showConfig) {
            mCheckFrame.setVisibility(View.GONE);
            mCheckInvertImage.setVisibility(View.GONE);
            mCheckLFD.setVisibility(View.GONE);
            mCheckNFIQ.setVisibility(View.GONE);
            mCheckUsbHostMode.setVisibility(View.GONE);
            mButtonSave.setVisibility(View.GONE);
            mButtonScan.setVisibility(View.GONE);
            mButtonStop.setVisibility(View.GONE);
            mScannerInfo.setVisibility(View.GONE);
        }else {
            mCheckFrame.setVisibility(View.VISIBLE);
            mCheckInvertImage.setVisibility(View.VISIBLE);
            mCheckLFD.setVisibility(View.VISIBLE);
            mCheckNFIQ.setVisibility(View.VISIBLE);
            mCheckUsbHostMode.setVisibility(View.VISIBLE);
            mButtonSave.setVisibility(View.VISIBLE);
            mButtonScan.setVisibility(View.VISIBLE);
            mButtonStop.setVisibility(View.VISIBLE);
            mScannerInfo.setVisibility(View.VISIBLE);

        }
    }


    public static void InitFingerPictureParameters(int wight, int height) {
        mImageWidth = wight;
        mImageHeight = height;

        mImageFP = new byte[FtrScanUsbHostActivity.mImageWidth * FtrScanUsbHostActivity.mImageHeight];
        mPixels = new int[FtrScanUsbHostActivity.mImageWidth * FtrScanUsbHostActivity.mImageHeight];

        mBitmapFP = Bitmap.createBitmap(wight, height, Bitmap.Config.RGB_565);

        mCanvas = new Canvas(mBitmapFP);
        mPaint = new Paint();

        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        mPaint.setColorFilter(f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStop = true;
        if (mFPScan != null) {
            mFPScan.stop();
            mFPScan = null;
        }
        usb_host_ctx.CloseDevice();
        usb_host_ctx.Destroy();
        usb_host_ctx = null;
    }

    private boolean StartScan() {
        mFPScan = new FPScan(usb_host_ctx, mHandler);
        mFPScan.start();
        return true;
    }

    private void SaveImage() {
        Intent serverIntent = new Intent(this, SelectFileFormatActivity.class);
        serverIntent.putExtra("cpf", agcProvaCandidato.getCpfCandidato().trim());
        startActivityForResult(serverIntent, REQUEST_FILE_FORMAT);
    }

    private void SaveImageByFileFormat(String fileFormat, String fileName) {
        if (fileFormat.compareTo("WSQ") == 0)    //save wsq file
        {
            Scanner devScan = new Scanner();
            devScan.SetDiodesStatus(1, 1);
            boolean bRet;
            if (mUsbHostMode)
                bRet = devScan.OpenDeviceOnInterfaceUsbHost(usb_host_ctx);

            else
                bRet = devScan.OpenDevice();
            if (!bRet) {
                mMessage.setText(devScan.GetErrorMessage());
                return;
            }
            byte[] wsqImg = new byte[mImageWidth * mImageHeight];

            long hDevice = devScan.GetDeviceHandle();
            ftrWsqAndroidHelper wsqHelper = new ftrWsqAndroidHelper();
            if (wsqHelper.ConvertRawToWsq(hDevice, mImageWidth, mImageHeight, 2.25f, mImageFP, wsqImg)) {
                File file = new File(fileName);
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(wsqImg, 0, wsqHelper.mWSQ_size);    // save the wsq_size bytes data to file
                    agcProvaCandidato.setImagemDigital(wsqImg);
                    new AGC_Provas_CandidatosService().inserirDigital(agcProvaCandidato.getImagemDigital(), agcProvaCandidato.getId().toString(), this);
                    result();
                    out.close();
                    playSound();
                    MensagemErroUtil.mostrar("Biometria capturada.", this);
                    mMessage.setText("Image is saved as " + fileName);
                } catch (Exception e) {
                    mMessage.setText("Erro ao salvar a digital");
                }

            } else
                mMessage.setText("Failed to convert the image!");
            if (mUsbHostMode)
                devScan.CloseDeviceUsbHost();
            else
                devScan.CloseDevice();
            return;
        }
        // 0 - save bitmap file
        File file = new File(fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            //mBitmapFP.compress(Bitmap.CompressFormat.PNG, 90, out);
            MyBitmapFile fileBMP = new MyBitmapFile(mImageWidth, mImageHeight, mImageFP);
            out.write(fileBMP.toBytes());
            out.close();

            mMessage.setText("Image is saved as " + fileName);
        } catch (Exception e) {
            mMessage.setText("Exception in saving file");
        }


    }

    private void result() {
        Intent intent = new Intent();
        intent.putExtra("dig", agcProvaCandidato.getImagemDigital());
        // Set result and finish this Activity
        setResult(Activity.RESULT_OK, intent);
    }

    // The Handler that gets information back from the FPScan
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SHOW_MSG:
                    String showMsg = (String) msg.obj;
                    //showMsg = showMsg.replaceAll("\\D+","");
                   // showMsg = showMsg.substring(0,showMsg.trim().length()-1);
                   // if (showMsg.contains("OK.")) {
                    if (showMsg.matches("[0-9]+")) {
                     // String time =   showMsg.replaceAll("\\D+","");
                      //time = time.substring(0,time.trim().length()-1);
                        if (Integer.parseInt(showMsg) > COLLECTION_TIME) {
                            mMessage.setText(showMsg);
                            parar();
                            salvar();
                        }
                    }
                    if(showMsg.equalsIgnoreCase("Error code is 87"))
                        mMessage.setText("O scanner foi desconectado. Reconecte-o.");
                    else
                        mMessage.setText(showMsg);
                    break;
                case MESSAGE_SHOW_SCANNER_INFO:
                    String showInfo = (String) msg.obj;
                    mScannerInfo.setText(showInfo);
                    break;
                case MESSAGE_SHOW_IMAGE:
                    ShowBitmap();
                    break;
                case MESSAGE_ERROR:
                    //mFPScan = null;
                    mButtonScan.setEnabled(true);
                    mCheckUsbHostMode.setEnabled(true);
                    mButtonStop.setEnabled(false);
                    break;
                case UsbDeviceDataExchangeImpl.MESSAGE_ALLOW_DEVICE:
                    if (usb_host_ctx.ValidateContext()) {
                        if (StartScan()) {
                            mButtonScan.setEnabled(false);
                            mButtonSave.setEnabled(false);
                            mCheckUsbHostMode.setEnabled(false);
                            mButtonStop.setEnabled(true);
                        }
                    } else
                        mMessage.setText("Can't open scanner device");
                    break;
                case UsbDeviceDataExchangeImpl.MESSAGE_DENY_DEVICE:
                    mMessage.setText("User deny scanner device");
                    break;
            }
        }
    };

    private static void ShowBitmap() {
        for (int i = 0; i < mImageWidth * mImageHeight; i++) {
            mPixels[i] = Color.rgb(mImageFP[i], mImageFP[i], mImageFP[i]);
        }

        mCanvas.drawBitmap(mPixels, 0, mImageWidth, 0, 0, mImageWidth, mImageHeight, false, mPaint);

        mFingerImage.setImageBitmap(mBitmapFP);
        mFingerImage.invalidate();

        synchronized (mSyncObj) {
            mSyncObj.notifyAll();
        }
    }

    private static void ShowBitmapNovo(){


       /* //File extStorageDirectory = Environment.getExternalStorageDirectory();
        File digital1 = new File("/storage/emulated/0/Android/FtrScan/61165965330.wsq");
        File digital = new File("/storage/emulated/0/Android/FtrScanDemo/1234.bmp");
        WsqDecoder decoder = new WsqDecoder();
        //decoder.decode(digital);
        ImageUtils imageUtils = new ImageUtils();
        try {
            byte[] bytes = imageUtils.bitmap2png(decoder.decode(digital1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(digital.getAbsolutePath(),bmOptions);
        //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
        mFingerImage.setImageBitmap(bitmap);*/

    //    File digital1 = new File("/storage/emulated/0/Android/FtrScan/61165965330.wsq");
        //byte[] gifBytes = Jnbis.wsq().decode(digital1).toGif().asByteArray();
     //   org.jnbis.api.model.Bitmap bmp = Jnbis.wsq().decode(digital1).asBitmap();
      ///  Bitmap bitmap = BitmapFactory.decodeByteArray(bmp.getPixels(), 0, bmp.getLength());
     //   mFingerImage.setImageBitmap(bitmap);
      /*  int[] pixels = new int[mImageWidth * mImageHeight];
        for (int i = 0; i < mImageWidth * mImageHeight; i++)
            pixels[i] = mImageFP[i];
        Bitmap emptyBmp = Bitmap.createBitmap(pixels, mImageWidth,
                mImageHeight, Bitmap.Config.RGB_565);

        int width, height;
        height = emptyBmp.getHeight();
        width = emptyBmp.getWidth();

        mBitmapFP = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(mBitmapFP);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(emptyBmp, 0, 0, paint);

        mFingerImage.setImageBitmap(mBitmapFP);*/


        byte[] probeImage = new byte[0];
        byte[] candidateImage =  new byte[0];
        try {

            File digital1 = new File("/storage/emulated/0/Android/FtrScan/61165965330.wsq");
            byte[] bytesArray = new byte[(int) digital1.length()];
            FileInputStream fis = new FileInputStream(digital1);
           /* ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream();
                probeImage = Files.readAllBytes(Paths.get());
                candidateImage = Files.readAllBytes(Paths.get("/storage/emulated/0/Android/FtrScan/611659653300.wsq"));*/


        } catch (IOException e) {
            e.printStackTrace();
        }


        FingerprintTemplate probe = new FingerprintTemplate()
                .dpi(500)
                .create(probeImage);

        FingerprintTemplate candidate = new FingerprintTemplate()
                .dpi(500)
                .create(candidateImage);
        double score = new FingerprintMatcher()
                .index(probe)
                .match(candidate);

        double threshold = 40;
        boolean matches = score >= threshold;
        System.out.println(matches);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_FILE_FORMAT:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the file format
                    String[] extraString = data.getExtras().getStringArray(SelectFileFormatActivity.EXTRA_FILE_FORMAT);
                    String fileFormat = extraString[0];
                    String fileName = extraString[1];
                    SaveImageByFileFormat(fileFormat, fileName);
                } else
                    mMessage.setText("Cancelled!");
                break;
        }
    }

    private void pararCaptura() {
        mStop = true;

        if (mFPScan != null) {
            mFPScan.stop();
            mFPScan = null;
        }
        String msg = mMessage.getText().toString();
        mMessage.setText(msg);
        if (msg.matches("[0-9]+")) {
            if (Integer.parseInt(msg) > COLLECTION_TIME) {

                Toast.makeText(this, "Digital capturada", Toast.LENGTH_LONG).show();
                // digitalCount2 = 1;
                // capturarDigital("2");
                //playSound();
                // imagem de mão direita com indicador marcado
                // showPopUpDialog(R.style.popup_theme2);
                SaveImage();

            } else {
                if (msg.equalsIgnoreCase("Error code is 87")) {
                    mMessage.setText("O coletor foi desconectado. Por favor, reconecte-o.");
                }
            }

        }
    }

    private void iniciar(){
        if (mFPScan != null) {
            mStop = true;
            mFPScan.stop();

        }
        mStop = false;
        if (mUsbHostMode) {
            usb_host_ctx.CloseDevice();
            if (usb_host_ctx.OpenDevice(0, true)) {
                if (StartScan()) {
                    mButtonScan.setEnabled(false);
                    mButtonSave.setEnabled(false);
                    mCheckUsbHostMode.setEnabled(false);
                    mButtonStop.setEnabled(true);
                }
            } else {
                if (!usb_host_ctx.IsPendingOpen()) {
                    mMessage.setText("Can not start scan operation.\nCan't open scanner device");
                }
            }
        } else {
            if (StartScan()) {
                mButtonScan.setEnabled(false);
                mButtonSave.setEnabled(false);
                mCheckUsbHostMode.setEnabled(false);
                mButtonStop.setEnabled(true);
            }
        }
    }

    private void parar(){
        mStop = true;
        if (mFPScan != null) {
            mFPScan.stop();
            mFPScan = null;

        }
        mButtonScan.setEnabled(true);
        mButtonSave.setEnabled(true);
        mCheckUsbHostMode.setEnabled(true);
        mButtonStop.setEnabled(false);
    }

    private void salvar() {
        if (mImageFP != null)
            SaveImage();
    }
    private void loadSound() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);
        soundID = soundPool.load(getApplicationContext(),
                R.raw.bip_coletado_duplo, 1);

    }

    private void playSound() {
        if (soundID != 0) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            float curVolume = audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float leftVolume = (float) 0.2;
            float rightVolume = (float) 0.2;
            int priority = 1;
            int no_loop = 0;
            float normal_playback_rate = 1f;

            soundPool.play(soundID, leftVolume, rightVolume, priority, no_loop,
                    normal_playback_rate);
        }

    }
}


