package net.rainville.android.outstreamads;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.brightcove.player.event.Event;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.event.EventListener;
import com.brightcove.player.event.EventType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.brightcove.player.view.BrightcoveVideoView;

import java.util.List;

/**
 * Created by jim on 10/9/17.
 */

public class ArticleListFragment extends Fragment {

    private RecyclerView mArticleRecyclerView;
    private ArticleAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        mArticleRecyclerView =  view.findViewById(R.id.article_recycler_view);
        mArticleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        Article article = Article.get(getActivity());
        List<ArticleItem> articleItems = article.getArticleItems();

        mAdapter = new ArticleAdapter(articleItems);
        mArticleRecyclerView.setAdapter(mAdapter);
    }

    private abstract class AbstractArticleItemHolder extends RecyclerView.ViewHolder {
        private ArticleItem mArticleItem;

        public AbstractArticleItemHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
            super(inflater.inflate(layoutId, parent, false));
        }

        /*
        public void bind(ArticleItem articleItem ) {
            mArticleItem = articleItem;
        }
        */


        public ArticleItem getArticleItem() {
            return mArticleItem;
        }
    }

    private class TextArticleHolder extends AbstractArticleItemHolder {
        private TextView mTextView;
        private ArticleItemText mTexttem;

        public TextArticleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.article_item_text);
            mTextView = (TextView)itemView.findViewById(R.id.text_view);
        }

        public void bind(ArticleItemText textItem) {
            mTexttem = textItem;
            mTextView.setText((String)mTexttem.getContent());
        }
    }

    private class VideoArticleHolder extends AbstractArticleItemHolder {

        public final Context context;
        public final TextView videoTitleText;
        public final FrameLayout videoFrame;
        public final BrightcoveVideoView videoView;

        public VideoArticleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.article_item_video);
            context = itemView.getContext();
            videoFrame = (FrameLayout) itemView.findViewById(R.id.video_frame);
            videoTitleText = (TextView) itemView.findViewById(R.id.video_title_text);
            videoView = new BrightcoveExoPlayerVideoView(context);
            videoFrame.addView(videoView);
            videoView.finishInitialization();

            EventEmitter eventEmitter = videoView.getEventEmitter();
            eventEmitter.on(EventType.ENTER_FULL_SCREEN, new EventListener() {
                @Override
                public void processEvent(Event event) {
                    //You can set listeners on each Video View
                }
            });
        }

        // private TextView mTextView;
        // private ArticleItemVideo mVideoItem;

        /*
        public VideoArticleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.article_item_video);
            mTextView = (TextView)itemView.findViewById(R.id.text_view);
        }
        */
        public void bind(ArticleItemVideo videoItem) {
            // mVideoItem = videoItem;
            // mTextView.setText((String)mVideoItem.getContent());
        }

    }

    private class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int ARTICLE_ITEM_TEXT = 0;
        private static final int ARTICLE_ITEM_VIDEO = 1;

        private List<ArticleItem> mArticleItems;

        public ArticleAdapter(List<ArticleItem> articleItems) {
            mArticleItems = articleItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            if (viewType == ARTICLE_ITEM_TEXT) {
                return new TextArticleHolder(layoutInflater, parent);
            } else if (viewType == ARTICLE_ITEM_VIDEO) {
                return new VideoArticleHolder(layoutInflater, parent);
            } else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ArticleItem articleItem = mArticleItems.get(position);

            if (articleItem.getArticleType() == ArticleItem.ARTICLE_TYPE.TEXT) {
                ((TextArticleHolder) holder).bind((ArticleItemText)articleItem);
            } else if (articleItem.getArticleType() == ArticleItem.ARTICLE_TYPE.VIDEO) {

                // ((VideoArticleHolder)holder).bind(articleItem);
                Video video = (Video) articleItem.getContent();
                VideoArticleHolder vHolder = (VideoArticleHolder) holder;
                vHolder.videoTitleText.setText(video.getStringProperty(Video.Fields.NAME));
                BrightcoveVideoView videoView = vHolder.videoView;
                videoView.clear();
                videoView.add(video);

                // ((VideoArticleHolder) holder).bind((ArticleItemVideo)articleItem);
            }
        }

        @Override
        public int getItemCount() {
            return mArticleItems.size();
        }

        @Override
        public int getItemViewType(int position) {
            ArticleItem.ARTICLE_TYPE aType = mArticleItems.get(position).getArticleType();

            if (aType == ArticleItem.ARTICLE_TYPE.VIDEO) {
                return ARTICLE_ITEM_VIDEO;
            } else {
                return ARTICLE_ITEM_TEXT;
            }
        }
    }

}
