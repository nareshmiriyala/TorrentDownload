package com.dellnaresh.torrent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by nareshm on 5/15/2016.
 */
public interface TorrentClient {
    void downloadFile(String torrent,String folder) throws IOException, NoSuchAlgorithmException;
}
