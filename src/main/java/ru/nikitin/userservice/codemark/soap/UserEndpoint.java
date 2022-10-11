package ru.nikitin.userservice.codemark.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.nikitin.userservice.codemark.*;
import ru.nikitin.userservice.codemark.service.UserService;
import ru.nikitin.userservice.codemark.to.UserTo;
import ru.nikitin.userservice.codemark.utill.UserUtil;
import ru.nikitin.userservice.codemark.utill.validation.ValidationUtil;

import java.util.stream.Collectors;

import static ru.nikitin.userservice.codemark.utill.UserUtil.convertUserToGetUserResponse;
import static ru.nikitin.userservice.codemark.utill.UserUtil.convertXsdToUser;


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
    public GetAllResponse getAll(@RequestPayload GetAllRequest request) {
        GetAllResponse response = new GetAllResponse();
        var list =
                service.getAll()
                        .stream()
                        .map(UserUtil::convertUserXsd)
                        .collect(Collectors.toList());
        log.info("getAll list size = " + list.size());
        response.getUserXsd().addAll(list);
        return response;
    }


    @PayloadRoot(namespace = NAME_SPACE, localPart = LOCAL_PART_CREATE)
    @ResponsePayload
    public CreateResponse create(@RequestPayload CreateRequest request) {
        var userTo = convertXsdToUser(request.getUserWithRoleXsd());
        ValidationUtil.validate(userTo);
        UserTo saved = service.create(userTo);
        log.info("createUser user = " + saved.toString());

        var response = new CreateResponse();
        response.setSuccess(true);
        return response;
    }

    @PayloadRoot(namespace = NAME_SPACE, localPart = LOCAL_PART_UPDATE)
    @ResponsePayload
    public UpdateResponse update(@RequestPayload UpdateRequest request) {
        var userTo = convertXsdToUser(request.getUserWithRoleXsd());
        ValidationUtil.validate(userTo);
        UserTo saved = service.update(userTo);
        log.info("updateUser user = " + saved.toString());

        var response = new UpdateResponse();
        response.setSuccess(true);
        return response;
    }

    @PayloadRoot(namespace = NAME_SPACE, localPart = LOCAL_PART_GET)
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        String login = request.getLogin();

        UserTo got = service.getUserWithRole(login);
        log.info("GetUserWithRoles user = " + got.toString());

        return convertUserToGetUserResponse(got);
    }


    @PayloadRoot(namespace = NAME_SPACE, localPart = LOCAL_PART_DELETE)
    @ResponsePayload
    public DeleteResponse deleteUser(@RequestPayload DeleteRequest request) {
        service.deleteUser(request.getLogin());
        log.info("DeleteUser deleted = ");

        var response = new DeleteResponse();
        response.setSuccess(true);
        return response;
    }
}