package ru.nikitin.userservice.codemark.soap;

import org.springframework.oxm.Marshaller;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.AbstractEndpointExceptionResolver;
import ru.nikitin.userservice.codemark.ObjectFactory;

//https://maarten.mulders.it/2018/07/custom-soap-faults-using-spring-ws/


public class FaultCustom extends AbstractEndpointExceptionResolver {

    private static final ObjectFactory FACTORY = new ObjectFactory();

    private final Marshaller marshaller;

    public FaultCustom(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Override
    protected boolean resolveExceptionInternal(MessageContext messageContext, Object o, Exception e) {
        return false;
    }
}
