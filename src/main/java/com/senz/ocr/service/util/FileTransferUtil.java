package com.senz.ocr.service.util;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

public class FileTransferUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileTransferUtil.class);

    public static void transferFileToVM(String localFilePath, String remoteFilePath, String host, String user, String privateKey) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;

        try (FileInputStream fis = new FileInputStream(localFilePath)) {
            LOGGER.debug("vm request " + host + ":" + user + ":" + privateKey);
            jsch.addIdentity(privateKey);
            session = jsch.getSession(user, host, 22);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;

            channelSftp.put(fis, remoteFilePath, ChannelSftp.OVERWRITE);

            LOGGER.debug("File uploaded successfully to " + host + ":" + remoteFilePath);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}

