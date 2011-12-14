package com.chrishoekstra.trello;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.Log;

import com.chrishoekstra.trello.vo.NewsVO;
import com.chrishoekstra.trello.vo.NotificationVO;

public class Utils {

    private static SimpleDateFormat mSimpleDateFormat;
    private static DateTimeFormatter mDateTimeFormatter;
    private static HashMap<String, NewsFormatFunction> mNewsFormattings;
    
    // Card
    private static final String ADDED_TO_CARD   = "added you to the card ";
    private static final String CHANGE_CARD     = "changed the card ";
    private static final String COMMENT_CARD    = "commented on the card ";

    // Organization
    private static final String INVITED_TO_ORGANIZTION = "invited you to the organization ";

    private interface NewsFormatFunction {
        Spanned format(NewsVO news);
    }
    
    static {
        mSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        mDateTimeFormatter = ISODateTimeFormat.dateTime();
        
        mNewsFormattings = new HashMap<String, NewsFormatFunction>();
        
        // Card
        mNewsFormattings.put(NewsVO.ADDED_TO_CARD, new NewsFormatFunction() {
            @Override
            public Spanned format(NewsVO news) {
                Spanned text;
                text = Html.fromHtml(ADDED_TO_CARD + "<b>" + news.data.card.name + "</b> on <b>" + news.data.board.name + "</b>");
                return text;
            }
        });
        
        mNewsFormattings.put(NewsVO.CHANGE_CARD, new NewsFormatFunction() {
            @Override
            public Spanned format(NewsVO news) {
                Spanned text;
                text = Html.fromHtml(CHANGE_CARD + "<b>" + news.data.card.name + "</b> on <b>" + news.data.board.name + "</b>");
                return text;
            }
        });
        
        mNewsFormattings.put(NewsVO.COMMENT_CARD, new NewsFormatFunction() {
            @Override
            public Spanned format(NewsVO news) {
                Spanned text;
                text = Html.fromHtml(COMMENT_CARD + "<b>" + news.data.card.name + "</b> on <b>" + news.data.board.name + "</b>");
                return text;
            }
        });
        
        // Organization
        mNewsFormattings.put(NewsVO.INVITED_TO_ORGANIZTION, new NewsFormatFunction() {
            @Override
            public Spanned format(NewsVO news) {
                Spanned text;
                text = Html.fromHtml(INVITED_TO_ORGANIZTION + "<b>" + news.data.organization.name + "</b>");
                return text;
            }
        });
    }
    
    public static Spanned getHtmlFormattedNotificationText(NotificationVO notification) {
        NewsFormatFunction formatFunction = mNewsFormattings.get(notification.type);
        
        if (formatFunction != null) {
            return formatFunction.format(notification);
        } else {
            Log.w("News Format", "Unsupported news format type - " + notification.type);
            return Html.fromHtml(notification.type);
        }
    }

    public static String getFormattedStringFromDate(String date) {
        Date current = new Date();
        Date twoDaysAgo = new Date();
        Date newsDate = mDateTimeFormatter.parseDateTime(date).toDate();
        
        twoDaysAgo.setDate(current.getDate() - 2);
        if (twoDaysAgo.after(newsDate)) {
            return mSimpleDateFormat.format(newsDate);
        } else {
            return (String) DateUtils.getRelativeTimeSpanString(mDateTimeFormatter.parseDateTime(date).getMillis(), 
                    new Date().getTime(), 
                    DateUtils.SECOND_IN_MILLIS);
        }        
    }
}
