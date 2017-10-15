package net.rainville.android.outstreamads;

import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.event.EventEmitterImpl;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcovePlayer;

import java.util.UUID;

/**
 * Created by jim on 10/12/17.
 */

public class ArticleItemVideo extends BrightcovePlayer implements ArticleItem {
    private UUID mId;
    private ARTICLE_TYPE mType;
    private Video   mVideo;
    // private String mVideo;

    public ArticleItemVideo(String VideoID) {
        mType = ARTICLE_TYPE.VIDEO;
        mId = UUID.randomUUID();
        // mVideo = VideoID;

        // Get the event emitter from the SDK and create a catalog request to fetch a video from the
        // Brightcove Edge service, given a video id, an account id and a policy key.
        //EventEmitter eventEmitter = brightcoveVideoView.getEventEmitter();


        EventEmitter eventEmitter = new EventEmitterImpl();
        Catalog catalog = new Catalog(eventEmitter, getString(R.string.account), getString(R.string.policy));

        catalog.findVideoByID(VideoID, new VideoListener() {

            // Add the video found to the queue with add().
            // Start playback of the video with start().
            @Override
            public void onVideo(Video video) {
                mVideo = video;
            }

            @Override
            public void onError(String s) {
                throw new RuntimeException(s);
            }
        });
    }

    public Object getContent() {
        return (Object)mVideo;
    }

    public UUID getArticleId() {
        return mId;
    }

    public ARTICLE_TYPE getArticleType() {
        return mType;
    }

}
