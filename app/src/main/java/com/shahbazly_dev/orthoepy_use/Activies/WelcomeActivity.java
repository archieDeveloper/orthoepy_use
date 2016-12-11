package com.shahbazly_dev.orthoepy_use.Activies;

import com.shahbazly_dev.orthoepy_use.R;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeConfiguration;


public class WelcomeActivity extends com.stephentuso.welcome.WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorAccent)
                .page(new TitlePage(R.drawable.unlocked,
                        "Title")
                )
                .page(new BasicPage(R.drawable.unlocked,
                        "Header",
                        "More text.")
                        .background(R.color.colorPrimaryDark)
                )
                .page(new BasicPage(R.drawable.unlocked,
                        "Lorem ipsum",
                        "dolor sit amet.")
                )
                .swipeToDismiss(true)
                .build();
    }
}