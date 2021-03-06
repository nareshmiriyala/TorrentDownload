package com.dellnaresh;

import com.dellnaresh.db.repo.MovieRepository;
import com.dellnaresh.jsoup.Movies;
import com.dellnaresh.torrent.Download;
import com.dellnaresh.torrent.TorrentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired
  private Movies movies;

  @Autowired
  private Download download;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
//    movies.getTeluguTorrents();
    download.start();
  }
}
