package cn.zhaojian.system;

import cn.zhaojian.system.common.utils.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
//    @Bean
//    public SpringContextHolder springContextHolder() {
//        return new SpringContextHolder();
//    }
//
//    @Bean
//    public ServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
//        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
//        return fa;
//    }
//
//    /**
//     * 访问首页提示
//     *
//     * @return /
//     */
//    @RequestMapping("/")
//    public String index() {
//        return "Backend service started successfully";
//    }
}
