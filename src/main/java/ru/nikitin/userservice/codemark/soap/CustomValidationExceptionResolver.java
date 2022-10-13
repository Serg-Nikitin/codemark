package ru.nikitin.userservice.codemark.soap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.Comparator;

@Slf4j
@Component
public class CustomValidationExceptionResolver extends SoapFaultMappingExceptionResolver {
    private final String SUCCESS = "success";
    private final String ERRORS = "errors";


    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {

        log.info(" customizeFault Object endpoint = " + endpoint.getClass().getName());
        SoapFaultDetail detail = fault.addFaultDetail();
        detail.addFaultDetailElement(new QName(SUCCESS)).addText("false");
        detail.addFaultDetailElement(new QName(ERRORS)).addText(ex.getMessage());
        log.info(" details errors  = " + ex.getMessage());
        super.customizeFault(endpoint, ex, fault);
    }

}
