package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import soapapp.DataService;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public Wsdl11Definition WSLDdefinition() {
        SimpleWsdl11Definition wsdlDefinition = new SimpleWsdl11Definition();
        wsdlDefinition.setWsdl(new ClassPathResource("wsdl/service.wsdl"));
        return wsdlDefinition;
    }

    @Bean
    public DataService DataService() {
        return new DataService();
    }

}
