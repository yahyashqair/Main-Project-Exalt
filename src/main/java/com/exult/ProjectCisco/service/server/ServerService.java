package com.exult.ProjectCisco.service.server;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
public class ServerService {
    public void zipFile(String username, String host, String password){
        try{
            JSch jsch=new JSch();
            Session session=jsch.getSession(username, host, 22);
            session.setPassword(password);
            session.connect(30000);   // making a connection with timeout.
            Channel channel=session.openChannel("shell");
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            String command = "tar -czvf devicePackageLoader.tar.gz /opt/CSCOlumos/xmp_inventory/dar  /opt/CSCOlumos/xmp_inventory/xde-home/packages/standard\n " ;
            channel.setInputStream(new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8)));
            channel.setOutputStream(System.out);
            channel.connect(3*1000);
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
}
