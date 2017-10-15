package net.rainville.android.outstreamads;

import android.os.Bundle;

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

    public ArticleItemVideo(String VideoID, String AccountID, String PolicyKey) {
        mType = ARTICLE_TYPE.VIDEO;
        mId = UUID.randomUUID();

        EventEmitter eventEmitter = new EventEmitterImpl();
        Catalog catalog = new Catalog(eventEmitter, AccountID, PolicyKey);

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
