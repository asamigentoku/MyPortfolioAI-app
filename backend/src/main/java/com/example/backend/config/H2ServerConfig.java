package com.example.backend.config;


import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2ServerConfig {

    // 開発環境(dev)やローカル環境でのみ起動するようにしておくと安全です
    //本番環境では削除
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        // ポート9092でTCPサーバーを起動し、外部ツールからの接続を許可する
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}
