package com.dellnaresh.jsoup;


import com.dellnaresh.db.model.MovieEntity;
import com.dellnaresh.db.repo.MovieRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by nareshm on 5/14/2016.
 */
@Component
public class TeluguMovies implements Movies {
    private Logger logger = LoggerFactory.getLogger(TeluguMovies.class);
    @Value("${app.movie.url}")
    public String URL ;
    @Value("${app.movie.download.directory}")
    public String downloadDirectory ;
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void getTeluguTorrents() throws IOException {
        Document doc = Jsoup.connect(URL).get();
        Elements torrents = doc.select("a[title$=Download torrent file]");
        torrents.parallelStream().forEach(torrent -> {
            String href = torrent.attr("href");
            String name = org.apache.commons.lang3.StringUtils.substringAfter(href,"title=[kat.cr]");
            href = "https:" + href;
            MovieEntity movieEntity = new MovieEntity(name, href);
            List<MovieEntity> byName = movieRepository.findByName(name);
            logger.debug("Existing movie {}",byName);
            if (byName.size() > 0) {
                logger.debug("Movie {} exits in the database", movieEntity);
            } else {
                logger.debug("Adding movie into the database:{}",movieEntity);
                movieRepository.save(movieEntity);
                downloadTorrentFile(href);
            }
        });
    }

    private void downloadTorrentFile(String link) {
        FirefoxProfile fxProfile = new FirefoxProfile();

        fxProfile.setPreference("browser.download.folderList", 2);
        fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        fxProfile.setPreference("browser.download.dir", downloadDirectory);
        fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-bittorrent");
        WebDriver driver = new FirefoxDriver(fxProfile);
        driver.manage().window().setPosition(new Point(-0, -2800));
        driver.get(link);
        driver.close();
    }
}
