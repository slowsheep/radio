package net.lzzy.radio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class LocalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        ImageView img = view.findViewById(R.id.fragment_local_img);
        Picasso.get()
                .load("http://pic.qingting.fm/2013/channel_logo/1756.jpg")
                .resize(100,100)
                .into(img);
        Picasso.get().invalidate("http://pic.qingting.fm/2013/channel_logo/1756.jpg");
        return view;
    }
}
