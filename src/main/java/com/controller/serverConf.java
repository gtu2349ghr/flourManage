package com.controller;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class serverConf {

    /**
     * @param host
     *            另一台服务器地址
     * @param port
     *            另一台服务器端口
     * @param username
     *            另一台服务器权限账户用户名
     * @param password
     *            另一台服务器权限账户密码
     * @return
     */
    public ChannelSftp connect(String host, int port, String username, String password) {
        ChannelSftp sftp = null;
        JSch jsch = new JSch();
        try {
            jsch.getSession(username, host, port);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        Session sshSession = null;
        try {
            sshSession = jsch.getSession(username, host, port);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        try {
            sshSession.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        try {
            channel = sshSession.openChannel("sftp");
        } catch (JSchException e) {
            e.printStackTrace();
        }
        try {
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        sftp = (ChannelSftp) channel;
        return sftp;
    }
    /**
     * 文件上传
     *
     * @param directory  上传至另一台服务器的存储路径
     * @param uploadFile 本服务器需要上传的文件路径及文件名
     * @param sftp  调用上方连接另一台服务器的方法connect
     */
    public void SendFile(String directory, String uploadFile, ChannelSftp sftp) {

        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        File file = new File(uploadFile);
        try {
            sftp.put(new FileInputStream(file), file.getName());
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
