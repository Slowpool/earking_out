package org.swetlokognatsk.earking_out;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

public final class WebEntryPoint {

    public static void main(String[] args) {
        SpringApp.build = Build.WEB;
        var springApplication = new SpringApplication(SpringApp.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }

}
