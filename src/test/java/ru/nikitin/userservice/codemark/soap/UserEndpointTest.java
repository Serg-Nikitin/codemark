package ru.nikitin.userservice.codemark.soap;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.to.UserTo;
import ru.nikitin.userservice.codemark.utill.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.ws.test.server.RequestCreators.withSoapEnvelope;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static ru.nikitin.userservice.codemark.service.UserServiceTest.*;
import static ru.nikitin.userservice.codemark.soap.DataString.*;

@Slf4j
public class UserEndpointTest extends SoapClientForTesting {

    private final static String CREATE_REQUEST = "createRequest";
    private final static String CREATE_RESPONSE = "createResponse";
    private final static String GET_USER_REQUEST = "getUserRequest";
    private final static String UPDATE_REQUEST = "updateRequest";
    private final static String UPDATE_RESPONSE = "updateResponse";
    private final static String DELETE_RESPONSE = "deleteResponse";
    private final static String DELETE_REQUEST = "deleteRequest";
    private final static String LOGIN_ALEX = getAlex.getLogin();
    private final static String LOGIN_CREATED = createUserWithRoles.getLogin();
    private final static UserTo duplicate = new UserTo("al", "Created", "Cr1ads", List.of(RoleName.OPERATOR.name()));
    private final static String ERROR_GET_NOT_FOUND = "Method getUserWithRoles: User with login = [ cr ], not found";
    private final static String ERROR_DUPLICATE = "Method create: User with login = [ al ] already exists";
    private final static String ERROR_UPDATE_NOT_FOUND = "Method update: User with login = [ cr ], not found";


    @Test
    @Transactional(propagation = Propagation.NEVER)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateShouldReturnFaultMessageNotFound() {
        mockClient
                .sendRequest(withSoapEnvelope(userRequest(createUserWithRoles, UPDATE_REQUEST)))
                .andExpect(payload(negativeResponse(ERROR_UPDATE_NOT_FOUND)));

    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createShouldReturnFaultMessageAlreadyExists() {
        mockClient
                .sendRequest(withSoapEnvelope(userRequest(duplicate, CREATE_REQUEST)))
                .andExpect(payload(negativeResponse(ERROR_DUPLICATE)));
    }


    @Test
    public void getAll() {
        mockClient
                .sendRequest(withSoapEnvelope(GET_ALL_REQUEST))
                .andExpect(payload(getAllResponse(USERS)));
    }

    @Test
    void getShouldReturnFaultMessageNotFound() {
        mockClient
                .sendRequest(withSoapEnvelope(byLoginReq(LOGIN_CREATED, GET_USER_REQUEST)))
                .andExpect(payload(negativeResponse(ERROR_GET_NOT_FOUND)));
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
    void createWithRole() {
        mockClient
                .sendRequest(withSoapEnvelope(userRequest(createUserWithRoles, CREATE_REQUEST)))
                .andExpect(payload(positiveResponse(CREATE_RESPONSE)));

        assertEquals(createUserWithRoles, service.getUserWithRole(createUserWithRoles.getLogin()));

    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createWithoutRole() {
        mockClient
                .sendRequest(withSoapEnvelope(userRequest(createUserWithoutRoles, CREATE_REQUEST)))
                .andExpect(payload(positiveResponse(CREATE_RESPONSE)));
        createUserWithoutRoles.setRoles(List.of(RoleName.EMPLOYEE.name()));
        assertEquals(createUserWithoutRoles, service.getUserWithRole(createUserWithoutRoles.getLogin()));

    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateWithRole() {
        mockClient
                .sendRequest(withSoapEnvelope(userRequest(updateUser, UPDATE_REQUEST)))
                .andExpect(payload(positiveResponse(UPDATE_RESPONSE)));
        assertEquals(updateUser, service.getUserWithRole(updateUser.getLogin()));

    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateWithOutRole() {
        mockClient
                .sendRequest(withSoapEnvelope(userRequest(updateYuriWithoutRole, UPDATE_REQUEST)))
                .andExpect(payload(positiveResponse(UPDATE_RESPONSE)));
        updateYuriWithoutRole.setRoles(yuriRoles);
        assertEquals(updateYuriWithoutRole, service.getUserWithRole(updateYuriWithoutRole.getLogin()));

    }


    @Test
    @Transactional(propagation = Propagation.NEVER)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteUser() {
        mockClient
                .sendRequest(withSoapEnvelope(byLoginReq(LOGIN_ALEX, DELETE_REQUEST)))
                .andExpect(payload(positiveResponse(DELETE_RESPONSE)));
        assertThrows(NotFoundException.class, () -> service.getUserWithRole(LOGIN_ALEX));
    }


}