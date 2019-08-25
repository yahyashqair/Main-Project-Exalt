package com.exult.ProjectCisco.service.server;

import com.exult.ProjectCisco.model.Server;
import com.exult.ProjectCisco.repository.ServerRepository;
import com.google.common.io.CharStreams;
import com.jcraft.jsch.*;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ServerService {
    @Autowired
    private ServerRepository serverRepository;

    public Server insertServer(Server server){
        serverRepository.save(server);
        return server;
    }

    public Server getServer(Long id){
        return serverRepository.findById(id).get();
    }

    public void zipFile(String username, String host, String password) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, 22);
            session.setPassword(password);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect(30000);   // making a connection with timeout.
            String command = "tar -czvf devicePackageLoader.tar.gz /opt/CSCOlumos/xmp_inventory/dar  /opt/CSCOlumos/xmp_inventory/xde-home/packages/standard\n ";
            //System.out.println(executeCommand(session,command));
            //downloadFile(session);
            unzippedFile();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private String executeCommand(Session session, String command) {
        if (session == null || !session.isConnected()) {
            return null;
        }
        try {
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            InputStream output = channel.getInputStream();
            channel.connect();
            String result = CharStreams.toString(new InputStreamReader(output));
            channel.disconnect();
            return result;
        } catch (JSchException | IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private void downloadFile(Session session) throws JSchException, SftpException {
        try {
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get("devicePackageLoader.tar.gz", "files/");
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    private String unzippedFile() throws  IOException {
        String s = "files/devicePackageLoader.tar.gz";
        int x = (int)Math.floor(Math.random() * 700000);
        String pathUnzippedFile = "files/" + x;
        Files.createTempDirectory(Paths.get("files"),"devicePackageFiles");
        Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");
        archiver.extract(new File(s), new File(pathUnzippedFile));
        System.out.println("done");
        return pathUnzippedFile;
    }

}
