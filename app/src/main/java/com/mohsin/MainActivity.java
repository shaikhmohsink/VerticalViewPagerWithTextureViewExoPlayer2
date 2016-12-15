package com.mohsin;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.mohsin.adapter.PopularCategoriesFragmentAdapter;
import com.mohsin.components.VerticalViewPager;
import com.mohsin.components.transformer.DepthPageTransformer;
import com.mohsin.fragment.PopularCategoriesFragment;

public class MainActivity extends AppCompatActivity {
    public int currentlySelectedPopularCategoriesPage = 0;
    public PopularCategoriesFragmentAdapter popularCategoriesFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        VerticalViewPager popularCategoriesVerticalViewPager = (VerticalViewPager) findViewById(R.id.popularCategoriesVerticalViewPager);
        popularCategoriesVerticalViewPager.setPageTransformer(true, new DepthPageTransformer());
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        popularCategoriesVerticalViewPager.setOverScrollMode(popularCategoriesVerticalViewPager.OVER_SCROLL_NEVER);
        popularCategoriesVerticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(!AppController.pauseVideoAndShowImageOnlyOnce) {
                    AppController.scrollStarted = true;
                    AppController.scrollEnded = false;
                    //try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).mediaPlayer.pause(); } catch(Exception e) {}
                    //try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).imaPlayer.pause(); } catch(Exception e) {}
                    /*try {
                        System.out.println("WHAT THE HELL!!! BEFORE");
                        ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).videoTexturePresenter.pause();
                    } catch(Exception e) { e.printStackTrace(); System.out.println("WHAT THE HELL!!!");}*/
                    try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).player.setPlayWhenReady(false); } catch(Exception e) {}
                    AppController.pauseVideoAndShowImageOnlyOnce = true;
                }

                if(AppController.pauseVideoAndShowImageOnlyOnce && positionOffsetPixels == 0) {
                    System.out.println("WHAT THE HELL!!! WHAT THE HELL!!!");
                    AppController.pauseVideoAndShowImageOnlyOnce = false;
                    AppController.scrollStarted = false;
                    AppController.scrollEnded = true;
                    ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).currentlySelected = position;
                    AppController.currentlySelectedPopularCategoriesPage = position;
                    //try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).mediaPlayer.start(); } catch(Exception e) {}
                    //try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).imaPlayer.play(); } catch(Exception e) {}
                    //try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).videoTexturePresenter.play(); } catch(Exception e) {}
                    try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).player.setPlayWhenReady(true); } catch(Exception e) {}
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentlySelectedPopularCategoriesPage = position;
                /*System.out.println("onPageSelected position:"+position);
                ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).currentlySelected = position;
                AppController.currentlySelectedPopularCategoriesPage = position;
                //try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).mediaPlayer.start(); } catch(Exception e) {}
                try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).imaPlayer.play(); } catch(Exception e) {}*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        // Create an adapter with the fragments we show on the ViewPager
        popularCategoriesFragmentAdapter = new PopularCategoriesFragmentAdapter(
                getSupportFragmentManager(), 7);
        for (int i = 0; i < 7; i++) {
            PopularCategoriesFragment popularCategoriesFragment = new PopularCategoriesFragment();

            popularCategoriesFragment.indexOfThis = i;
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            /*popularCategoriesFragment.screenHeight = displaymetrics.heightPixels;
            popularCategoriesFragment.screenWidth = displaymetrics.widthPixels;*/

            if(i == 0) {
                popularCategoriesFragment.setColorVaule("#ffffff");
                popularCategoriesFragment.videoToPlayURL = "https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bca0bf97f815217cd5784e/vod/57bca0bf97f815217cd5784e.m3u8";
                //popularCategoriesFragment.videoToPlayURL = "https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/5758b1b697f815891ca96700/vod/5758b1b697f815891ca96700.mp4";
                //popularCategoriesFragment.videoToPlayURL = "http://sample.vodobox.net/skate_phantom_flex_4k/skate_phantom_flex_4k.m3u8";
            } else if(i == 1) {
                popularCategoriesFragment.setColorVaule("#ff0000");
                popularCategoriesFragment.videoToPlayURL = "https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bca0be99f815d6387023ed/vod/57bca0be99f815d6387023ed.m3u8";
                //popularCategoriesFragment.videoToPlayURL = "http://content.jwplatform.com/manifests/vM7nH0Kl.m3u8";
            } else if(i == 2) {
                popularCategoriesFragment.setColorVaule("#00ff00");
                popularCategoriesFragment.videoToPlayURL = "https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bc9ffd99f815113a7023ed/vod/57bc9ffd99f815113a7023ed.m3u8";
                //popularCategoriesFragment.videoToPlayURL = "http://cdn-fms.rbs.com.br/vod/hls_sample1_manifest.m3u8";
            } else if(i == 3) {
                popularCategoriesFragment.setColorVaule("#0000ff");
                popularCategoriesFragment.videoToPlayURL = "https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bc9ffd97f815437ad5784d/vod/57bc9ffd97f815437ad5784d.m3u8";
                //popularCategoriesFragment.videoToPlayURL = "http://content.jwplatform.com/manifests/vM7nH0Kl.m3u8";
            } else if(i == 4) {
                popularCategoriesFragment.setColorVaule("#0f0f0f");
                popularCategoriesFragment.videoToPlayURL = "https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bc9ffd97f815197bd5784d/vod/57bc9ffd97f815197bd5784d.m3u8";
                //popularCategoriesFragment.videoToPlayURL = "http://playertest.longtailvideo.com/adaptive/wowzaid3/playlist.m3u8";
            } else if(i == 5) {
                popularCategoriesFragment.setColorVaule("#ffffff");
                popularCategoriesFragment.videoToPlayURL = "https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bc9ed199f8155f377023ed/vod/57bc9ed199f8155f377023ed.m3u8";
                //popularCategoriesFragment.videoToPlayURL = "http://playertest.longtailvideo.com/adaptive/captions/playlist.m3u8";
            } else if(i == 6) {
                popularCategoriesFragment.setColorVaule("#ff0000");
                popularCategoriesFragment.videoToPlayURL = "https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bc9ed199f81517387023ed/vod/57bc9ed199f81517387023ed.m3u8";
                //popularCategoriesFragment.videoToPlayURL = "http://playertest.longtailvideo.com/adaptive/oceans_aes/oceans_aes.m3u8";
            } else {
                popularCategoriesFragment.setColorVaule("#00ff00");
                popularCategoriesFragment.videoToPlayURL = "https://k7q5a5e5.ssl.hwcdn.net/files/company/575729ad97f8152c41a96700/assets/videos/57bc9ed197f815f378d5784d/vod/57bc9ed197f815f378d5784d.m3u8";
            }
            popularCategoriesFragmentAdapter.addFragment(popularCategoriesFragment);
        }

        popularCategoriesVerticalViewPager.setAdapter(popularCategoriesFragmentAdapter);
        //popularCategoriesVerticalViewPager.setOffscreenPageLimit(popularCategoriesFragmentAdapter.getCount());
        //popularCategoriesVerticalViewPager.setVerticalFadingEdgeEnabled(true);

        AppController.currentlySelectedPopularCategoriesPage = 0;
        //try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).mediaPlayer.start(); } catch(Exception e) {}
        //try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).imaPlayer.play(); } catch(Exception e) {}
    }
    public DisplayMetrics displaymetrics = new DisplayMetrics();
}
