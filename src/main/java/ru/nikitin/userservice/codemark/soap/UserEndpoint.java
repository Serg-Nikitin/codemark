package ru.nikitin.userservice.codemark.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.nikitin.userservice.codemark.*;
import ru.nikitin.userservice.codemark.service.UserService;
import ru.nikitin.userservice.codemark.to.UserTo;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import static ru.nikitin.userservice.codemark.utill.validation.ValidationUtil.getErrors;


@Endpoint
public class UserEndpoint {

    private static final String LOCAL_PART_CREATE = "createRequest";
    private static final String LOCAL_PART_UPDATE = "updateUser";
    private static final String LOCAL_PART_DELETE = "deleteUser";
    private static final String LOCAL_PART_GET = "getUserRequest";


    private final Logger log = LoggerFactory.getLogger(UserEndpoint.class);

    public static final String NAME_SPACE = "http://ru/nikitin/userservice/codemark";

    private final UserService service;

    public UserEndpoint(UserService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAME_SPACE,
            localPart = LOCAL_PART_GET)
    @ResponsePayload
    public JAXBElement get(@RequestPayload GetUserRequest request) {
        try {
            service.getUser(request.getLogin());
            return getSuccess(LOCAL_PART_DELETE);
        } catch (Exception e) {
            return getFailed(LOCAL_PART_DELETE, e.getMessage());
        }
    }


    @PayloadRoot(namespace = NAME_SPACE, localPart = LOCAL_PART_CREATE)
    @ResponsePayload
    @Transactional
    public JAXBElement createUser(@RequestPayload CreateRequest user) {
        var userTo = new UserTo(user.getLogin(), user.getName(), user.getPassword(), user.getRoles());
        String errors = getErrors(userTo);
        if (!"".equals(errors)) {
            return getFailed(errors, LOCAL_PART_UPDATE);
        }

        try {
            service.create(userTo);
            return getSuccess(LOCAL_PART_UPDATE);
        } catch (Exception e) {
            return getFailed(e.getMessage(), LOCAL_PART_UPDATE);
        }
    }


    @PayloadRoot(namespace = NAME_SPACE,
            localPart = LOCAL_PART_UPDATE)
    @ResponsePayload
    public JAXBElement update(@RequestPayload UpdateRequest user) {
        var userTo = new UserTo(user.getLogin(), user.getName(), user.getPassword(), user.getRoles());
        String errors = getErrors(userTo);
        if (!"".equals(errors)) {
            return getFailed(errors, LOCAL_PART_UPDATE);
        }

        try {
            service.update(userTo.getLogin(), userTo);
            return getSuccess(LOCAL_PART_UPDATE);
        } catch (Exception e) {
            return getFailed(e.getMessage(), LOCAL_PART_UPDATE);
        }
    }


    @PayloadRoot(namespace = NAME_SPACE,
            localPart = LOCAL_PART_DELETE)
    @ResponsePayload
    public JAXBElement delete(@RequestPayload DeleteRequest request) {
        try {
            service.deleteUser(request.getLogin());
            return getSuccess(LOCAL_PART_DELETE);
        } catch (Exception e) {
            return getFailed(LOCAL_PART_DELETE, e.getMessage());
        }
    }


    private JAXBElement getSuccess(String localPart) {
        var success = new SuccessResponse();
        success.setSuccess(true);
        return new JAXBElement<SuccessResponse>(new QName(NAME_SPACE, localPart), SuccessResponse.class, success);
    }

    private JAXBElement getFailed(String errors, String localPart) {
        var failed = new FailedResponse();
        failed.setSuccess(false);
        failed.setErrors(errors);
        return new JAXBElement<FailedResponse>(new QName(NAME_SPACE, localPart), FailedResponse.class, failed);
    }
}