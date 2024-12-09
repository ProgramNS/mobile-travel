package com.reza.travel.activity.ui.kontak;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.reza.travel.R;

public class KontakFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kontak, container, false);

        TextView tvFacebook = view.findViewById(R.id.tvFacebook);
        TextView tvInstagram = view.findViewById(R.id.tvInstagram);
        TextView tvTiktok = view.findViewById(R.id.tvTiktok);
        TextView tvWhatsapp = view.findViewById(R.id.tvWhatsapp);

        tvFacebook.setOnClickListener(v -> openLink("https://www.facebook.com/profile.php?id=61558296853510"));
        tvInstagram.setOnClickListener(v -> openLink("https://www.instagram.com/nila.transport?igsh=MWc1OHQ3dnM1Z3F4Mw=="));
        tvTiktok.setOnClickListener(v -> openLink("https://www.tiktok.com/@nilatransport?_t=8rlO0xPf4Co&_r=1"));
        tvWhatsapp.setOnClickListener(v -> openLink("https://wa.me/6285175115195"));

        return view;
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
