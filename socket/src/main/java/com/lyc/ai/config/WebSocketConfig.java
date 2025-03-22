package com.lyc.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * springboot
 * Created by yadong.zhang on me.zhyd.springboot.websocket
 *
 * @author: yadong.zhang
 * @date: 2017/11/23 13:43
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
