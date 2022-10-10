package ru.nikitin.userservice.codemark.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.nikitin.userservice.codemark.CreateRequest;
import ru.nikitin.userservice.codemark.CreateResponse;
import ru.nikitin.userservice.codemark.GetAllResponse;
import ru.nikitin.userservice.codemark.service.UserService;
import ru.nikitin.userservice.codemark.to.UserTo;
import ru.nikitin.userservice.codemark.utill.exception.NotFoundException;
import ru.nikitin.userservice.codemark.utill.validation.ValidationUtil;

import java.util.stream.Collectors;


@Endpoint
@ControllerAdvice
public class UserEndpoint {

    private static final String LOCAL_PART_GET_ALL = "getAllRequest";
    private static final String LOCAL_PART_CREATE = "createRequest";
    private static final String LOCAL_PART_UPDATE = "updateRequest";
    private static final String LOCAL_PART_DELETE = "deleteRequest";
    private static final String LOCAL_PART_GET = "getUserRequest";


    private final Logger log = LoggerFactory.getLogger(UserEndpoint.class);

    public static final String NAME_SPACE = "http://ru/nikitin/userservice/codemark";

    private final UserService service;

    public UserEndpoint(UserService service) {
        this.service = service;
    }


    @PayloadRoot(namespace = NAME_SPACE,
            localPart = LOCAL_PART_GET_ALL)
    @ResponsePayload
    public GetAllResponse getAll() {
        GetAllResponse response = new GetAllResponse();
        var list =
                service.getAll()
                        .stream()
                        .map(UserTo::convertXml)
                        .collect(Collectors.toList());
        log.info("list size = " + list.size());
        throw new NotFoundException("test with getAll and Exception handler");
//        response.getUsers().addAll(list);
    }


    @PayloadRoot(namespace = NAME_SPACE, localPart = LOCAL_PART_CREATE)
    @ResponsePayload
    public CreateResponse createUser(@RequestPayload CreateRequest user) {
        var userTo = new UserTo(user.getLogin(), user.getName(), user.getPassword(), user.getRoles());
        ValidationUtil.validate(userTo);
        service.create(userTo);
        var response = new CreateResponse();
        response.setSuccess(true);
        return response;
    }


    //    @PayloadRoot(namespace = NAME_SPACE,
//            localPart = LOCAL_PART_GET_ALL)
 /*   @ResponsePayload
    @ExceptionHandler(NotFoundException.class)
    public handleException(RuntimeException e) {
        log.info("////////////////exception = " + e.getMessage());
        return ;
    }
*/



/*    @PayloadRoot(namespace = NAME_SPACE,
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

    }*/
}