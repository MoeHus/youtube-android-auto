package com.thekirankumar.youtubeauto;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by kiran.kumar on 11/01/18.
 */

public class VideoWebView extends VideoEnabledWebView {
    public VideoWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setWebViewClient(new WebViewClient(){
            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                super.onUnhandledKeyEvent(view, event);
            }
        });
    }

    public VideoWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void playNextTrack() {
        loadUrl("javascript:var v = document.querySelector(\"video\"); v.currentTime = v.getDuration();\n" +
                "var next = document.querySelectorAll('a[aria-label=\"Play next video\"]'); next[0].click();\n");
    }

    public void pauseVideo() {
        loadUrl("javascript:var v = document.querySelector(\"video\"); v.pause();");
    }

    public void playVideo() {
        loadUrl("javascript:var v = document.querySelector(\"video\"); v.play();");
    }

    public void requestFullScreen() {
        loadUrl("javascript: var vid = document.querySelector('video');  " +
                "if (vid.requestFullscreen) {\n" +
                "      vid.requestFullscreen();\n" +
                "    } else if (vid.mozRequestFullScreen) {\n" +
                "      vid.mozRequestFullScreen();\n" +
                "    } else if (vid.webkitRequestFullscreen) {\n" +
                "      vid.webkitRequestFullscreen();\n" +
                "    }");
    }

    public void discoverVideoElements() {
        loadUrl("javascript: function waitForElementToDisplay(selector, time, fn) {\n" +
                "var el = document.querySelector(selector);" +
                "if(el!=null) {" +
                "            fn(el);" +
                "            return;" +
                "        }" +
                "        else {" +
                "            setTimeout(function() {" +
                "                waitForElementToDisplay(selector, time, fn);" +
                "            }, time);\n" +
                "        }" +
                "    }" +
                "waitForElementToDisplay('video', 1000, function(el) {" +
                "window.nativecallbacks.onVideoDiscovered();" +
                "})");

    }

    public void playVideoIfPausedFlag() {
        loadUrl("javascript: " +
                "if(window.pausedDueToMinimize) {" +
                "var el = document.getElementsByTagName('video')[0];" +
                "    el.play();" +
                "window.pausedDueToMinimize = false;" +
                "}");
    }

    public void pauseVideoAndSetFlag() {
        loadUrl("javascript: var el = document.getElementsByTagName('video')[0];\n" +
                "if(!el.paused) {" +
                "el.pause();" +
                "window.pausedDueToMinimize = true;" +
                "}" +
                "");
    }

    public void attachVideoListeners() {
        String s = ("javascript: var el = document.querySelector('video'); " +
                "el.addEventListener('playing', function(e){window.nativecallbacks.onVideoEvent('playing');});" +
                "el.addEventListener('pause', function(e){window.nativecallbacks.onVideoEvent('pause');});" +
                "el.addEventListener('progress', function(e){window.nativecallbacks.onVideoEvent('progress');});" +
                "el.addEventListener('waiting', function(e){window.nativecallbacks.onVideoEvent('waiting');});" +
                "");
        loadUrl(s);
    }

    public void findAndClickFirstVideo() {
        String s = ("javascript: var el = document.querySelector(\"a[href^='/watch'\"); " +
                "el.click();" +
                "");
        loadUrl(s);
    }

    public void setAspectRatio(String mode) {
        String s = "javascript:document.querySelector('video').setAttribute('style','object-fit:"+mode+"');";
        loadUrl(s);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }


    public void seekBySeconds(int seconds) {
        loadUrl("javascript:var v = document.querySelector(\"video\"); v.currentTime = v.currentTime + "+seconds+";");
    }
    public void getCurrentVideoTimeResult() {
        loadUrl("javascript:var v = document.querySelector(\"video\"); window.nativecallbacks.onVideoCurrentTimeResult(v.currentTime);");
    }
}
