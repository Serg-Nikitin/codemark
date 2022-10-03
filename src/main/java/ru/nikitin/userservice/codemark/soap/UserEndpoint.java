package ru.nikitin.userservice.codemark.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.nikitin.userservice.codemark.*;
import ru.nikitin.userservice.codemark.service.UserService;
import ru.nikitin.userservice.codemark.to.UserTo;

import static ru.nikitin.userservice.codemark.to.UserTo.getToFromRequest;


@Endpoint
public class UserEndpoint {

    private final Logger log = LoggerFactory.getLogger(UserEndpoint.class);

    public static final String NAME_SPACE = "http://ru/nikitin/userservice/codemark";

    private final UserService service;

    public UserEndpoint(UserService service) {
        this.service = service;
    }


    @PayloadRoot(namespace = NAME_SPACE, localPart = "getUsersRequest")
    @ResponsePayload
    public GetUsersResponse getUsers() {
        GetUsersResponse response = new GetUsersResponse();
        response.getUsers().addAll(service.getAll());
        return response;
    }

    @PayloadRoot(namespace = NAME_SPACE,
            localPart = "getUserByLoginRequest")
    @ResponsePayload
    public GetUserByLoginResponse getByLogin(@RequestPayload GetUserByLoginRequest request) {
        GetUserByLoginResponse resp = new GetUserByLoginResponse();
        resp.setUser(service.getUser(request.getLogin()));
        return resp;
    }

    @PayloadRoot(namespace = NAME_SPACE,
            localPart = "deleteUserByLoginRequest")
    @ResponsePayload
    public DeleteUserByLoginResponse delete(@RequestPayload DeleteUserByLoginRequest request) {
        DeleteUserByLoginResponse resp = new DeleteUserByLoginResponse();
        boolean result = service.deleteUser(request.getLogin());
        resp.setSuccess(result);
        return resp;
    }

    @PayloadRoot(namespace = NAME_SPACE,
            localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse create(@RequestPayload CreateUserRequest request) {
        UserTo userTo = getToFromRequest(request.getUser()) ;
        boolean res = service.create(userTo) != null;
        CreateUserResponse resp = new CreateUserResponse();
        resp.setSuccess(res);
        return resp;
    }

}
   /*

    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse update(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse resp = new UpdateUserResponse();
        return resp;
    }



*/