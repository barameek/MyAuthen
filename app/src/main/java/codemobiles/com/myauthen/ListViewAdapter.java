package codemobiles.com.myauthen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codemobiles.util.CMXmlJsonConvertor;
import com.codemobiles.util.CircleTransform;
import com.google.android.youtube.player.YouTubeIntents;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by maboho_retina on 9/26/15 AD.
 */
public class ListViewAdapter extends BaseAdapter {

    private final ArrayList<Object> mFeedDataList;
    public LayoutInflater mInflater;
    public Context mContext;


    public ListViewAdapter(Context context, ArrayList<Object> feedDataList){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mFeedDataList = feedDataList;
    }


    @Override
    public int getCount() {
        return mFeedDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_listview, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.item_listview_title);
            holder.description = (TextView)convertView.findViewById(R.id.item_listview_desc);
            holder.authorImage = (ImageView)convertView.findViewById(R.id.item_listview_authorIcon);
            holder.youtubeThumbnail = (ImageView)convertView.findViewById(R.id.item_listview_youtube_image);

            holder.youtubeThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String youtubeID = v.getTag(R.id.fbLoginButton).toString();
                    Toast.makeText(mContext, youtubeID, Toast.LENGTH_SHORT).show();
                    Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(mContext, youtubeID, true, false);
                    mContext.startActivity(intent);
                }
            });


            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();

        }



        Object item = mFeedDataList.get(position);

        String youtubeID = CMXmlJsonConvertor.getValue(item, "youtubeID");

        holder.youtubeThumbnail.setTag(R.id.fbLoginButton,youtubeID);


        holder.title.setText(CMXmlJsonConvertor.getValue(item,"title"));
        holder.description.setText(CMXmlJsonConvertor.getValue(item,"description"));

        Picasso.with(mContext).load(CMXmlJsonConvertor.getValue(item,"youtube_image")).into(holder.youtubeThumbnail);
        Picasso.with(mContext).load(CMXmlJsonConvertor.getValue(item, "image_link")).transform(new CircleTransform()).into(holder.authorImage);

        return convertView;
    }

    public class ViewHolder{
        TextView title;
        ImageView authorImage;
        ImageView youtubeThumbnail;
        TextView description;
    }
}
