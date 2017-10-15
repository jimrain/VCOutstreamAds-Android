package net.rainville.android.outstreamads;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jim on 10/12/17.
 */

public class Article {
    private static Article sArticle;

    private List<ArticleItem> mArticleItems;

    public static Article get(Context context) {
        if (sArticle == null) {
            sArticle = new Article(context);
        }
        return sArticle;
    }

    private Article(Context context) {
        mArticleItems = new ArrayList<>();

        ArticleItemText article1 = new ArticleItemText(context.getString(R.string.text_item_1));
        mArticleItems.add(article1);

        ArticleItemVideo article2 = new ArticleItemVideo(context.getString(R.string.videoId),
                                                        context.getString(R.string.account),
                                                        context.getString(R.string.policy));
        mArticleItems.add(article2);

        ArticleItemText article3 = new ArticleItemText(context.getString(R.string.text_item_2));
        mArticleItems.add(article3);
    }

    public List<ArticleItem> getArticleItems() {
        return mArticleItems;
    }

    public ArticleItem getArticleItem(UUID id) {
        for (ArticleItem articleItem : mArticleItems) {
            if (articleItem.getArticleId().equals(id)) {
                return articleItem;
            }
        }
        return null;
    }
}
