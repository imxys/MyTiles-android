package com.xys.mytiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

public class VolumeTileService extends TileService {
    String TAG = VolumeTileService.class.getSimpleName();

    @Override
    public void onStartListening() {
        super.onStartListening();
        Tile tile = getQsTile();
        if (tile == null) {
            Log.w(TAG, "onStartListening getQsTile == null");
            return;
        }
        Log.i(TAG, "onStartListening current state: " + tile.getState());
        if (tile.getState() == Tile.STATE_UNAVAILABLE) {
            tile.setState(Tile.STATE_INACTIVE);
        }
        tile.updateTile();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        Log.i(TAG, "onStopListening");
        updateTile();
    }

    @Override
    public void onClick() {
        final String CONFIG_PERMISSION_NOTIFIED = "PermissionNotified";

        super.onClick();
        Log.i(TAG, "onClick");

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Object statusBarManager = getSystemService("statusbar");
        SharedPreferences sharedPreferences = getSharedPreferences("config", Context.MODE_APPEND);

        if (!sharedPreferences.getBoolean(CONFIG_PERMISSION_NOTIFIED, false)) {
            startActivityToAllowPermission();
            sharedPreferences.edit().putBoolean(CONFIG_PERMISSION_NOTIFIED, true).apply();
            return;
        }

        audioManager.adjustVolume(AudioManager.ADJUST_SAME,
                AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_ALLOW_RINGER_MODES);

        // TODO: allow config: we need to create an activity.
        try {
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            int sdkVersion = android.os.Build.VERSION.SDK_INT;
            Method collapse = null;
            if (sdkVersion <= 16) {
                collapse = clazz.getMethod("collapse");
            } else {
                collapse = clazz.getMethod("collapsePanels");
            }
            collapse.setAccessible(true);
            collapse.invoke(statusBarManager);
        } catch (Exception e) {
            Toast.makeText(this, R.string.status_bar_collapse_failed_toast, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        updateTile();
    }

    /**
     * Can't check permission in code.
     */
    private void startActivityToAllowPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri myAppUri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(myAppUri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivityAndCollapse(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.start_app_detail_failed_toast, Toast.LENGTH_SHORT).show();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, R.string.permission_notification_toast, Toast.LENGTH_LONG).show();
    }

    private void updateTile() {
        Tile tile = getQsTile();
        if (tile == null) {
            Log.w(TAG, "updateTile tile == null");
            return;
        }
        tile.updateTile();
    }
}
