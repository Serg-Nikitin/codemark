package ru.nikitin.userservice.codemark.soap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.xml.transform.StringSource;
import ru.nikitin.userservice.codemark.to.UserTo;

import javax.xml.transform.Source;
import java.util.List;

@Slf4j
public class DataString {

    public static Source byLoginRequest(String login, String action) {
        final String form = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cod=\"http://ru/nikitin/userservice/codemark\"><soapenv:Header/><soapenv:Body><cod:%s><cod:login>%s</cod:login></cod:%s></soapenv:Body></soapenv:Envelope>";
        return new StringSource(
                String.format(form, action, login, action)
        );
    }

    public static Source userRequest(UserTo to, String action) {
        StringBuilder top = new StringBuilder(
                "\n<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cod=\"http://ru/nikitin/userservice/codemark\">\n<soapenv:Header/><soapenv:Body>\n"
        );
        String body = String.format(
                """
                             <cod:%s>
                                 <cod:UserWithRoleXsd>
                                    <cod:login>%s</cod:login>
                                    <cod:name>%s</cod:name>
                                    <cod:password>%s</cod:password>
                                    <cod:listRole>%s</cod:listRole>
                                 </cod:UserWithRoleXsd>
                              </cod:%s>
                        """
                , action, to.getLogin(), to.getName(), to.getPassword(), requestWithRoles(to), action);
        top.append(body).append("</soapenv:Body>\n</soapenv:Envelope>\n");
        log.info("request result = " + top);

        return new StringSource(top.toString());
    }

    public static Source positiveResponse(String action) {
        final String form = "<%s xmlns=\"http://ru/nikitin/userservice/codemark\" xmlns:ns2=\"http://ru/nikitin/userservice/codemark\"><success>true</success></%s>";
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
                "<getUserResponse xmlns=\"http://ru/nikitin/userservice/codemark\" xmlns:ns2=\"http://ru/nikitin/userservice/codemark\">" +
                        "<UserWithRoleXsd>" +
                        "<login>" + got.getLogin() + "</login>" +
                        "<name>" + got.getName() + "</name>" +
                        "<password>" + got.getPassword() + "</password>" +
                        "<listRole>" + responseWithRoles(got) + "</listRole>" +
                        "</UserWithRoleXsd>" +
                        "</getUserResponse>"
        );
    }

    public static Source getAllResponse(List<UserTo> list) {
        StringBuilder top = new StringBuilder("<getAllResponse xmlns=\"http://ru/nikitin/userservice/codemark\" xmlns:ns2=\"http://ru/nikitin/userservice/codemark\">");
        StringBuilder bottom = new StringBuilder("</getAllResponse>");
        String element = "<UserXsd><login>%s</login><name>%s</name><password>%s</password></UserXsd>";
        String result = "<getAllResponse xmlns:ns2=\"http://ru/nikitin/userservice/codemark\"/>\n";
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


    private static String requestWithRoles(UserTo got) {
        return prepareRoles(got, "<cod:role>%s</cod:role>");

    }

    private static String responseWithRoles(UserTo got) {
        return prepareRoles(got, "<role>%s</role>");
    }


    private static String prepareRoles(UserTo got, String form) {
        StringBuilder res = new StringBuilder();
        var list = got.getRoles();
        if (list.isEmpty()) {
            return String.format(form, "?");
        }
        list.forEach(role ->
                res.append(String.format(form, role))
        );
        log.info("gotGetRoles roles = " + res);
        return res.toString();
    }
}
