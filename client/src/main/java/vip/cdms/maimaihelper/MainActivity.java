package vip.cdms.maimaihelper;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    ServerConfig config;

    ImageView imageView;
    LinearLayout configLayout;
    EditText urlInput;
    EditText tokenInput;
    CheckBox roundScreenCheckbox;
    Button saveButton;

    View rootView;
    int windowWidth, windowHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config = new ServerConfig(getPreferences(MODE_PRIVATE));

        rootView = getWindow().getDecorView().getRootView();
        imageView = findViewById(R.id.image);
        configLayout = findViewById(R.id.config);
        urlInput = findViewById(R.id.url);
        tokenInput = findViewById(R.id.token);
        roundScreenCheckbox = findViewById(R.id.roundScreen);
        saveButton = findViewById(R.id.save);

        imageView.setOnClickListener(v -> refreshQr());
        imageView.setOnLongClickListener(v -> {
            imageView.setVisibility(ImageView.GONE);
            configLayout.setVisibility(LinearLayout.VISIBLE);
            return true;
        });

        urlInput.setText(config.getUrl());
        tokenInput.setText(config.getToken());
        roundScreenCheckbox.setChecked(config.isRoundScreen());

        saveButton.setOnClickListener(v -> {
            config.setUrl(urlInput.getText().toString());
            config.setToken(tokenInput.getText().toString());
            config.setRoundScreen(roundScreenCheckbox.isChecked());
            applyRoundScreenSetting();
            configLayout.setVisibility(LinearLayout.GONE);
            imageView.setVisibility(ImageView.VISIBLE);
            refreshQr();
        });

        applyRoundScreenSetting();
        refreshQr();
    }

    private void applyRoundScreenSetting() {
        boolean enabled = config.isRoundScreen();
        if (enabled) {
            // 圆屏适配：给根视图添加内边距
            windowWidth = getResources().getDisplayMetrics().widthPixels;
            windowHeight = getResources().getDisplayMetrics().heightPixels;
            // 水平方向 12% 边距，垂直方向 2% 边距（在保证不遮挡的前提下尽可能大）
            int paddingH = (int) (windowWidth * 0.12);
            int paddingV = (int) (windowWidth * 0.02);
            rootView.setPadding(paddingH, paddingV, paddingH, paddingV);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            // 正常模式：无内边距，填满屏幕
            rootView.setPadding(0, 0, 0, 0);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    void refreshQr() {
        String url = config.getUrl() + "?token=" + config.getToken();
        imageView.setImageDrawable(new ColorDrawable(Color.YELLOW));
        NetworkImage.fetch(url, bitmap -> {
            if (bitmap == null) imageView.setImageDrawable(new ColorDrawable(Color.RED));
            else imageView.setImageBitmap(bitmap);
        });
    }
}
