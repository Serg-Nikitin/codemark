package ru.nikitin.userservice.codemark.soap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.xml.transform.StringSource;
import ru.nikitin.userservice.codemark.to.UserTo;

import javax.xml.transform.Source;
import java.util.List;

@Slf4j
public class DataString {

    public static Source byLoginReq(String login, String action) {
        final String form = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cod=\"http://ru/nikitin/userservice/codemark\"><soapenv:Header/><soapenv:Body><cod:%s><cod:login>%s</cod:login></cod:%s></soapenv:Body></soapenv:Envelope>";
        return new StringSource(
                String.format(form, action, login, action)
        );
    }

    public static Source positiveResponse(String action) {
        final String form = "<ns2:%s xmlns:ns2=\"http://ru/nikitin/userservice/codemark\"><ns2:success>true</ns2:success></ns2:%s>";
        return new StringSource(
                String.format(form, action, action)
        );
    }

    public static Source negativeResponse(String errors) {
        final String form = "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><faultcode>SOAP-ENV:Server</faultcode><faultstring xml:lang=\"en\">%s</faultstring><detail><success>false</success><errors>%s</errors></detail></SOAP-ENV:Fault>\n";
        return new StringSource(
                String.format(form, errors, errors)
        );
    }


    public static Source responseSingleUser(UserTo got) {
        return new StringSource(
                "<ns2:getUserResponse xmlns:ns2=\"http://ru/nikitin/userservice/codemark\">" +
                        "<ns2:UserWithRoleXsd>" +
                        "<ns2:login>" + got.getLogin() + "</ns2:login>" +
                        "<ns2:name>" + got.getName() + "</ns2:name>" +
                        "<ns2:password>" + got.getPassword() + "</ns2:password>" +
                        "<ns2:listRole>" + responseWithRoles(got) + "</ns2:listRole>" +
                        "</ns2:UserWithRoleXsd>" +
                        "</ns2:getUserResponse>"
        );
    }

    public static Source getAllResponse(List<UserTo> list) {
        StringBuilder top = new StringBuilder("<ns2:getAllResponse xmlns:ns2=\"http://ru/nikitin/userservice/codemark\">");
        StringBuilder bottom = new StringBuilder("</ns2:getAllResponse>");
        String element = "<ns2:UserXsd><ns2:login>%s</ns2:login><ns2:name>%s</ns2:name><ns2:password>%s</ns2:password></ns2:UserXsd>";
        String result = "<ns2:getAllResponse xmlns:ns2=\"http://ru/nikitin/userservice/codemark\"/>\n";
        if (!list.isEmpty()) {
            list.forEach(to ->
                    top.append(String.format(element, to.getLogin(), to.getName(), to.getPassword()))
            );
            top.append(bottom);
            result = top.toString();
            log.info("list result = " + result);
        }
        return new StringSource(result);
    }

    static Source GET_ALL_REQUEST = new StringSource(
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cod=\"http://ru/nikitin/userservice/codemark\"><soapenv:Header/><soapenv:Body><cod:getAllRequest>?</cod:getAllRequest></soapenv:Body></soapenv:Envelope>");


    public static Source userRequest(UserTo to, String action) {
        StringBuilder top = new StringBuilder(
                "\n<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cod=\"http://ru/nikitin/userservice/codemark\">\n<soapenv:Header/><soapenv:Body>\n"
        );
        String body = String.format(
                        "     <cod:%s>\n" +
                        "         <cod:UserWithRoleXsd>\n" +
                        "            <cod:login>%s</cod:login>\n" +
                        "            <cod:name>%s</cod:name>\n" +
                        "            <cod:password>%s</cod:password>\n" +
                        "            <cod:listRole>%s</cod:listRole>\n" +
                        "         </cod:UserWithRoleXsd>\n" +
                        "      </cod:%s>\n"
                , action, to.getLogin(), to.getName(), to.getPassword(), requestWithRoles(to), action);
        top.append(body).append("</soapenv:Body>\n</soapenv:Envelope>\n");
        log.info("request result = " + top);

        return new StringSource(top.toString());
    }


    private static String requestWithRoles(UserTo got) {
        return prepareRoles(got, "<cod:role>%s</cod:role>");

    }

    private static String responseWithRoles(UserTo got) {
        return prepareRoles(got, "<ns2:role>%s</ns2:role>");
    }


    private static String prepareRoles(UserTo got, String form) {
        StringBuilder res = new StringBuilder();
        var list = got.getRoles();
        if (list.isEmpty()) {
            return String.format(form, "?");
        }
        list.forEach(role -> {
            res.append(String.format(form, role));
        });
        log.info("gotGetRoles roles = " + res);
        return res.toString();
    }


}
