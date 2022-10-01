package ru.nikitin.userservice.codemark.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.nikitin.userservice.codemark.GetUserRequest;
import ru.nikitin.userservice.codemark.GetUserResponse;
import ru.nikitin.userservice.codemark.service.UserService;

import static ru.nikitin.userservice.codemark.soap.UserServiceConfig.NAME_SPACE_URI;

@Endpoint
public class UserEndpoint {

    private final UserService service;

    public UserEndpoint(UserService service) {
        this.service = service;
    }


    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getAll(@RequestPayload GetUserRequest request) {
        GetUserResponse resp = new GetUserResponse();
        var list = resp.getUsers().addAll(service.getAll());
        return resp;
    }
}
