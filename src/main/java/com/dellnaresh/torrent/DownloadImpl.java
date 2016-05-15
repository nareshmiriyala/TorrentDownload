package com.dellnaresh.torrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nareshm on 5/15/2016.
 */
@Component
public class DownloadImpl implements Download {
    private Logger logger = LoggerFactory.getLogger(DownloadImpl.class);
    @Value("${app.movie.download.directory}")
    public String downloadDirectory;
    @Value("${app.torrent.download.directory}")
    public String torrentDir;
    @Autowired
    private TorrentClient client;

    @Override
    public void start() {
        List<String> torrents = new ArrayList<>();
        try {

            Files.walk(Paths.get(downloadDirectory)).parallel().filter(file -> file.toString().contains(".torrent")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    torrents.add(filePath.toString());

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        torrents.parallelStream().filter(file->file.contains("hd")).forEach(torrent -> {
            try {
                logger.debug("Downloading file :{}", torrent);
                client.downloadFile(torrent, torrentDir);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
    }
}
