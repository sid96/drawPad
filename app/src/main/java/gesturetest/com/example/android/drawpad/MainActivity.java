//application created using url https://android.jlelse.eu/learn-to-create-a-paint-application-for-android-5b16968063f8

package gesturetest.com.example.android.drawpad;

import android.app.Dialog;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private PaintView paintView;
    private Button clear,undo;
    public int DefaultColor;
    private ImageButton redButton;
    private ImageButton blackButton;
    private Dialog dialog;
    public final static String APP_PATH_SD_CARD ="Drawpad";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "thumbnails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD;
        /*try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
        }*/
        Log.i("MainActivity", "onCreate: "+fullPath);
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.stroke_dialog);
        paintView = (PaintView)findViewById(R.id.paintView);
        redButton = (ImageButton)findViewById(R.id.imageButtonRed);
        blackButton=(ImageButton)findViewById(R.id.imageButtonBlack);
        redButton.setOnClickListener(this);
        blackButton.setOnClickListener(this);
        clear=(Button)findViewById(R.id.clear);
        undo=(Button)findViewById(R.id.undo);
        clear.setOnClickListener(this);
        undo.setOnClickListener(this);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        DefaultColor=paintView.currentColor;
        //paintView.normal();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.drawpad_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.color:
                //Function to open color picker
                OpenColorPickerDialog(false);
                paintView.currentColor= DefaultColor;
                return true;
            case R.id.StrokeWidth:
                final TextView width=(TextView)dialog.findViewById(R.id.strokeWidthTextview);
                SeekBar seekBar=(SeekBar)dialog.findViewById(R.id.seekBar);
                width.setText(Integer.toString(paintView.strokeWidth));
                seekBar.setMax(50);
                seekBar.setProgress(paintView.strokeWidth);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        width.setText(Integer.toString(i));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        paintView.strokeWidth=seekBar.getProgress();
                    }
                });
                Window window = dialog.getWindow();
                window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                dialog.show();
                return true;
            case R.id.export:
                //paintView.save_bitmap_to_storage();
                //paintView.strokeWidth=20;


               // paintView.normal();


            /*case R.id.close:

                System.exit(0);
                return true;

            case R.id.emboss:
                paintView.emboss();
                return true;
            case R.id.blur:
                paintView.blur();
                return true;*/
            /*case R.id.clear:
                paintView.clear();
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.clear:
                paintView.clear();
                break;
            case R.id.imageButtonBlack:
                DefaultColor=getResources().getColor(R.color.black);
                paintView.currentColor=DefaultColor;
                break;

            case R.id.imageButtonRed:
                DefaultColor=getResources().getColor(R.color.red);
                paintView.currentColor=DefaultColor;
                break;
            case R.id.undo:
                ArrayList<FingerPath> undoPaths=paintView.paths;
                if(undoPaths.size()>0) {
                    undoPaths.remove(paintView.paths.size() - 1);
                }
                    paintView.invalidate();
                //paintView.mCanvas.drawPath(fp.path, paintView.mPaint);
                break;
        }
    }

    //Color picker function using Library ambilwarna
    private void OpenColorPickerDialog(boolean AlphaSupport){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(MainActivity.this, DefaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {
                paintView.currentColor=color;
                DefaultColor = color;
            }

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                //Toast.makeText(MainActivity.this, "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });
        ambilWarnaDialog.show();


    }
}
