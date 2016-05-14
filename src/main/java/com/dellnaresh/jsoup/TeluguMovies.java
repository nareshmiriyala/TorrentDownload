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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by nareshm on 5/14/2016.
 */
@Component
public class TeluguMovies implements Movies {

    public static final String URL = "https://kat.cr/usearch/telugu%20category%3Amovies/?field=seeders&sorder=desc";
    @Autowired
    private MovieRepository movieRepository;
    @Override
    public void getTeluguTorrents() throws IOException {
        Document doc = Jsoup.connect(URL).get();
        Elements torrentname = doc.getElementsByClass("torrentname");
        Elements torrents = doc.select("a[title$=Download torrent file]");
        torrents.parallelStream().forEach(torrent -> {
            String href = torrent.attr("href");
            String torrentName=torrentname.attr("href");
//            href = StringUtils.substringBefore(href, "?title=");
            href = "https:" + href;
            movieRepository.save(new MovieEntity(torrentName,href));
            downloadTorrentFile(href);
        });
    }

    private void downloadTorrentFile(String link) {
        FirefoxProfile fxProfile = new FirefoxProfile();

        fxProfile.setPreference("browser.download.folderList", 2);
        fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        fxProfile.setPreference("browser.download.dir", "C:\\Users\\nareshm\\Downloads\\Telugu_Torrents");
        fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-bittorrent");
        WebDriver driver = new FirefoxDriver(fxProfile);
        driver.manage().window().setPosition(new Point(-0, -2800));
        driver.get(link);
        driver.close();
    }
}
