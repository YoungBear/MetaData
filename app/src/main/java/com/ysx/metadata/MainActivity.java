package com.ysx.metadata;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        displayData();
    }

    private void displayData() {
        Context context = this.getApplicationContext();
        final String KEY_STRING = "string_value";
        final String KEY_INT = "int_value";
        final String KEY_BOOLEAN = "boolean_value";
        final String KEY_COLOR = "color_value";
        final String KEY_FLOAT = "float_value";
        final String KEY_LONG = "long_value";
        final String KEY_SERVICE_ID = "SERVICE_ID";


        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA
            );
            Bundle metaData = applicationInfo.metaData;

            int aInt = metaData.getInt(KEY_INT);
            String aString = metaData.getString(KEY_STRING);
            boolean aBoolean = metaData.getBoolean(KEY_BOOLEAN);
            int aColor = metaData.getInt(KEY_COLOR);
            mTvContent.setTextColor(aColor);
            float aFloat = metaData.getFloat(KEY_FLOAT);

            String tmpStr = metaData.getString(KEY_LONG);
            long aLong = Long.parseLong(tmpStr.substring(1));

            String tmpStrId = metaData.getString(KEY_SERVICE_ID);
            long serviceId = Long.parseLong(tmpStrId.substring(1));

            StringBuilder sb = new StringBuilder();
            sb
                    .append("\n" + "aInt: " + aInt)
                    .append("\n" + "aString: " + aString)
                    .append("\n" + "aBoolean: " + aBoolean)
                    .append("\n" + "aColor: " + aColor)
                    .append("\n" + "aLong: " + aLong)
                    .append("\n" + "aFloat: " + aFloat)
                    .append("\n" + "serviceId: " + serviceId)

            ;

            Log.d(TAG, "displayData: " + sb.toString());


            mTvContent.setText(sb.toString());

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
