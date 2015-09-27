package codemobiles.com.myauthen;


import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.codemobiles.util.BlurUtil;
import com.codemobiles.util.CMFeedJsonUtil;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedJSONFragment extends Fragment {


    private ListView listview;
    private ArrayList<Object> feedDataList;
    private View headerView;
    private ImageView mBlurredImage;
    private ImageView mNormalImage;

    public FeedJSONFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed_json, container, false);
        listview = (ListView)v.findViewById(R.id.listview);
        mBlurredImage = (ImageView) v.findViewById(R.id.blurred_image);
        mNormalImage = (ImageView) v.findViewById(R.id.normal_image);

        loadData();
        return v;
    }

    private void loadData() {

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // custom thread
                // json feed
                final String _url = "http://codemobiles.com/adhoc/feed/youtube_feed.php";
                // assign post data
                ContentValues postData = new ContentValues();
                postData.put("type","json");

                feedDataList = CMFeedJsonUtil.feed(_url, postData);


                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (headerView == null) {
                            headerView = new View(getActivity());
                            final int TOP_MARGIN = (int) getActivity().getResources().getDimension(R.dimen.listview_header_height);

                            headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, TOP_MARGIN));
                            listview.addHeaderView(headerView);
                        }
                        BlurUtil.setupBlurEffect(getActivity(), listview,R.drawable.bg,headerView,mNormalImage,mBlurredImage);

                        listview.setAdapter(new ListViewAdapter(getActivity(), feedDataList));
                    }
                });

            }
        }).start();
    }


}
