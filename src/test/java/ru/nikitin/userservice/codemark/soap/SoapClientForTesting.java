package ru.nikitin.userservice.codemark.soap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootTest
public class SoapClientForTesting {


    @Autowired
    private ApplicationContext applicationContext;

    protected MockWebServiceClient mockClient;

    @PostConstruct
    public void init() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
        log.info("mock = " + mockClient);

    }

}
