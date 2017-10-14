package net.rainville.android.outstreamads;

import java.util.UUID;

/**
 * Created by jim on 10/12/17.
 */

public interface ArticleItem {
    public static enum ARTICLE_TYPE {
        TEXT,
        VIDEO
    }

    public Object getContent();

    public UUID getArticleId();

    public ARTICLE_TYPE getArticleType();
}
