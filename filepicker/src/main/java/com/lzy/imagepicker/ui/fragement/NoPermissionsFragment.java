package com.lzy.imagepicker.ui.fragement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.imagepicker.R;

/**
 *未获取到权限的时候，的显示碎片
 */
public class NoPermissionsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_no_permissions,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().findViewById(R.id.t_open_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(); intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                //设置去向意图
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                //发起跳转
                startActivity(intent);
            } });

    }
}
