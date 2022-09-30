package ru.nikitin.userservice.codemark.soap.jsx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nikitin.userservice.codemark.service.UserService;
import ru.nikitin.userservice.codemark.to.UserTo;

import javax.jws.WebService;
import java.util.List;


@WebService(serviceName = "UserWebServicePort", portName = "UserPort",
        targetNamespace = "http://service.ws.users/",
        endpointInterface = "ru.nikitin.userservice.codemark.soap.jsx.UserWebService")
public class UserWebServicePortImpl implements UserWebService {


    @Autowired
    private UserService service;

    private static final Logger log = LoggerFactory.getLogger(UserWebServicePortImpl.class.getName());


    public List<UserTo> getAll() {
        log.info("Executing operation getAll");
        try {
            return service.getAll();
        } catch (Exception ex) {
            log.info("Exception getAll");
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
