package com.pruebaItx.infrastructure.config;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for H2 database TCP server. This enables remote connections to the H2
 * database.
 */
@Configuration
public class H2ServerConfig {

  @Value("${h2.tcp.enabled:false}")
  private boolean tcpServerEnabled;

  @Value("${h2.tcp.port:9092}")
  private String tcpPort;

  @Value("${h2.tcp.allowed.origins:*}")
  private String allowedOrigins;

  /**
   * Starts H2 TCP server for remote connections if enabled.
   *
   * @return the H2 TCP server instance
   * @throws SQLException if server cannot be started
   */
  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server h2TcpServer() throws SQLException {
    if (!tcpServerEnabled) {
      return null;
    }

    return Server.createTcpServer(
        "-tcp", "-tcpAllowOthers", "-tcpPort", tcpPort, "-baseDir", "~/.h2");
  }
}
