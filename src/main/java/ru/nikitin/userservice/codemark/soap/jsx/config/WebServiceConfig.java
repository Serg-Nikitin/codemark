package ru.nikitin.userservice.codemark.soap.jsx.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nikitin.userservice.codemark.soap.jsx.UserWebServicePortImpl;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    private final Bus bus;

    public WebServiceConfig(Bus bus) {
        this.bus = bus;
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new UserWebServicePortImpl());
        endpoint.publish("/service");
        return endpoint;
    }
}
