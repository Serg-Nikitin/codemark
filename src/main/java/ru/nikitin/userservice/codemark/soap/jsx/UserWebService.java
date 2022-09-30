package ru.nikitin.userservice.codemark.soap.jsx;

import ru.nikitin.userservice.codemark.to.UserTo;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@javax.jws.WebService(targetNamespace = "http://service.ws.users/", name = "UserWebService")
public interface UserWebService {

    /**
     * === WebResult???
     *
     * @return
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAllRequest",
            targetNamespace = "http://service.ws.users/",
            className = "ru.nikitin.userservice.codemark.soap.GetAll")
    @WebMethod(action = "urn:GetAll")
    @ResponseWrapper(localName = "getAllResponse",
            targetNamespace = "http://service.ws.users/",
            className = "ru.nikitin.userservice.codemark.soap.GetAllResponse")
    List<UserTo> getAll();

}
