package com.example.cameralifting;


import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.shell.Shell;

public class MainActivity extends AppCompatActivity {
    //设置弹出和收回命令
    String huancunlujing;
    String up_cmd = "LD_LIBRARY_PATH=/data/local/tmp /data/local/tmp/xiaomi-motor.bin popup 1";
    String down_cmd = "LD_LIBRARY_PATH=/data/local/tmp /data/local/tmp/xiaomi-motor.bin takeback 1";

    TextView ismiui_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置状态栏沉浸
        setStatusBarBgColor(getResources().getColor(R.color.white));

        ismiui_text = findViewById(R.id.ismiui_t);

       huancunlujing = getExternalCacheDir().getAbsolutePath();
       //把assrts文件夹下的文件复制到外部存储
        AssetsUtils.copyFolderFromAssetsToSD(this, "files", huancunlujing + "/");
        if (myutils.isMIUI()) // 检测MIUI
        {
            ismiui_text.setText(R.string.isMIUI_ture);
            ismiui_text.setTextColor(getResources().getColor(R.color.red));
        }
        else
        {
            ismiui_text.setText(R.string.isMIUI_false);
            ismiui_text.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        if (!myutils.checkRoot()) // 检测ROOT
        {
            Toast.makeText(this, R.string.check_root_false, Toast.LENGTH_LONG).show();

        }


    }

    //升起
    public void b_up(View view) {
        if (!myutils.checkRoot()) // 检测ROOT
        {
            Toast.makeText(this, R.string.check_root_false, Toast.LENGTH_LONG).show();

        }else {
            Shell.SU.run("cp -r " + huancunlujing + "/* /data/local/tmp");
            Shell.SU.run("chmod 777 /data/local/tmp/*");
            Shell.SU.run(up_cmd);
            Shell.SU.run("rm -f /data/local/tmp/*");
        }
    }

    //落下
    public void b_down(View view) {
        if (!myutils.checkRoot()) // 检测ROOT
        {
            Toast.makeText(this, R.string.check_root_false, Toast.LENGTH_LONG).show();

        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle(R.string.dialog_title);
            alertDialog.setMessage(R.string.dialog_msg);
            alertDialog.setCancelable(true);


            alertDialog.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    down();
                    dialog.dismiss();
                }
            });

            alertDialog.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setNeutralButton(R.string.dialog_what, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    erciqueren();
                }
            });
            alertDialog.create();
            alertDialog.show();
        }

    }



public void down()
{
    Shell.SU.run("cp -r " + huancunlujing + "/* /data/local/tmp");
    Shell.SU.run("chmod 777 /data/local/tmp/*");
    Shell.SU.run(down_cmd);
    Shell.SU.run("rm -f /data/local/tmp/*");
}

//收回时二次确认
public void erciqueren()
{
    AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(MainActivity.this);
    alertDialog1.setTitle(R.string.dialog_title1);
    alertDialog1.setMessage(R.string.dialog_msg1);
    alertDialog1.setCancelable(true);


    alertDialog1.setPositiveButton(R.string.dialog_yes1, new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            down();
        }
    });

    alertDialog1.setNegativeButton(R.string.dialog_no1, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    alertDialog1.create();
    alertDialog1.show();
}

    public void lianxizuozhe(View view) {
            Uri uri =Uri.parse("http://www.coolapk.com/u/990229");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);

    }
    //设置状态栏沉浸的同时使用isLightColor方法使状态栏反色
    public void setStatusBarBgColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
            if (isLightColor(color)) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }
}