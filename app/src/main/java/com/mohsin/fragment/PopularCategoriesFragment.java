package com.mohsin.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelections;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.mohsin.AppController;
import com.mohsin.R;
import com.mohsin.utils.EventLogger;
import com.mohsin.utils.TrackSelectionHelper;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by user on 30-07-2016.
 */
public class PopularCategoriesFragment extends Fragment implements ExoPlayer.EventListener,
        TrackSelector.EventListener<MappingTrackSelector.MappedTrackInfo>,
        VideoRendererEventListener, SimpleExoPlayer.VideoListener {
    public PopularCategoriesFragment() {
        // Required empty public constructor
    }

    private EventLogger eventLogger;
    public SimpleExoPlayer player;
    MappingTrackSelector trackSelector;
    TrackSelectionHelper trackSelectionHelper;

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    protected String userAgent;

    DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(getContext(), bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }

    HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String videoURL;

    private View view;
    public String colorVaule;
    public VideoView videoView;
    public MediaPlayer mediaPlayer;
    public TextureView textureView;
    public int indexOfThis = 0;
    public int currentlySelected = 0;
    public String videoToPlayURL = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_intro, container, false);
        view = inflater.inflate(R.layout.popular_categories_item, container, false);
        view.setBackgroundColor(Color.parseColor(getColorVaule()));
        //videoView = (VideoView) view.findViewById(R.id.videoView);
        /*textureView = (TextureView) view.findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);*/

        userAgent = Util.getUserAgent(getContext(), "ExoPlayerDemo");

        System.out.println("MOHSIN1");

        Handler handler = new Handler();

        System.out.println("bandwidthMeter.getBitrateEstimate()bandwidthMeter.getBitrateEstimate():" + BANDWIDTH_METER.getBitrateEstimate());
        MediaSource sampleSource = new HlsMediaSource(
                //Uri.parse("https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bca0bf97f815217cd5784e/vod/57bca0bf97f815217cd5784e.m3u8"),
                //Uri.parse("https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/578ec6ac97f8150609ed2a1b/vod/578ec6ac97f8150609ed2a1b.m3u8"),
                //Uri.parse("https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/5758b1b697f815891ca96700/vod/5758b1b697f815891ca96700.m3u8"),
                //Uri.parse("https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bca0bf97f815217cd5784e/vod/57bca0bf97f815217cd5784e.m3u8"),
                //Uri.parse("http://sample.vodobox.net/skate_phantom_flex_4k/skate_phantom_flex_4k.m3u8"),
                Uri.parse(videoToPlayURL),
                buildDataSourceFactory(BANDWIDTH_METER),
                1, null, null);

        LoopingMediaSource loopingMediaSource = new LoopingMediaSource(sampleSource);

        System.out.println("MOHSIN2");

        if (player == null) {
            Handler mainHandler = new Handler();
            eventLogger = new EventLogger();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            trackSelector.addListener(this);
            trackSelector.addListener(eventLogger);
            trackSelectionHelper = new TrackSelectionHelper(trackSelector, videoTrackSelectionFactory);
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, new DefaultLoadControl());

            player.addListener(this);
            player.addListener(eventLogger);
            player.setAudioDebugListener(eventLogger);
            player.setVideoDebugListener(eventLogger);
            player.setId3Output(eventLogger);
        }

        //player.prepare(loopingMediaSource);
        player.prepare(sampleSource);
        player.setVideoTextureView((TextureView) view.findViewById(R.id.textureView));
        //player.setPlayWhenReady(true);
        System.out.println("MOHSIN3");

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public String getColorVaule() {
        return colorVaule;
    }

    public void setColorVaule(String colorVaule) {
        this.colorVaule = colorVaule;
    }

    /*@Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Surface s = new Surface(surfaceTexture);

        try {
            mediaPlayer= new MediaPlayer();
            System.out.println("videoToPlayURLvideoToPlayURLvideoToPlayURL:"+videoToPlayURL);
            mediaPlayer.setDataSource(videoToPlayURL);
            mediaPlayer.setSurface(s);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepareAsync();
            //mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                    System.out.println("onBufferingUpdateonBufferingUpdateonBufferingUpdate:"+i);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    System.out.println(i1+"onErroronErroronError:"+i);
                    return true;
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                    //System.out.println("");
                    return true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    *//*try {
                        mediaPlayer.seekTo(100);
                        if (currentlySelected == indexOfThis)
                            mediaPlayer.start();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }*//*
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    try {
                        System.out.println("onPreparedonPreparedonPrepared");
                        *//*mediaPlayer.seekTo(100);
                        mediaPlayer.pause();*//*

                        if (currentlySelected == indexOfThis)
                            mediaPlayer.start();*//*
                        else
                            mediaPlayer.pause();*//*
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            *//*mediaPlayer.setOnVideoSizeChangedListener(this);*//*
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //mediaPlayer.prepareAsync();
            //mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }*/





    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_BUFFERING) {
            System.out.println("ExoPlayer.STATE_BUFFERING");
            view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }
        if (playbackState == ExoPlayer.STATE_READY) {
            System.out.println("ExoPlayer.STATE_READY");
            view.findViewById(R.id.progressBar).setVisibility(View.GONE);

            if(AppController.currentlySelectedPopularCategoriesPage == indexOfThis) {
                if(!AppController.scrollStarted && AppController.scrollEnded) {
                    view.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    player.setPlayWhenReady(true);
                }
                System.out.println("Play ExoPlayer.STATE_READYExoPlayer.STATE_READYExoPlayer.STATE_READY"+indexOfThis);
            } else {
                view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                player.setPlayWhenReady(false);
                System.out.println("Pause ExoPlayer.STATE_READYExoPlayer.STATE_READYExoPlayer.STATE_READY"+indexOfThis);
            }
        }
        if (playbackState == ExoPlayer.STATE_ENDED) {
            System.out.println("ExoPlayer.STATE_ENDED");
            executedOnlyOnce = false;
            player.seekTo(0);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    // MappingTrackSelector.EventListener implementation

    @Override
    public void onTrackSelectionsChanged(TrackSelections<? extends
            MappingTrackSelector.MappedTrackInfo> trackSelections) {
        MappingTrackSelector.MappedTrackInfo trackInfo = trackSelections.info;

        System.out.println("onTrackSelectionsChanged CALLED");

        if (!executedOnlyOnce) {
            System.out.println("executedOnlyOnceexecutedOnlyOnceexecutedOnlyOnce");

            trackSelectionHelper.trackInfo = trackSelector.getCurrentSelections().info;
            trackSelectionHelper.rendererIndex = 0;

            trackSelectionHelper.trackGroups = trackSelectionHelper.trackInfo.getTrackGroups(trackSelectionHelper.rendererIndex);
            trackSelectionHelper.trackGroupsAdaptive = new boolean[trackSelectionHelper.trackGroups.length];
            for (int i = 0; i < trackSelectionHelper.trackGroups.length; i++) {
                trackSelectionHelper.trackGroupsAdaptive[i] =
                        trackSelectionHelper.adaptiveVideoTrackSelectionFactory != null &&
                                trackSelectionHelper.trackInfo.getAdaptiveSupport(0, i, false) != RendererCapabilities.ADAPTIVE_NOT_SUPPORTED &&
                                trackSelectionHelper.trackGroups.get(i).length > 1;
            }
            trackSelectionHelper.isDisabled = trackSelectionHelper.selector.getRendererDisabled(0);
            //trackSelectionHelper.setOverride(0, trackSelectionHelper.trackGroups.get(0), true);
            trackSelectionHelper.override = trackSelectionHelper.selector.getSelectionOverride(0, trackSelectionHelper.trackGroups);

            System.out.println("bandwidthMeter.getBitrateEstimate()bandwidthMeter.getBitrateEstimate():" + BANDWIDTH_METER.getBitrateEstimate());


            for (int i = 0; i < trackSelections.info.getTrackGroups(0).get(0).length-1; i++) {
            //for (int i = 0; i < 1; i++) {
                int groupIndex = 0;
                int trackIndex = i;
                /*if (i == 0)
                    trackIndex = trackSelections.info.getTrackGroups(0).get(0).length - 1;
                if(i==1)
                    trackIndex = trackSelections.info.getTrackGroups(0).get(0).length-2;
                if(i==2)
                    trackIndex = trackSelections.info.getTrackGroups(0).get(0).length-3;
                if(i==3)
                    trackIndex = trackSelections.info.getTrackGroups(0).get(0).length-4;
                if(i==4)
                    trackIndex = trackSelections.info.getTrackGroups(0).get(0).length-5;*/
                if (!trackSelectionHelper.trackGroupsAdaptive[groupIndex] || trackSelectionHelper.override == null
                        || trackSelectionHelper.override.groupIndex != groupIndex) {
                    trackSelectionHelper.override = new MappingTrackSelector.SelectionOverride(trackSelectionHelper.FIXED_FACTORY, groupIndex, trackIndex);
                } else {
                    // The group being modified is adaptive and we already have a non-null override.
                    // Add the track to the override.
                    trackSelectionHelper.setOverride(groupIndex, trackSelectionHelper.getTracksAdding(trackSelectionHelper.override, trackIndex),
                            true);
                }
            }


            trackSelectionHelper.setOverride(trackSelectionHelper.override.groupIndex, trackSelectionHelper.override.tracks, true);

            /*int[] tracks = new int[trackSelections.info.getTrackGroups(0).get(0).length];
            for (int j = 0, i = trackSelections.info.getTrackGroups(0).get(0).length; i > 0; i--, j++) {
                tracks[j] = i - 1;
            }
            TrackSelection.Factory factory = trackSelections.info.getTrackGroups(0).get(0).length == 1 ? trackSelectionHelper.FIXED_FACTORY
                    : (false ? trackSelectionHelper.RANDOM_FACTORY : trackSelectionHelper.adaptiveVideoTrackSelectionFactory);
            trackSelectionHelper.override = new MappingTrackSelector.SelectionOverride(factory, 0, tracks);
            trackSelectionHelper.setOverride(trackSelectionHelper.override.groupIndex, trackSelectionHelper.override.tracks, true);*/


            trackSelectionHelper.selector.setRendererDisabled(0, false);
            if (trackSelectionHelper.override != null) {
                trackSelectionHelper.selector.setSelectionOverride(0, trackSelectionHelper.trackGroups, trackSelectionHelper.override);
            }
            //if(executedOnlyOnceFlag >1)
            executedOnlyOnce = true;
            //executedOnlyOnceFlag++;
            System.out.println("executedOnlyOnceexecutedOnlyOnceexecutedOnlyOnce");
        }
    }

    public int executedOnlyOnceFlag = 0;
    public boolean executedOnlyOnce = false;

    @Override
    public void onVideoEnabled(DecoderCounters counters) {

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

    }

    @Override
    public void onVideoInputFormatChanged(Format format) {
        //player.setPlayWhenReady(true);
    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

    }

    @Override
    public void onRenderedFirstFrame() {

    }

    @Override
    public void onVideoTracksDisabled() {

    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }
}