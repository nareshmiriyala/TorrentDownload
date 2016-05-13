package com.dellnaresh;

import com.dellnaresh.torrent.TorrentClient;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, NoSuchAlgorithmException {
        TorrentClient torrentClient=new TorrentClient();
        torrentClient.downloadFile();
    }
}
