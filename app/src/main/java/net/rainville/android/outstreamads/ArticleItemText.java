package net.rainville.android.outstreamads;

import android.text.Html;
import android.text.Spanned;

import java.util.UUID;

/**
 * Created by jim on 10/12/17.
 */

public class ArticleItemText implements ArticleItem {
    private UUID mId;
    private ARTICLE_TYPE mType;
    // private String mArticleText;
    private Spanned mArticleText;

    @SuppressWarnings("deprecation")
    public ArticleItemText(String articleText) {
        mType = ARTICLE_TYPE.TEXT;
        mId = UUID.randomUUID();

        // The text content is HTML so we need to parse it. Need to check the version because
        // we parse it different ways depending on version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mArticleText = Html.fromHtml(articleText,Html.FROM_HTML_MODE_LEGACY);
        } else {
            mArticleText = Html.fromHtml(articleText);
        }

    }

    public Object getContent() {
        return (Object)mArticleText;
    }

    public UUID getArticleId() {
        return mId;
    }

    public ARTICLE_TYPE getArticleType() {
        return mType;
    }
}
