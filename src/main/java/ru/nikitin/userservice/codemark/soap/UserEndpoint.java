package ru.nikitin.userservice.codemark.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.nikitin.userservice.codemark.*;
import ru.nikitin.userservice.codemark.service.UserService;
import ru.nikitin.userservice.codemark.soap.validation.UserSoap1;
import ru.nikitin.userservice.codemark.soap.xml.*;
import ru.nikitin.userservice.codemark.to.UserTo;

import javax.validation.Valid;
import javax.xml.bind.Marshaller;

import static ru.nikitin.userservice.codemark.soap.UserServiceConfig.NAME_SPACE_URI;
import static ru.nikitin.userservice.codemark.to.UserTo.getToFromRequest;

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
        resp.getUsers().addAll(service.getAll());
        return resp;
    }

    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "getUserByLoginRequest")
    @ResponsePayload
    public Marshaller getByLogin(@RequestPayload GetUserByLoginRequest request) {
  /*      if(result.hasErrors()){
            log.info(result.toString());
        }*/

        GetUserByLoginResponse resp = new GetUserByLoginResponse();
        resp.setUser(service.getUserWithRole(request.getLogin()));
        return resp;
    }


    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse create(@RequestPayload CreateUserRequest request) {
        CreateUserResponse resp = new CreateUserResponse();
        UserTo userTo = getToFromRequest(request.getUser());

        resp.setUser(service.create(userTo));
        return resp;
    }

    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse update(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse resp = new UpdateUserResponse();
        UserTo userTo = getToFromRequest(request.getUser());

        resp.setUser(service.update(request.getLogin(), userTo));
        return resp;
    }

    @PayloadRoot(namespace = NAME_SPACE_URI,
            localPart = "deleteUserByLoginRequest")
    @ResponsePayload
    public DeleteUserByLoginResponse delete(@RequestPayload DeleteUserByLoginRequest request) {
        String login = request.getLogin();
        DeleteUserByLoginResponse resp = new DeleteUserByLoginResponse();
        resp.setDelete(service.deleteUser(login));
        return resp;
    }

}
