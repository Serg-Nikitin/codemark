package ru.nikitin.userservice.codemark.soap;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.ws.test.server.RequestCreators.withSoapEnvelope;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static ru.nikitin.userservice.codemark.service.UserServiceTest.*;
import static ru.nikitin.userservice.codemark.soap.DataString.*;

@Slf4j
public class UserEndpointTest extends SoapClientForTesting {

    private static String CREATE_REQUEST = "createRequest";
    private static String CREATE_RESPONSE = "createResponse";
    private static String GET_USER_REQUEST = "getUserRequest";
    private static String UPDATE_REQUEST = "updateRequest";
    private static String UPDATE_RESPONSE = "updateResponse";
    private static String DELETE_RESPONSE = "deleteResponse";
    private static String DELETE_REQUEST = "deleteRequest";
    private static String LOGIN_ALEX = "al";


    @Test
    public void getAll() {
        mockClient
                .sendRequest(withSoapEnvelope(GET_ALL_REQUEST))
                .andExpect(payload(getAllResponse(USERS)));
    }


    @Test
    void getUser() {
        mockClient
                .sendRequest(withSoapEnvelope(byLoginReq(LOGIN_ALEX, GET_USER_REQUEST)))
                .andExpect(payload(responseSingleUser(getAlex)));
    }


    @Test
    @Transactional(propagation = Propagation.NEVER)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void create() {
        mockClient
                .sendRequest(withSoapEnvelope(userRequest(createUser, CREATE_REQUEST)))
                .andExpect(payload(positiveResponse(CREATE_RESPONSE)));

    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void update() {
        mockClient
                .sendRequest(withSoapEnvelope(userRequest(updateUser, UPDATE_REQUEST)))
                .andExpect(payload(positiveResponse(UPDATE_RESPONSE)));

    }


    @Test
    @Transactional(propagation = Propagation.NEVER)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteUser() {
        mockClient
                .sendRequest(withSoapEnvelope(byLoginReq(LOGIN_ALEX, DELETE_REQUEST)))
                .andExpect(payload(positiveResponse(DELETE_RESPONSE)));
    }




}