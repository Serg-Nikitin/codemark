package ru.nikitin.userservice.codemark.soap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPMessage;
import java.util.Iterator;

@Slf4j
public class CustomizationResponseEndpointInterceptor implements EndpointInterceptor {
    @Override
    public boolean handleRequest(MessageContext messageContext, Object o) throws Exception {
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object o) throws Exception {
        log.info("handleResponse, remove prefix into body response");
        SOAPMessage message = ((SaajSoapMessage) messageContext.getResponse()).getSaajMessage();
        SOAPBody body = message.getSOAPBody();
        removePrefix(body.getChildElements());
        return false;
    }


    @Override
    public boolean handleFault(MessageContext messageContext, Object o) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object o, Exception e) throws Exception {

    }

    private void removePrefix(Iterator<Node> body) {
        while (body.hasNext()) {
            Node node = body.next();
            if (!(node instanceof SOAPBodyElement)) {
                break;
            }
            node.setPrefix("");
            if (node.hasChildNodes()) {
                removePrefix(((SOAPBodyElement) node).getChildElements());
            }
        }
    }
}
