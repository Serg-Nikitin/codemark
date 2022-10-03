package ru.nikitin.userservice.codemark.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.nikitin.userservice.codemark.*;
import ru.nikitin.userservice.codemark.service.UserService;

import static ru.nikitin.userservice.codemark.soap.UserServiceConfig.NAME_SPACE_URI;

@Endpoint
public class UserEndpoint {

    private final Logger log = LoggerFactory.getLogger(UserEndpoint.class);


    private final UserService service;

    public UserEndpoint(UserService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "getUsersRequest")
    @ResponsePayload
    public GetUsersResponse getAll() {
        GetUsersResponse resp = new GetUsersResponse();

        return resp;
    }

    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "getUserByLoginRequest")
    @ResponsePayload
    public GetUserByLoginResponse getByLogin(@RequestPayload GetUserByLoginRequest request) {
        GetUserByLoginResponse resp = new GetUserByLoginResponse();
        return resp;
    }


    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse create(@RequestPayload CreateUserRequest request) {
        CreateUserResponse resp = new CreateUserResponse();


        return resp;
    }

    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse update(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse resp = new UpdateUserResponse();


        return resp;
    }

    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "deleteUserByLoginRequest")
    @ResponsePayload
    public DeleteUserByLoginResponse delete(@RequestPayload DeleteUserByLoginRequest request) {
        String login = request.getLogin();
        DeleteUserByLoginResponse resp = new DeleteUserByLoginResponse();

        return resp;
    }

}
